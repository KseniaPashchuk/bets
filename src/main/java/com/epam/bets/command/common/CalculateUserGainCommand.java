package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bets.constant.PageConstant.MAIN_PAGE;
import static com.epam.bets.constant.PageConstant.MATCHES_PAGE;

public class CalculateUserGainCommand implements AbstractCommand {

    private static final String PARAM_NAME_MATCH_ID = "match_id";
    private static final String NEXT_PAGE = MATCHES_PAGE;
    private static final String ERROR_PAGE = MAIN_PAGE;
    private UserReceiver receiver = new UserReceiverImpl();
    private static final Logger LOGGER = LogManager.getLogger(CalculateUserGainCommand.class);

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;

        int matchId = Integer.parseInt(request.getParameter(PARAM_NAME_MATCH_ID));
        try {
            if (receiver.calculateGain(matchId)) {
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
