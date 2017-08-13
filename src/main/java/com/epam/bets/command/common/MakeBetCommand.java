package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.entity.Bet;
import com.epam.bets.entity.BetType;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.epam.bets.constant.PageConstant.MAIN_PAGE;
import static com.epam.bets.constant.PageConstant.MATCHES_PAGE;

public class MakeBetCommand implements AbstractCommand {

    private static final String PARAM_NAME_MATCH_ID = "match_id";
    private static final String PARAM_NAME_SUMM = "bet_money";
    private static final String PARAM_NAME_BET_TYPE = "bet_type";
    private static final String PARAM_NAME_USER_ID = "userId";
    private static final String NEXT_PAGE = MATCHES_PAGE;
    private static final String ERROR_PAGE = MAIN_PAGE;
    private UserReceiver receiver = new UserReceiverImpl();
    private static final Logger LOGGER = LogManager.getLogger(MakeBetCommand.class);


    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        List<Bet> bets = new ArrayList<>();
        String[] matchIds = request.getParameterValues(PARAM_NAME_MATCH_ID);
        String[] summs = request.getParameterValues(PARAM_NAME_SUMM);
        String[] betTypes = request.getParameterValues(PARAM_NAME_BET_TYPE);
        int userId = (int) request.getSession().getAttribute(PARAM_NAME_USER_ID);
        int betLen = matchIds.length;
        for (int i = 0; i < betLen; i++) {
            bets.add(new Bet(new BigDecimal(summs[i]), Integer.parseInt(matchIds[i]), BetType.valueOf(betTypes[i]), userId));
        }
        try {
            if (receiver.makeBet(bets)) {
                navigator = new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
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
