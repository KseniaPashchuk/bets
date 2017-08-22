package com.epam.bets.command.admin;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.NewsReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.bets.constant.ErrorConstant.ERROR_MAP_NAME;
import static com.epam.bets.constant.PageConstant.AFTER_EDIT_NEWS_PAGE;
import static com.epam.bets.constant.PageConstant.NEWS_PAGE;
import static com.epam.bets.constant.PageConstant.SERVER_ERROR_PAGE;

public class EditNewsCommand implements AbstractCommand {

    private static final String NEXT_PAGE = AFTER_EDIT_NEWS_PAGE;
    private static final String ERROR_PAGE = NEWS_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(EditNewsCommand.class);
    private NewsReceiverImpl receiver = new NewsReceiverImpl();

    @Override
    public PageNavigator execute(RequestContent requestContent) {
        PageNavigator navigator;
        try {
            receiver.editNews(requestContent);
            if (requestContent.findRequestAttribute(ERROR_MAP_NAME) == null) {
                navigator = new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
            } else {
                navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(SERVER_ERROR_PAGE, PageType.REDIRECT);
        }
        return navigator;
    }
}
