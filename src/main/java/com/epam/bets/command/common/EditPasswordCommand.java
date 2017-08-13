package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.validator.PasswordValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bets.constant.ErrorConstant.INVALID_PARAMS_ERROR;
import static com.epam.bets.constant.ErrorConstant.INVALID_PARAMS_MESSAGE;
import static com.epam.bets.constant.PageConstant.CHANGE_PASSWORD_PAGE;


public class EditPasswordCommand implements AbstractCommand {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_OLD_PASSWORD = "current_password";
    private static final String PARAM_NAME_NEW_PASSWORD = "new_password";
    private static final String NEXT_PAGE = CHANGE_PASSWORD_PAGE;
    private static final String ERROR_PAGE = CHANGE_PASSWORD_PAGE;

    private static final Logger LOGGER = LogManager.getLogger(EditPasswordCommand.class);
    private UserReceiver receiver = new UserReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {//TODO
        PageNavigator navigator;
        String login = (String) request.getSession().getAttribute(PARAM_NAME_LOGIN);
        String oldPassword = request.getParameter(PARAM_NAME_OLD_PASSWORD);
        String newPassword = request.getParameter(PARAM_NAME_NEW_PASSWORD);
        try {
            if (new PasswordValidator().validate(oldPassword) &&
                    new PasswordValidator().validate(newPassword)) {
                if (receiver.editPassword(login, oldPassword, newPassword)) {
                    navigator = new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
                } else {
                    request.setAttribute(INVALID_PARAMS_ERROR, INVALID_PARAMS_MESSAGE);
                    navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
                }
            } else {
                request.setAttribute(INVALID_PARAMS_ERROR, INVALID_PARAMS_MESSAGE);
                navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
        }
        return navigator;
    }
}
