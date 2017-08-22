package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bets.constant.PageConstant.REFILL_CASH_PAGE;

public class RefillCashCommand implements AbstractCommand {//TODO
    private static final String NEXT_PAGE = REFILL_CASH_PAGE;
    private static final String PARAM_NAME_EMAIL = "email";
    private static final Logger LOGGER = LogManager.getLogger(RefillCashCommand.class);
    private UserReceiver receiver = new UserReceiverImpl();

    @Override
    public PageNavigator execute(RequestContent requestContent) {
        return null;
    }
}
