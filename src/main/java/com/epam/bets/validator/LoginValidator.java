package com.epam.bets.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidator {

    private final String LOGIN_REGEX = "^.+@\\w+\\.\\w{2,6}$";

    public boolean validate(String login) {
        Pattern loginPattern = Pattern.compile(LOGIN_REGEX);
        Matcher loginMatcher = loginPattern.matcher(login);
        return loginMatcher.matches();
    }
}
