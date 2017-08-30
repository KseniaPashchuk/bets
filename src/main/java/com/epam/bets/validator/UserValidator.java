package com.epam.bets.validator;

import com.epam.bets.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.bets.constant.ErrorConstant.UserError.*;
import static com.epam.bets.constant.ErrorConstant.UserError.INVALID_CREDIT_CARD_ERROR;
import static com.epam.bets.constant.ErrorConstant.UserError.INVALID_LAST_NAME_ERROR;


public class UserValidator {
    private final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{6,10}$";
    private final String LOGIN_REGEX = "^.+@\\w+\\.\\w{2,6}$";
    private final String CREDIT_CARD_REGEX = "[0-9]{13,16}";

    public boolean validateLogin(String login) {
        Pattern loginPattern = Pattern.compile(LOGIN_REGEX);
        Matcher loginMatcher = loginPattern.matcher(login);
        return loginMatcher.matches();
    }

    public boolean validatePassword(String password) {
        Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        return passwordMatcher.matches();
    }

    public boolean validateBirthDate(LocalDate birthDate) {
        return LocalDate.now().getYear() - birthDate.getYear() >= 18;
    }

    public boolean validateName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public boolean validateCreditCard(String creditCard) {
        Pattern cardPattern = Pattern.compile(CREDIT_CARD_REGEX);
        Matcher cardMatcher = cardPattern.matcher(creditCard);
        return cardMatcher.matches();
    }


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

    public boolean validatePersonalParams(User user, List<String> errors) {

        boolean isValid = true;
        if (!validateBirthDate(user.getBirthDate())) {
            isValid = false;
            errors.add(INVALID_BIRTH_DATE_ERROR);
        }
        if (!validateName(user.getFirstName())) {
            isValid = false;
            errors.add(INVALID_FIRST_NAME_ERROR);
        }
        if (!validateName(user.getLastName())) {
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
}
