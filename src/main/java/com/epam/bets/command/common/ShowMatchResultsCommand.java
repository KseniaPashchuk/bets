package com.epam.bets.command.common;

import com.epam.bets.command.AjaxCommand;
import com.epam.bets.entity.Match;
import com.epam.bets.exception.CommandException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.MatchReceiver;
import com.epam.bets.receiver.impl.MatchReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShowMatchResultsCommand implements AjaxCommand<Match> {

    private MatchReceiver receiver = new MatchReceiverImpl();
    private static final Logger LOGGER = LogManager.getLogger(ShowMatchResultsCommand.class);

    @Override
    public List<Match> execute(RequestContent requestContent) throws CommandException {
        List<Match> matchList = null;
        try {
            matchList = receiver.showMatchResults(requestContent);
        } catch (ReceiverException e) {
            throw new CommandException(e);
        }
        return matchList;
    }
}
