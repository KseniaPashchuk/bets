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

import static com.epam.bets.constant.PageConstant.SHOW_BETS_PAGE;
import static com.epam.bets.constant.RequestParamConstant.UserParam.PARAM_NAME_USER_BETS_TYPE;

/**
 * Class provides showing user bets operation.
 *
 * @author Pashchuk Ksenia
 * @see AjaxCommand
 */
public class ShowBetsCommand implements AjaxCommand<Bet> {

    private static final Logger LOGGER = LogManager.getLogger(ShowNewsCommand.class);
    private UserReceiver receiver = new UserReceiverImpl();

    /**
     * Provides showing user bets operation.
     * Takes as parameter {@link RequestContent} and pass it to the Receiver layer  {@link UserReceiver}.
     * Takes Receiver operation result adn return it to {@link com.epam.bets.servlet.AjaxServlet}
     * Saves navigation page to the session (required for use in locale change command
     * {@link com.epam.bets.command.common.ChangeLocaleCommand}).
     * If Receiver operation throws {@link ReceiverException} throws {@link CommandException}
     *
     * @param requestContent ({@link RequestContent}) request from client to get parameters to work with
     * @return {@link List} with response parameters.
     */
    @Override
    public List<Bet> execute(RequestContent requestContent) throws CommandException {
        List<Bet> bets = null;
        try {
            bets = receiver.showBets(requestContent);
            requestContent.insertSessionAttribute(PREV_REQUEST, SHOW_BETS_PAGE +
                    requestContent.findParameterValue(PARAM_NAME_USER_BETS_TYPE));
        } catch (ReceiverException e) {
            throw new CommandException(e);
        }
        return bets;
    }
}
