package com.epam.bets.command.admin;


import com.epam.bets.command.AbstractCommand;
import com.epam.bets.command.AjaxCommand;
import com.epam.bets.command.common.PasswordRecoverCommand;
import com.epam.bets.entity.News;
import com.epam.bets.entity.SupportMail;
import com.epam.bets.exception.CommandException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.receiver.LoadReceiver;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.epam.bets.constant.PageConstant.SHOW_SUPPORT_PAGE;
import static com.epam.bets.constant.PageConstant.SUPPORT_PAGE;

/**
 * Class provides showing support page operation for admin.
 *
 * @author Pashchuk Ksenia
 * @see AbstractCommand
 */
public class ShowSupportPageCommand implements AjaxCommand<SupportMail>{
    private static final String NEXT_PAGE = SUPPORT_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(PasswordRecoverCommand.class);
    private UserReceiver receiver = new UserReceiverImpl();

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
