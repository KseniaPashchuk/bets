package com.epam.bets.command;

import com.epam.bets.entity.Match;
import com.epam.bets.receiver.impl.MatchReceiverImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowMatchesCommand implements AjaxCommand<Match> {

    private MatchReceiverImpl receiver = new MatchReceiverImpl();
    private static final Logger LOGGER = LogManager.getLogger(ShowMatchesCommand.class);

    @Override
    public List<Match> execute(HttpServletRequest request) {
        return null;
    }
}
