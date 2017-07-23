package com.epam.bets.command;

import com.epam.bets.receiver.impl.UserReceiverImpl;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bets.JspConstant.MAIN_PAGE;
import static com.epam.bets.JspConstant.PROFILE_PAGE;
import static com.epam.bets.JspConstant.REGISTRATION_PAGE;

public class EditProfileCommand implements AbstractCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_ROLE = "role";
    private static final String PARAM_NAME_FIRST_NAME = "name";
    private static final String PARAM_NAME_LAST_NAME = "surname";
    private static final String PARAM_NAME_BIRTH_DATE = "birth_date";
    private static final String PARAM_NAME_CREDIT_CARD = "credit_card";
    private static final String NEXT_PAGE = PROFILE_PAGE;
    private static final String ERROR_PAGE = REGISTRATION_PAGE;
    private static final String EXISTING_USER_ERROR = "alreadyExists";
    private static final String INVALID_PASSWORD_ERROR = "invalidPassword";
    private static final String INVALID_LOGIN_ERROR = "invalidLogin";
    private static final String EXISTING_USER_MESSAGE = "user_already_exists";
    private static final String INVALID_PASSWORD_MESSAGE = "invalid_password";
    private static final String INVALID_LOGIN_MESSAGE = "invalid_login";
    private UserReceiverImpl receiver = new UserReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        return null;
    }
}
