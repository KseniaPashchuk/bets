package com.epam.bets.command;

import com.epam.bets.entity.News;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.NewsReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bets.constant.PageConstant.MAIN_PAGE;
import static com.epam.bets.constant.PageConstant.PIECE_OF_NEWS_PAGE;

public class ShowPieceOfNewsCommand implements AbstractCommand{

    private static final String PARAM_NAME_ID = "id";
    private static final String PARAM_NAME_TITLE = "title";
    private static final String PARAM_NAME_DATE = "date";
    private static final String PARAM_NAME_TEXT = "text";
    private static final String PARAM_NAME_PICTURE = "picture";
    private static final String NEXT_PAGE = PIECE_OF_NEWS_PAGE;
    private static final String ERROR_PAGE = MAIN_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(ShowPieceOfNewsCommand.class);
    private NewsReceiverImpl receiver = new NewsReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        News news;
        try {
            news = receiver.showPieceOfNews(request.getParameter(PARAM_NAME_TITLE));
            if (news != null) {
                request.setAttribute(PARAM_NAME_ID, news.getId());
                request.setAttribute(PARAM_NAME_TITLE, news.getTitle());
                request.setAttribute(PARAM_NAME_DATE, news.getDate());
                request.setAttribute(PARAM_NAME_TEXT, news.getText());
                request.setAttribute(PARAM_NAME_PICTURE, news.getPictureUrl());
                navigator = new PageNavigator(NEXT_PAGE, PageType.FORWARD);
            } else {
                navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }

        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
        }
        return navigator;
    }
}
