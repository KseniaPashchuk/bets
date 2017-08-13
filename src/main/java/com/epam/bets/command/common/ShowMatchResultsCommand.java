package com.epam.bets.command.common;

import com.epam.bets.command.AjaxCommand;
import com.epam.bets.entity.Match;
import com.epam.bets.exception.CommandException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.MatchReceiver;
import com.epam.bets.receiver.impl.MatchReceiverImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShowMatchResultsCommand implements AjaxCommand<Match> {

    private static final String PARAM_NAME_CONFEDERACY = "confederacy";
    private static final String PARAM_NAME_DATE = "date";
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private MatchReceiver receiver = new MatchReceiverImpl();
    private static final Logger LOGGER = LogManager.getLogger(ShowMatchResultsCommand.class);

    @Override
    public List<Match> execute(HttpServletRequest request) throws CommandException {
        List<Match> matchList = null;
        try {
            String confederacy = request.getParameter(PARAM_NAME_CONFEDERACY);
            String dateString = request.getParameter(PARAM_NAME_DATE);
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_PATTERN));
            matchList = receiver.showMatchResults(date, confederacy);
        } catch (ReceiverException e) {
            throw new CommandException(e);
        }
        return matchList;
    }
}
