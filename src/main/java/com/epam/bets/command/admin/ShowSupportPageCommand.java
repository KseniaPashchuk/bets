package com.epam.bets.command.admin;


import com.epam.bets.command.AbstractCommand;
import com.epam.bets.command.AjaxCommand;
import com.epam.bets.command.common.PasswordRecoverCommand;
import com.epam.bets.entity.SupportMail;
import com.epam.bets.exception.CommandException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.receiver.SupportMailReceiver;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.SupportMailReceiverImpl;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.epam.bets.constant.PageConstant.SHOW_SUPPORT_PAGE;

/**
 * Class provides showing support page operation for admin.
 *
 * @author Pashchuk Ksenia
 * @see AjaxCommand
 */
public class ShowSupportPageCommand implements AjaxCommand<SupportMail>{

    private static final Logger LOGGER = LogManager.getLogger(PasswordRecoverCommand.class);
    private SupportMailReceiver receiver = new SupportMailReceiverImpl();
    /**
     * Provides showing add football team page operation for admin.
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
    public List<SupportMail> execute(RequestContent requestContent) throws CommandException {
        List<SupportMail> mailList = null;
        try {
            mailList = receiver.showLastUsersMail(requestContent);
            requestContent.insertSessionAttribute(PREV_REQUEST, SHOW_SUPPORT_PAGE);
        } catch (ReceiverException e) {
            throw new CommandException(e);
        }
        return mailList;
    }


}
