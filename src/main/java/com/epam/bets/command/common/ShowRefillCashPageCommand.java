package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;

import com.epam.bets.exception.ReceiverException;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.bets.constant.PageConstant.SHOW_REFILL_CASH_PAGE;
import static com.epam.bets.constant.PageConstant.REFILL_CASH_PAGE;
import static com.epam.bets.constant.PageConstant.SERVER_ERROR_PAGE;

/**
 * Class provides showing refill page  operation.
 *
 * @author Pashchuk Ksenia
 * @see AbstractCommand
 */
public class ShowRefillCashPageCommand implements AbstractCommand {

    private static final String NEXT_PAGE = REFILL_CASH_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(RefillCashCommand.class);
    private UserReceiver receiver = new UserReceiverImpl();
    /**
     * Provides  showing refill page  operation.
     * Takes as parameter {@link RequestContent} and pass it to the Receiver layer  {@link UserReceiver}.
     * Takes Receiver operation result, navigates to {@link com.epam.bets.constant.PageConstant#REFILL_CASH_PAGE}
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
            receiver.findAllCreditCards(requestContent);
            receiver.findUserBalance(requestContent);
            navigator = new PageNavigator(NEXT_PAGE, PageNavigator.PageType.FORWARD);
            requestContent.insertSessionAttribute(PREV_REQUEST, SHOW_REFILL_CASH_PAGE);
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(SERVER_ERROR_PAGE, PageNavigator.PageType.REDIRECT);
        }
        return navigator;
    }
}
