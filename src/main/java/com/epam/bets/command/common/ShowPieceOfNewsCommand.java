package com.epam.bets.command.common;

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


public class ShowPieceOfNewsCommand implements AbstractCommand {

    private static final String NEXT_PAGE = PIECE_OF_NEWS_PAGE;
    private static final String ERROR_PAGE = NEWS_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(ShowPieceOfNewsCommand.class);
    private NewsReceiver receiver = new NewsReceiverImpl();

    @Override
    public PageNavigator execute(RequestContent requestContent) {
        PageNavigator navigator;

        try {
            receiver.showPieceOfNews(requestContent);
            if (requestContent.findRequestAttribute(ERROR_LIST_NAME) == null) {
                navigator = new PageNavigator(NEXT_PAGE, PageType.FORWARD);
                requestContent.insertSessionAttribute(PREV_REQUEST, SHOW_PIECE_OF_NEWS_PAGE +
                        requestContent.findParameterValue(PARAM_NAME_TITLE));
            } else {
                navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
                requestContent.insertSessionAttribute(PREV_REQUEST, ERROR_PAGE);
            }

        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(SERVER_ERROR_PAGE, PageType.REDIRECT);
        }
        return navigator;
    }
}
