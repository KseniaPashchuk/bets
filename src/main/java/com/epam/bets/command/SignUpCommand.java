package com.epam.bets.command;

import com.epam.bets.entity.User;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.validator.LoginValidator;
import com.epam.bets.validator.PasswordValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.Month;

import static com.epam.bets.JspConstant.MAIN_PAGE;
import static com.epam.bets.JspConstant.REGISTRATION_PAGE;

public class SignUpCommand implements AbstractCommand {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_ROLE = "role";
    private static final String PARAM_NAME_FIRST_NAME = "name";
    private static final String PARAM_NAME_LAST_NAME = "surname";
    private static final String NEXT_PAGE = MAIN_PAGE;
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
        PageNavigator navigator = new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        try {
            if (new LoginValidator().validate(login)) {
                if (new PasswordValidator().validate(password)) {
                    User user = new User();
                    user.setLogin(login);
                    user.setPassword(password);
                    user.setFirstName(request.getParameter(PARAM_NAME_FIRST_NAME));
                    user.setLastName(request.getParameter(PARAM_NAME_LAST_NAME));
                    user.setBirthDate(LocalDate.of(2014, Month.JANUARY, 1));//TODO
                    user.setCreditCard("1111111111111111");
                    if (receiver.signUp(user)) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute(PARAM_NAME_LOGIN, user.getLogin());
                        session.setAttribute(PARAM_NAME_ROLE, user.getRole());
                    } else {
                        request.setAttribute(EXISTING_USER_ERROR, EXISTING_USER_MESSAGE);
                        navigator.setType(PageType.FORWARD);
                        navigator.setPageUrl(ERROR_PAGE);
                    }
                } else {
                    request.setAttribute(INVALID_PASSWORD_ERROR, INVALID_PASSWORD_MESSAGE);
                    navigator.setType(PageType.FORWARD);
                    navigator.setPageUrl(ERROR_PAGE);
                }
            } else {
                request.setAttribute(INVALID_LOGIN_ERROR, INVALID_LOGIN_MESSAGE);
                navigator.setType(PageType.FORWARD);
                navigator.setPageUrl(ERROR_PAGE);
            }
        } catch (ReceiverException e) {
            navigator.setType(PageType.FORWARD);
            navigator.setPageUrl(ERROR_PAGE);
        }
        return navigator;
    }
}

