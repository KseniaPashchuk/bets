package com.epam.bets.command.bookmaker;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.MatchReceiver;
import com.epam.bets.receiver.impl.MatchReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.bets.constant.ErrorConstant.ERROR_LIST_NAME;
import static com.epam.bets.constant.PageConstant.*;
import static com.epam.bets.constant.RequestParamConstant.MatchParam.PARAM_NAME_MATCH_ID;

/**
 * Class provides setting football match score operation for bookmaker.
 *
 * @author Pashchuk Ksenia
 * @see AbstractCommand
 */
public class SetMatchScoreCommand implements AbstractCommand {

    private static final String NEXT_PAGE = AFTER_SETTING_MATCH_SCORE;
    private static final String ERROR_PAGE = SHOW_MATCHES_PAGE;
    private MatchReceiver receiver = new MatchReceiverImpl();
    private static final Logger LOGGER = LogManager.getLogger(SetMatchScoreCommand.class);

    /**
     * Provides setting football match score operation for bookmaker.
     * Takes as parameter {@link RequestContent} and pass it to the Receiver layer {@link MatchReceiver}.
     * If Receiver operation passed successfully navigates to {@link com.epam.bets.constant.PageConstant#AFTER_SETTING_MATCH_SCORE}
     * (that provide calculating gain for users). Else navigates to {@link com.epam.bets.constant.PageConstant#SHOW_MATCHES_PAGE}
     * and saves navigation page to the session (required for use in locale change command
     * {@link com.epam.bets.command.common.ChangeLocaleCommand}).
     * If Receiver operation throws {@link ReceiverException}  navigates to {@link com.epam.bets.constant.PageConstant#SERVER_ERROR_PAGE}
     *
     * @param requestContent ({@link RequestContent}) request from client to get parameters to work with
     * @return {@link PageNavigator} with response parameters.
     */
    @Override
    public PageNavigator execute(RequestContent requestContent) {
        PageNavigator navigator;

        try {
            receiver.setMatchScore(requestContent);
            if (requestContent.findRequestAttribute(ERROR_LIST_NAME) == null) {
                navigator = new PageNavigator(NEXT_PAGE + requestContent.findParameterValue(PARAM_NAME_MATCH_ID),
                        PageNavigator.PageType.REDIRECT);
            } else {
                navigator = new PageNavigator(ERROR_PAGE, PageNavigator.PageType.FORWARD);
                requestContent.insertSessionAttribute(PREV_REQUEST, NEXT_PAGE);
            }

        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(SERVER_ERROR_PAGE, PageNavigator.PageType.REDIRECT);
        }
        return navigator;

    }
}
