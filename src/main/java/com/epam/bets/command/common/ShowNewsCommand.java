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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class ShowNewsCommand implements AjaxCommand<News> {

    private static final Logger LOGGER = LogManager.getLogger(ShowNewsCommand.class);
    private NewsReceiver receiver = new NewsReceiverImpl();


    public List<News> execute(RequestContent requestContent) throws CommandException {
        List<News> newsList = null;
        try {
            newsList = receiver.showAllNews(requestContent);
        } catch (ReceiverException e) {
            throw new CommandException(e);
        }
        return newsList;
    }
}
