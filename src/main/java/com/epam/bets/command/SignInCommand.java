package com.epam.bets.command;

import com.epam.bets.entity.User;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.validator.LoginValidator;
import com.epam.bets.validator.PasswordValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.bets.JspConstant.MAIN_PAGE;
import static com.epam.bets.JspConstant.REGISTRATION_PAGE;

public class SignInCommand implements AbstractCommand {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_ROLE = "role";
    private static final String NEXT_PAGE = MAIN_PAGE;
    private static final String ERROR_PAGE = REGISTRATION_PAGE;
    private static final String INVALID_PARAMS_ERROR = "invalidParams";
    private static final String INVALID_PARAMS_MESSAGE = "invalid_params";
    private static final Logger LOGGER = LogManager.getLogger(SignInCommand.class);
    private UserReceiverImpl receiver = new UserReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {//TODO
        PageNavigator navigator;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        try {
            if (new LoginValidator().validate(login) &&
                    new PasswordValidator().validate(password)) {
                User user = receiver.signIn(login, password);
                if (user != null) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute(PARAM_NAME_LOGIN, user.getLogin());
                    session.setAttribute(PARAM_NAME_ROLE, user.getRole());
                    navigator = new PageNavigator(NEXT_PAGE, PageType.FORWARD);
                } else {
                    request.setAttribute(INVALID_PARAMS_ERROR, INVALID_PARAMS_MESSAGE);
                    navigator= new PageNavigator(ERROR_PAGE, PageType.FORWARD);
                }
            } else {
                request.setAttribute(INVALID_PARAMS_ERROR, INVALID_PARAMS_MESSAGE);
                navigator= new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e);
            navigator= new PageNavigator(ERROR_PAGE, PageType.FORWARD);
        }
        return navigator;
    }
}
