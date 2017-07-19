package com.epam.bets.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{6,10}$";

    public boolean validate(String password) {
        Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        return passwordMatcher.matches();
    }
}
