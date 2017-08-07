package com.epam.bets.command;

import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.MatchReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.epam.bets.constant.PageConstant.MATCHES_PAGE;
import static com.epam.bets.constant.PageConstant.MAIN_PAGE;


public class CreateMatchesPageCommand implements AbstractCommand {

    private static final String PARAM_NAME_TEAM_LIST = "teamList";
    private static final String PARAM_NAME_CONFEDERATION_LIST = "confederationList";
    private static final Logger LOGGER = LogManager.getLogger(CreateMatchesPageCommand.class);
    private static final String NEXT_PAGE = MATCHES_PAGE;
    private static final String ERROR_PAGE = MAIN_PAGE;

    private MatchReceiverImpl receiver = new MatchReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        List<String> confederations;
        List<String> teams;
        try {
            teams = receiver.showAllTeams();
            confederations = receiver.showAllConfederations();
            if (confederations != null && !confederations.isEmpty() && teams != null && !teams.isEmpty()) {
                request.setAttribute(PARAM_NAME_CONFEDERATION_LIST, confederations);
                request.setAttribute(PARAM_NAME_TEAM_LIST, teams);
                navigator = new PageNavigator(NEXT_PAGE, PageType.FORWARD);
            } else {
                navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
        }
        return navigator;
    }
}
