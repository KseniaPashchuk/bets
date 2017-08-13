package com.epam.bets.command.common;


import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bets.constant.PageConstant.REGISTRATION_PAGE;

public class PasswordRecoverCommand implements AbstractCommand {


    private static final String NEXT_PAGE = REGISTRATION_PAGE;
    private static final String PARAM_NAME_EMAIL = "email";
    private static final Logger LOGGER = LogManager.getLogger(PasswordRecoverCommand.class);
    private UserReceiver receiver = new UserReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;

        String email = request.getParameter(PARAM_NAME_EMAIL);
        try {
            if (receiver.recoverPassword(email)) {
                navigator = new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
            } else {
                navigator = new PageNavigator(NEXT_PAGE, PageType.FORWARD);
            }

        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
        }
        return navigator;
    }
}
