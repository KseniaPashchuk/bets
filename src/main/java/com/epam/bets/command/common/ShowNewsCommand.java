package com.epam.bets.command.common;

import com.epam.bets.command.AjaxCommand;
import com.epam.bets.entity.News;
import com.epam.bets.exception.CommandException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.NewsReceiver;
import com.epam.bets.receiver.impl.NewsReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.epam.bets.constant.PageConstant.SHOW_NEWS_PAGE;
import static com.epam.bets.constant.RequestParamConstant.CommonParam.PARAM_NAME_DATE;

/**
 * Class provides showing news operation.
 *
 * @author Pashchuk Ksenia
 * @see AjaxCommand
 */

public class ShowNewsCommand implements AjaxCommand<News> {

    private static final Logger LOGGER = LogManager.getLogger(ShowNewsCommand.class);
    private NewsReceiver receiver = new NewsReceiverImpl();

    /**
     * Provides showing matches operation.
     * Takes as parameter {@link RequestContent} and pass it to the Receiver layer  {@link NewsReceiver}.
     * Takes Receiver operation result adn return it to {@link com.epam.bets.servlet.AjaxServlet}
     * Saves navigation page to the session (required for use in locale change command
     * {@link com.epam.bets.command.common.ChangeLocaleCommand}).
     * If Receiver operation throws {@link ReceiverException} throws {@link CommandException}
     *
     * @param requestContent ({@link RequestContent}) request from client to get parameters to work with
     * @return {@link List} with response parameters.
     */

    public List<News> execute(RequestContent requestContent) throws CommandException {
        List<News> newsList = null;
        try {
            newsList = receiver.showAllNews(requestContent);
            requestContent.insertSessionAttribute(PREV_REQUEST, SHOW_NEWS_PAGE + requestContent.findParameterValue(PARAM_NAME_DATE));
        } catch (ReceiverException e) {
            throw new CommandException(e);
        }
        return newsList;
    }
}
