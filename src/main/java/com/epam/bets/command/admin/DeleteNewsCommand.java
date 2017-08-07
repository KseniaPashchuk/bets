package com.epam.bets.command.admin;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.command.PageNavigator;
import com.epam.bets.command.PageType;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.NewsReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bets.constant.ErrorConstant.INVALID_PARAMS_ERROR;
import static com.epam.bets.constant.ErrorConstant.INVALID_PARAMS_MESSAGE;
import static com.epam.bets.constant.PageConstant.NEWS_PAGE;

public class DeleteNewsCommand implements AbstractCommand {
    private static final String PARAM_NAME_TITLE = "delete_title";
    private static final String NEXT_PAGE = NEWS_PAGE;
    private static final String ERROR_PAGE = NEWS_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(DeleteNewsCommand.class);
    private NewsReceiverImpl receiver = new NewsReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        try {
            if (receiver.deleteNews(request.getParameter(PARAM_NAME_TITLE))) {
                navigator = new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
            } else {
                request.setAttribute(INVALID_PARAMS_ERROR, INVALID_PARAMS_MESSAGE);
                navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
        }
        return navigator;
    }
}
