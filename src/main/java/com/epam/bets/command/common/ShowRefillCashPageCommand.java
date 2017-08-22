package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;

import com.epam.bets.exception.ReceiverException;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.bets.constant.PageConstant.REFILL_CASH_PAGE;
import static com.epam.bets.constant.PageConstant.SERVER_ERROR_PAGE;


public class ShowRefillCashPageCommand implements AbstractCommand {

    private static final String NEXT_PAGE = REFILL_CASH_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(RefillCashCommand.class);
    private UserReceiver receiver = new UserReceiverImpl();

    @Override
    public PageNavigator execute(RequestContent requestContent) {
        PageNavigator navigator;
        try {
            receiver.findAllCreditCards(requestContent);
            receiver.findUserBalance(requestContent);
            navigator = new PageNavigator(NEXT_PAGE, PageType.FORWARD);
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(SERVER_ERROR_PAGE, PageType.REDIRECT);
        }
        return navigator;
    }
}
