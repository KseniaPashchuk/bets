package com.epam.bets.command.admin;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.command.common.EditUserAvatarCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.LoadReceiver;
import com.epam.bets.receiver.impl.LoadReceiverImpl;
import com.epam.bets.request.RequestContent;
import com.epam.bets.validator.NewsValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.bets.constant.ErrorConstant.ERROR_LIST_NAME;
import static com.epam.bets.constant.PageConstant.*;
import static com.epam.bets.constant.RequestParamConstant.NewsParam.PARAM_NAME_TITLE;

/**
 * Class provides editing news picture operation for admin.
 *
 * @author Pashchuk Ksenia
 * @see AbstractCommand
 */
public class EditNewsPictureCommand implements AbstractCommand {

    private static final String NEXT_PAGE_AFTER_EDIT = SHOW_PIECE_OF_NEWS_PAGE;
    private static final String NEXT_PAGE_AFTER_CREATE = NEWS_PAGE;
    private static final String ERROR_PAGE = NEWS_PAGE;

    private static final Logger LOGGER = LogManager.getLogger(EditUserAvatarCommand.class);
    private LoadReceiver loadReceiver = new LoadReceiverImpl();

    /**
     * Provides editing news picture operation for admin.
     * Takes as parameter {@link RequestContent} and pass it to the Receiver layer  {@link LoadReceiver}.
     * If Receiver operation passed successfully navigates to {@link com.epam.bets.constant.PageConstant#SHOW_PIECE_OF_NEWS_PAGE}
     * (that provide showing updated news). Else navigates to {@link com.epam.bets.constant.PageConstant#NEWS_PAGE}.
     * Saves navigation page to the session (required for use in locale change command
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
            loadReceiver.updateNewsPicture(requestContent);
            if (requestContent.findRequestAttribute(ERROR_LIST_NAME) == null) {
                if (new NewsValidator().validateTitle(newsTitle)) {
                    navigator = new PageNavigator(NEXT_PAGE_AFTER_CREATE, PageNavigator.PageType.REDIRECT);
                    requestContent.insertSessionAttribute(PREV_REQUEST, NEXT_PAGE_AFTER_CREATE);
                } else {
                    navigator = new PageNavigator(NEXT_PAGE_AFTER_EDIT + newsTitle, PageNavigator.PageType.REDIRECT);
                    requestContent.insertSessionAttribute(PREV_REQUEST, NEXT_PAGE_AFTER_EDIT + newsTitle);
                }
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
