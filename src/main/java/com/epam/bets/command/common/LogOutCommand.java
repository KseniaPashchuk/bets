package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import static com.epam.bets.constant.PageConstant.INDEX_PAGE;
/**
 * Class provides log out operation.
 *
 * @author Pashchuk Ksenia
 * @see AbstractCommand
 */
public class LogOutCommand implements AbstractCommand {

    private static  final String NEXT_PAGE = INDEX_PAGE;
    private UserReceiver receiver = new UserReceiverImpl();
    private static final Logger LOGGER = LogManager.getLogger(MakeBetCommand.class);
    /**
     * Provides log out  operation.
     * Takes as parameter {@link RequestContent} and pass it to the Receiver layer  {@link UserReceiver}.
     * Takes Receiver operation result, navigates to {@link com.epam.bets.constant.PageConstant#INDEX_PAGE}
     * and saves navigation page to the session (required for use in locale change command
     * {@link com.epam.bets.command.common.ChangeLocaleCommand}).
     *
     * @param requestContent ({@link RequestContent}) request from client to get parameters to work with
     * @return {@link PageNavigator} with response parameters.
     */
    @Override
    public PageNavigator execute(RequestContent requestContent) {
        receiver.logout(requestContent);
        requestContent.insertSessionAttribute(PREV_REQUEST, NEXT_PAGE);
        return new PageNavigator(NEXT_PAGE, PageNavigator.PageType.REDIRECT);
    }
}
