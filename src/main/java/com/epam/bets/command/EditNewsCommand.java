package com.epam.bets.command;

import com.epam.bets.entity.News;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.NewsReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bets.constant.PageConstant.AFTER_EDIT_NEWS_PAGE;
import static com.epam.bets.constant.PageConstant.NEWS_PAGE;

public class EditNewsCommand implements AbstractCommand {

    private static final String PARAM_NAME_ID = "news_id";
    private static final String PARAM_NAME_TITLE = "edit_title";
    private static final String PARAM_NAME_TEXT = "edit_text";
    private static final String NEXT_PAGE = AFTER_EDIT_NEWS_PAGE;
    private static final String ERROR_PAGE = NEWS_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(EditNewsCommand.class);
    private NewsReceiverImpl receiver = new NewsReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        try {
            News news = new News();
            news.setId(Integer.parseInt(request.getParameter(PARAM_NAME_ID)));
            news.setTitle(request.getParameter(PARAM_NAME_TITLE));
            news.setText(request.getParameter(PARAM_NAME_TEXT));
            if (receiver.editNews(news)) {
                navigator = new PageNavigator(NEXT_PAGE + news.getTitle(), PageType.REDIRECT);
            } else {
//                request.setAttribute(INVALID_PARAMS_ERROR, INVALID_PARAMS_MESSAGE);
                navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
        }
        return navigator;
    }
}
