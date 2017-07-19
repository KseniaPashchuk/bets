package com.epam.bets.command;

import com.epam.bets.entity.News;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.NewsReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bets.JspConstant.MAIN_PAGE;
import static com.epam.bets.JspConstant.NEWS_PAGE;

public class ShowPieceOfNewsCommand implements AbstractCommand{
    private static final String PARAM_NAME_TITLE = "newsTitle";
    private static final String PARAM_NAME_NEWS = "news";
    private static final String NEXT_PAGE = NEWS_PAGE;
    private static final String ERROR_PAGE = MAIN_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(SignInCommand.class);
    private NewsReceiverImpl receiver = new NewsReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = new PageNavigator(NEXT_PAGE, PageType.FORWARD);
        News news;
        try {
            news = receiver.showPieceOfNews(request.getParameter(PARAM_NAME_TITLE));
            if (news != null) {
                request.setAttribute(PARAM_NAME_NEWS, news);
            } else {
                navigator.setPageUrl(ERROR_PAGE);
            }

        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e);
            navigator.setPageUrl(ERROR_PAGE);
        }
        return navigator;
    }
}
