package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bets.constant.PageConstant.INDEX_PAGE;

public class LogOutCommand implements AbstractCommand {

    private static  final String NEXT_PAGE = INDEX_PAGE;
    private UserReceiver receiver = new UserReceiverImpl();
    private static final Logger LOGGER = LogManager.getLogger(MakeBetCommand.class);

    @Override
    public PageNavigator execute(RequestContent requestContent) {
        receiver.logout(requestContent);
        return new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
    }
}
