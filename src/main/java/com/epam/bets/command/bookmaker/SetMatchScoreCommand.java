package com.epam.bets.command.bookmaker;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.command.PageNavigator;
import com.epam.bets.command.PageType;
import com.epam.bets.command.ShowMatchesCommand;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.MatchReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Created by ASUS on 06.08.2017.
 */
public class SetMatchScoreCommand implements AbstractCommand {

    private static final String PARAM_NAME_MATCH_ID = "match_id";
    private static final String PARAM_NAME_FIRST_TEAM_SCORE = "first_team_score";
    private static final String PARAM_NAME_SECOND_TEAM_SCORE = "second_team_score";
    private MatchReceiverImpl receiver = new MatchReceiverImpl();
    private static final Logger LOGGER = LogManager.getLogger(SetMatchScoreCommand.class);

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        int matchId = Integer.parseInt(request.getParameter(PARAM_NAME_MATCH_ID));
        BigDecimal firstTeamScore = new BigDecimal(request.getParameter(PARAM_NAME_FIRST_TEAM_SCORE));
        BigDecimal secondTeamScore = new BigDecimal(request.getParameter(PARAM_NAME_SECOND_TEAM_SCORE));
        try {
            if (receiver.setMatchScore(matchId, firstTeamScore, secondTeamScore)) {
              //  navigator = new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
            }
            else{
             //   navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);

        }
        //return navigator;
        return null;
    }
}
