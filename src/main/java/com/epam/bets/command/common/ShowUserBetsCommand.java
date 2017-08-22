package com.epam.bets.command.common;

import com.epam.bets.command.AjaxCommand;
import com.epam.bets.entity.Bet;
import com.epam.bets.exception.CommandException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowUserBetsCommand implements AjaxCommand<Bet> {

    private static final Logger LOGGER = LogManager.getLogger(ShowNewsCommand.class);
    private UserReceiver receiver = new UserReceiverImpl();

    @Override
    public List<Bet> execute(RequestContent requestContent) throws CommandException {
        List<Bet> bets = null;

        try {
            bets = receiver.showBets(requestContent);
        } catch (ReceiverException e) {
            throw new CommandException(e);
        }
        return bets;
    }
}
