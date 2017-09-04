package com.epam.bets.command.common;

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

/**
 * Class provides showing match results page operation.
 *
 * @author Pashchuk Ksenia
 * @see AbstractCommand
 */
public class ShowMatchesPageCommand implements AbstractCommand {


    private static final Logger LOGGER = LogManager.getLogger(ShowMatchesPageCommand.class);
    private static final String NEXT_PAGE = MATCHES_PAGE;

    private MatchReceiver receiver = new MatchReceiverImpl();
    /**
     * Provides setting football match score operation for bookmaker.
     * Takes as parameter {@link RequestContent} and pass it to the Receiver layer {@link MatchReceiver}.
     * Takes Receiver operation result, navigates to {@link com.epam.bets.constant.PageConstant#MATCHES_PAGE}
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
            receiver.showAllTeams(requestContent);
            receiver.showAllConfederations(requestContent);
            navigator = new PageNavigator(NEXT_PAGE, PageNavigator.PageType.FORWARD);

            requestContent.insertSessionAttribute(PREV_REQUEST, SHOW_MATCHES_PAGE);
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(SERVER_ERROR_PAGE, PageNavigator.PageType.REDIRECT);
        }
        return navigator;
    }
}
