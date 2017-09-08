package com.epam.bets.validator;

import com.epam.bets.request.RequestContent;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.bets.constant.ErrorConstant.UserError.*;
import static com.epam.bets.constant.ErrorConstant.UserError.INVALID_CREDIT_CARD_ERROR;
import static com.epam.bets.constant.ErrorConstant.UserError.INVALID_LAST_NAME_ERROR;
import static com.epam.bets.constant.RequestParamConstant.CommonParam.DATE_PATTERN;
import static com.epam.bets.constant.RequestParamConstant.MatchParam.PARAM_NAME_MAX_BET;
import static com.epam.bets.constant.RequestParamConstant.UserParam.*;

/**
 * The class for user params validation.
 *
 * @author Pashchuk Ksenia
 */

public class UserValidator extends BaseValidator {

    private final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{6,10}$";
    private final String LOGIN_REGEX = "^.+@\\w+\\.\\w{2,6}$";
    private final String CREDIT_CARD_REGEX = "[0-9]{13,16}";
    private final String REFILL_AMOUNT_REGEX = "[0-9]{1,8}\\.?[0-9]{0,2}";
    private final String BET_REGEX = "[0-9]{1,5}\\.?[0-9]{0,2}";

    /**
     * Checks if login is valid
     *
     * @param login - login, entered by user
     * @return true if login matches to the regular expression.
     */
    public boolean validateLogin(String login, List<String> errors) {
        boolean isValid = true;
        if (!validateStringParamWithRegex(login, LOGIN_REGEX)) {
            errors.add(INVALID_LOGIN_ERROR);
            isValid = false;
        }
        return isValid;
    }


    /**
     * Checks if birth date is valid.
     *
     * @param birthDate - birth date, entered by user
     * @return true if the user is more than 18 years old
     */
    public boolean validateBirthDate(LocalDate birthDate) {
        return LocalDate.now().getYear() - birthDate.getYear() >= 18;
    }


    /**
     * Checks if credit card number is valid
     *
     * @param creditCard - credit card number, entered by user
     * @return true if credit card number matches to the regular expression.
     */
    public boolean validateCreditCard(String creditCard) {
        Pattern cardPattern = Pattern.compile(CREDIT_CARD_REGEX);
        Matcher cardMatcher = cardPattern.matcher(creditCard);
        return cardMatcher.matches();
    }

    /**
     * Checks if sign in params(login and password) is valid.
     *
     * @param requestContent -  all user information, required for sign in
     * @param errors         - for validation errors storage
     * @return true if login and password are valid
     */


    public boolean validateSignInParams(RequestContent requestContent, List<String> errors) {

        boolean isValid = true;

        if (!validateLogin(requestContent.findParameterValue(PARAM_NAME_LOGIN), errors)) {
            isValid = false;
        }
        if (!validateStringParamWithRegex(requestContent.findParameterValue(PARAM_NAME_PASSWORD), PASSWORD_REGEX)) {
            isValid = false;
            errors.add(INVALID_PASSWORD_ERROR);
        }
        return isValid;
    }


    /**
     * Checks if monetary bet big decimal param is valid
     *
     * @param param - monetary bet
     * @param regex - for param validation
     * @return true if bet matches to the regular expression.
     */
    public boolean validateBetParam(String param, String regex) {
        return (validateStringParamWithRegex(param, regex) && validateBigDecimalParam(new BigDecimal(param)));
    }


    /**
     * Checks if refill cash amount is valid.
     *
     * @param requestContent -  all user information, required for sign in
     * @param errors         - for validation errors storage
     * @return true if login and password are valid
     */
    public boolean validateRefillAmount(RequestContent requestContent, List<String> errors) {
        boolean isValid = true;
        if (!validateStringParamWithRegex(requestContent.findParameterValue(PARAM_NAME_REFILL_AMOUNT), REFILL_AMOUNT_REGEX)) {
            errors.add(INVALID_REFILL_AMOUNT);
            isValid = false;
        }

        return isValid;
    }


    /**
     * Checks if sign up params(login, password, birth date, first name, last name and credit card numbers)is valid.
     *
     * @param requestContent - all user information, required for sign up
     * @param errors         - for validation errors storage
     * @return true if all user information is valid
     */
    public boolean validateSignUpParams(RequestContent requestContent, List<String> errors) {
        boolean isValid = true;
        if (!validateSignInParams(requestContent, errors)) {
            isValid = false;
        }
        if (!validatePersonalParams(requestContent, errors)) {
            isValid = false;
        }
        return isValid;
    }


    /**
     * Checks if user personal information(birth date, first name, last name and credit card numbers) is valid.
     *
     * @param requestContent - all user information, required for sign up
     * @param errors         - for validation errors storage
     * @return true if all user personal information is valid
     */
    public boolean validatePersonalParams(RequestContent requestContent, List<String> errors) {
        boolean isValid = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

        String[] creditCardArray = requestContent.findParameterValues(PARAM_NAME_CREDIT_CARD);

        try {
            if (!validateBirthDate(LocalDate.parse(requestContent.findParameterValue(PARAM_NAME_BIRTH_DATE), formatter))) {
                isValid = false;
                errors.add(INVALID_BIRTH_DATE_ERROR);
            }
        }catch (DateTimeException e){
            isValid = false;
            errors.add(INVALID_BIRTH_DATE_ERROR);
        }
        if (!validateStringParam(requestContent.findParameterValue(PARAM_NAME_FIRST_NAME))) {
            isValid = false;
            errors.add(INVALID_FIRST_NAME_ERROR);
        }
        if (!validateStringParam(requestContent.findParameterValue(PARAM_NAME_LAST_NAME))) {
            isValid = false;
            errors.add(INVALID_LAST_NAME_ERROR);
        }
        if (creditCardArray.length != 0) {
            boolean isCardsValid = true;
            for (String creditCard : creditCardArray) {
                if (creditCard != null && !creditCard.isEmpty())
                    if (!validateStringParamWithRegex(creditCard, CREDIT_CARD_REGEX)) {
                        isCardsValid = false;
                    }
            }
            if (!isCardsValid) {
                isValid = false;
                errors.add(INVALID_CREDIT_CARD_ERROR);
            }
        } else {
            isValid = false;
            errors.add(INVALID_CREDIT_CARD_ERROR);
        }
        return isValid;
    }

    /**
     * Checks if user make bet params is valid.
     *
     * @param requestContent - all user information, required for sign up
     * @param errors         - for validation errors storage
     * @return true if all user personal information is valid
     */
    public boolean validateMakeBetParams(RequestContent requestContent, BigDecimal balance, List<String> errors) {
        boolean isValid = true;
        String[] summs = requestContent.findParameterValues(PARAM_NAME_SUMM);
        String[] maxBets = requestContent.findParameterValues(PARAM_NAME_MAX_BET);
        BigDecimal sumParam;
        BigDecimal betsSumm = new BigDecimal("0");
        for (int i = 0; i < summs.length; i++) {
            if (!validateBetParam(summs[i], BET_REGEX)) {
                isValid = false;
                errors.add(SUMM_NOT_POSITIVE);
                break;
            }
            sumParam = new BigDecimal(summs[i]);
            betsSumm = betsSumm.add(sumParam);
            if (sumParam.compareTo(new BigDecimal(maxBets[i])) == 1) {
                isValid = false;
                errors.add(TOO_BIG_BET_SUMM);
                break;
            }
        }
        if (balance.compareTo(betsSumm) == -1) {
            isValid = false;
            errors.add(NOT_ENOUGH_MONEY);
        }
        return isValid;
    }

    /**
     * Checks if user edit password params are valid.
     *
     * @param requestContent - all user information, required for sign up
     * @param errors         - for validation errors storage
     * @return true if all user personal information is valid
     */
    public boolean validateEditPasswordParams(RequestContent requestContent, List<String> errors) {
        boolean isValid = true;
        if (!validateStringParamWithRegex(requestContent.findParameterValue(PARAM_NAME_OLD_PASSWORD), PASSWORD_REGEX)) {
            isValid = false;
            errors.add(INVALID_CURRENT_PASSWORD_ERROR);
        }
        if (!validateStringParamWithRegex(requestContent.findParameterValue(PARAM_NAME_NEW_PASSWORD), PASSWORD_REGEX)) {
            isValid = false;
            errors.add(INVALID_NEW_PASSWORD_ERROR);
        }
        return isValid;
    }

}
