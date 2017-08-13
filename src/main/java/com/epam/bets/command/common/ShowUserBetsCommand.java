package com.epam.bets.command.common;

import com.epam.bets.command.AjaxCommand;
import com.epam.bets.entity.Bet;
import com.epam.bets.exception.CommandException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowUserBetsCommand implements AjaxCommand<Bet> {

    private static final String PARAM_NAME_ID = "userId";
    private static final String PARAM_NAME_TYPE = "type";
    private static final Logger LOGGER = LogManager.getLogger(ShowNewsCommand.class);
    private UserReceiver receiver = new UserReceiverImpl();

    @Override
    public List<Bet> execute(HttpServletRequest request) throws CommandException {
        List<Bet> bets = null;
        int userId = (int) request.getSession().getAttribute(PARAM_NAME_ID);
        String type = request.getParameter(PARAM_NAME_TYPE);
        try {
            bets = receiver.showBets(userId, type);
        } catch (ReceiverException e) {
            throw new CommandException(e);
        }
        return bets;
    }
}
