package com.epam.bets.command.admin;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.NewsReceiver;
import com.epam.bets.receiver.impl.NewsReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.bets.constant.ErrorConstant.ERROR_LIST_NAME;
import static com.epam.bets.constant.PageConstant.*;
import static com.epam.bets.constant.RequestParamConstant.NewsParam.PARAM_NAME_TITLE;

/**
 * Class provides editing news operation for admin.
 *
 * @author Pashchuk Ksenia
 * @see AbstractCommand
 */
public class EditNewsCommand implements AbstractCommand {

    private static final String NEXT_PAGE = SHOW_PIECE_OF_NEWS_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(EditNewsCommand.class);
    private NewsReceiver receiver = new NewsReceiverImpl();

    /**
     * Provides editing news operation for admin.
     * Takes as parameter {@link RequestContent} and pass it to the Receiver layer  {@link NewsReceiver}.
     * Takes Receiver operation result, navigates to {@link com.epam.bets.constant.PageConstant#SHOW_PIECE_OF_NEWS_PAGE}
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
            String newsTitle = requestContent.findParameterValue(PARAM_NAME_TITLE);
            receiver.editNews(requestContent);
            if (requestContent.findRequestAttribute(ERROR_LIST_NAME) == null) {
                navigator = new PageNavigator(NEXT_PAGE + newsTitle, PageType.REDIRECT);
            } else {
                navigator = new PageNavigator(NEXT_PAGE + newsTitle, PageType.FORWARD);
            }
            requestContent.insertSessionAttribute(PREV_REQUEST, NEXT_PAGE + newsTitle);
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(SERVER_ERROR_PAGE, PageType.REDIRECT);
        }
        return navigator;
    }
}
