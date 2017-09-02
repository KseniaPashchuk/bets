package com.epam.bets.command.admin;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.FAQReceiver;
import com.epam.bets.receiver.impl.FAQReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.bets.constant.ErrorConstant.ERROR_LIST_NAME;
import static com.epam.bets.constant.PageConstant.*;

/**
 * Class provides deleting FAQ operation for admin.
 *
 * @author Pashchuk Ksenia
 * @see AbstractCommand
 */
public class DeleteFAQCommand implements AbstractCommand {


    private static final String NEXT_PAGE = SHOW_FAQ_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(DeleteFAQCommand.class);
    private FAQReceiver receiver = new FAQReceiverImpl();
    /**
     * Provides deleting FAQ operation for admin.
     * Takes as parameter {@link RequestContent} and pass it to the Receiver layer {@link FAQReceiver}.
     * Takes Receiver operation result, navigates to {@link com.epam.bets.constant.PageConstant#SHOW_FAQ_PAGE}
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
            receiver.deleteFAQ(requestContent);
            if (requestContent.findRequestAttribute(ERROR_LIST_NAME) == null) {
                navigator = new PageNavigator(NEXT_PAGE, PageNavigator.PageType.REDIRECT);
            } else {
                navigator = new PageNavigator(NEXT_PAGE, PageNavigator.PageType.FORWARD);
            }
            requestContent.insertSessionAttribute(PREV_REQUEST, NEXT_PAGE);
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(SERVER_ERROR_PAGE, PageNavigator.PageType.REDIRECT);
        }
        return navigator;
    }
}
