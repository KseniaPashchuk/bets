package com.epam.bets.command.common;

import com.epam.bets.command.AjaxCommand;
import com.epam.bets.entity.News;
import com.epam.bets.exception.CommandException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.NewsReceiver;
import com.epam.bets.receiver.impl.NewsReceiverImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class ShowNewsCommand implements AjaxCommand<News> {

    private static final String PARAM_NAME_DATE = "date";
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final Logger LOGGER = LogManager.getLogger(ShowNewsCommand.class);
    private NewsReceiver receiver = new NewsReceiverImpl();


    public List<News> execute(HttpServletRequest request) throws CommandException {

        List<News> newsList = null;
        try {
            String dateString = request.getParameter(PARAM_NAME_DATE);
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_PATTERN));
            newsList = receiver.showAllNews(date);
        } catch (ReceiverException e) {
            throw new CommandException(e);
        }
        return newsList;
    }
}
