package com.epam.bets.validator;

import com.epam.bets.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.bets.constant.ErrorConstant.UserError.*;
import static com.epam.bets.constant.ErrorConstant.UserError.INVALID_CREDIT_CARD_ERROR;
import static com.epam.bets.constant.ErrorConstant.UserError.INVALID_LAST_NAME_ERROR;

/**
 * The class for user params validation.
 *
 * @author Pashchuk Ksenia
 */

public class UserValidator extends BaseValidator {

    private final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{6,10}$";
    private final String LOGIN_REGEX = "^.+@\\w+\\.\\w{2,6}$";
    private final String CREDIT_CARD_REGEX = "[0-9]{13,16}";

    /**
     * Checks if login is valid
     *
     * @param login - login, entered by user
     * @return true if login matches to the regular expression.
     */
    public boolean validateLogin(String login) {
        Pattern loginPattern = Pattern.compile(LOGIN_REGEX);
        Matcher loginMatcher = loginPattern.matcher(login);
        return loginMatcher.matches();
    }

    /**
     * Checks if password is valid.
     *
     * @param password - password,entered by user
     * @return true if password matches to the regular expression.
     */
    public boolean validatePassword(String password) {
        Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        return passwordMatcher.matches();
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
     * @param login    - login, entered by user
     * @param password - password, entered by user
     * @param errors   - for validation errors storage
     * @return true if login and password are valid
     */


    public boolean validateSignInParams(String login, String password, List<String> errors) {

        boolean isValid = true;
        if (!validateLogin(login)) {
            isValid = false;
            errors.add(INVALID_LOGIN_ERROR);
        }
        if (!validatePassword(password)) {
            isValid = false;
            errors.add(INVALID_PASSWORD_ERROR);
        }
        return isValid;
    }

    public boolean validateRefillAmount(BigDecimal amount, List<String> errors) {
        if (!validateBigDecimalParam(amount)) {
            errors.add(INVALID_REFILL_AMOUNT);
        }
        return validateBigDecimalParam(amount);
    }



    /**
     * Checks if sign up params(login, password, birth date, first name, last name and credit card numbers)is valid.
     *
     * @param user   - all user information, required for sign up
     * @param errors - for validation errors storage
     * @return true if all user information is valid
     */
    public boolean validateSignUpParams(User user, List<String> errors) {
        boolean isValid = true;
        if (!validateSignInParams(user.getLogin(), user.getPassword(), errors)) {
            isValid = false;
        }
        if (!validatePersonalParams(user, errors)) {
            isValid = false;
        }
        return isValid;
    }




    /**
     * Checks if user personal information(birth date, first name, last name and credit card numbers) is valid.
     *
     * @param user   - all user information, required for sign up
     * @param errors - for validation errors storage
     * @return true if all user personal information is valid
     */
    public boolean validatePersonalParams(User user, List<String> errors) {

        boolean isValid = true;
        if (!validateBirthDate(user.getBirthDate())) {
            isValid = false;
            errors.add(INVALID_BIRTH_DATE_ERROR);
        }
        if (!validateStringParam(user.getFirstName())) {
            isValid = false;
            errors.add(INVALID_FIRST_NAME_ERROR);
        }
        if (!validateStringParam(user.getLastName())) {
            isValid = false;
            errors.add(INVALID_LAST_NAME_ERROR);
        }
        if (user.getCreditCards().getCreditCarsSize() != 0) {
            boolean isCardsValid = true;
            for (String creditCard : user.getCreditCards().getCreditCardList()) {
                if (!validateCreditCard(creditCard)) {
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

    public boolean validateMakeBetParams(String[] summs, String[] maxBets, BigDecimal balance, List<String> errors) {
        boolean isValid = true;
        BigDecimal sumParam;
        BigDecimal betsSumm = new BigDecimal("0");
        for (int i = 0; i < summs.length; i++) {
            sumParam = new BigDecimal(summs[i]);
            betsSumm = betsSumm.add(sumParam);
            if (!validateBigDecimalParam(sumParam)) {
                isValid = false;
                errors.add(SUMM_NOT_POSITIVE);
                break;
            }
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

}
