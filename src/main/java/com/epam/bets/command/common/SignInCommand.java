package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.bets.constant.ErrorConstant.ERROR_LIST_NAME;
import static com.epam.bets.constant.PageConstant.REGISTRATION_PAGE;
import static com.epam.bets.constant.PageConstant.SERVER_ERROR_PAGE;
import static com.epam.bets.constant.PageConstant.SHOW_MAIN_PAGE;
/**
 * Class provides singing in operation.
 *
 * @author Pashchuk Ksenia
 * @see AbstractCommand
 */
public class SignInCommand implements AbstractCommand {

    private static final String NEXT_PAGE = SHOW_MAIN_PAGE;
    private static final String ERROR_PAGE = REGISTRATION_PAGE;

    private static final Logger LOGGER = LogManager.getLogger(SignInCommand.class);
    private UserReceiver receiver = new UserReceiverImpl();
    /**
     * Provides singing in operation.
     * Takes as parameter {@link RequestContent} and pass it to the Receiver layer {@link UserReceiver}.
     * If Receiver operation passed successfully navigates to {@link com.epam.bets.constant.PageConstant#SHOW_MAIN_PAGE}
     * (that provide saving news picture). Else navigates to {@link com.epam.bets.constant.PageConstant#REGISTRATION_PAGE}
     * and saves navigation page to the session (required for use in locale change command
     * {@link com.epam.bets.command.common.ChangeLocaleCommand}).
     * If Receiver operation throws {@link ReceiverException}  navigates to {@link com.epam.bets.constant.PageConstant#SERVER_ERROR_PAGE}
     *
     * @param requestContent ({@link RequestContent}) request from client to get parameters to work with
     * @return {@link PageNavigator} with response parameters.
     */
    @Override
    public PageNavigator execute(RequestContent requestContent) {
        PageNavigator navigator;
        try {
            receiver.signIn(requestContent);
            if (requestContent.findRequestAttribute(ERROR_LIST_NAME) == null) {
                navigator = new PageNavigator(NEXT_PAGE, PageNavigator.PageType.REDIRECT);
                requestContent.insertSessionAttribute(PREV_REQUEST, NEXT_PAGE);
            } else {
                navigator = new PageNavigator(ERROR_PAGE, PageNavigator.PageType.FORWARD);
                requestContent.insertSessionAttribute(PREV_REQUEST, ERROR_PAGE);
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(SERVER_ERROR_PAGE, PageNavigator.PageType.REDIRECT);
        }
        return navigator;
    }
}
