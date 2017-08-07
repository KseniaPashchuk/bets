package com.epam.bets.command;

import com.epam.bets.entity.Match;
import com.epam.bets.exception.CommandException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.MatchReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShowMatchesCommand implements AjaxCommand<Match> {

    private static final String PARAM_NAME_CONFEDERACY = "confederacy";
    private MatchReceiverImpl receiver = new MatchReceiverImpl();
    private static final Logger LOGGER = LogManager.getLogger(ShowMatchesCommand.class);

    @Override
    public List<Match> execute(HttpServletRequest request) throws CommandException {
        List<Match> matchList = null;
        try {
            String confederacy = request.getParameter(PARAM_NAME_CONFEDERACY);
            matchList = receiver.showMatches(confederacy);
        } catch (ReceiverException e) {
            throw new CommandException(e);
        }
        return matchList;
    }
}
