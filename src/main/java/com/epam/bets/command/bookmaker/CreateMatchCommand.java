package com.epam.bets.command.bookmaker;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.entity.BetType;
import com.epam.bets.entity.Match;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.MatchReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.epam.bets.constant.PageConstant.AFTER_EDIT_MATCHES_PAGE;
import static com.epam.bets.constant.PageConstant.MAIN_PAGE;

public class CreateMatchCommand implements AbstractCommand {

    private static final String PARAM_NAME_FIRST_TEAM = "first_team";
    private static final String PARAM_NAME_SECOND_TEAM = "second_team";
    private static final String PARAM_NAME_CONFEDERACY = "confederacy";
    private static final String PARAM_NAME_DATE = "match_date";
    private static final String PARAM_NAME_FW = "FW";
    private static final String PARAM_NAME_X = "X";
    private static final String PARAM_NAME_SW = "SW";
    private static final String PARAM_NAME_FWX = "FWX";
    private static final String PARAM_NAME_FS = "FS";
    private static final String PARAM_NAME_XSW = "XSW";
    private static final String PARAM_NAME_TL = "TL";
    private static final String PARAM_NAME_T = "T";
    private static final String PARAM_NAME_TM = "TM";
    private static final String PARAM_NAME_MAX_BET = "max_bet";
    private static final String DATE_PATTERN = "dd/MM/yyyy HH:mm";
    private static final String NEXT_PAGE = AFTER_EDIT_MATCHES_PAGE;
    private static final String ERROR_PAGE = MAIN_PAGE;
    private MatchReceiverImpl receiver = new MatchReceiverImpl();
    private static final Logger LOGGER = LogManager.getLogger(CreateMatchCommand.class);

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        Match match = new Match();
        match.setFirstTeam(request.getParameter(PARAM_NAME_FIRST_TEAM));
        match.setSecondTeam(request.getParameter(PARAM_NAME_SECOND_TEAM));
        match.setConfederacy(request.getParameter(PARAM_NAME_CONFEDERACY));
        match.setDate(LocalDateTime.parse(request.getParameter(PARAM_NAME_DATE), DateTimeFormatter.ofPattern(DATE_PATTERN)));
        match.addCoefficient(BetType.FW, new BigDecimal(request.getParameter(PARAM_NAME_FW)));
        match.addCoefficient(BetType.X, new BigDecimal(request.getParameter(PARAM_NAME_X)));
        match.addCoefficient(BetType.SW, new BigDecimal(request.getParameter(PARAM_NAME_SW)));
        match.addCoefficient(BetType.FWX, new BigDecimal(request.getParameter(PARAM_NAME_FWX)));
        match.addCoefficient(BetType.FS, new BigDecimal(request.getParameter(PARAM_NAME_FS)));
        match.addCoefficient(BetType.XSW, new BigDecimal(request.getParameter(PARAM_NAME_XSW)));
        match.addCoefficient(BetType.TL, new BigDecimal(request.getParameter(PARAM_NAME_TL)));
        match.addCoefficient(BetType.TM, new BigDecimal(request.getParameter(PARAM_NAME_TM)));
        match.setTotal(new BigDecimal(request.getParameter(PARAM_NAME_T)));
        match.setMaxBet(new BigDecimal(request.getParameter(PARAM_NAME_MAX_BET)));
        try {
            if (receiver.createMatch(match)) {
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
