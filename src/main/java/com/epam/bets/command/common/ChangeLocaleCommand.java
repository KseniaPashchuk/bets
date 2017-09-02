package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.request.RequestContent;

import static com.epam.bets.constant.PageConstant.INDEX_PAGE;

public class ChangeLocaleCommand implements AbstractCommand {

    private UserReceiver receiver = new UserReceiverImpl();

    @Override
    public PageNavigator execute(RequestContent requestContent) {
        String nextPage = (String) requestContent.findSessionAttribute(PREV_REQUEST);
        if (nextPage == null || nextPage.isEmpty() || nextPage.equals("/")) {
            nextPage = INDEX_PAGE;
        }
        receiver.changeLocale(requestContent);
        return new PageNavigator(nextPage, PageNavigator.PageType.REDIRECT);
    }
}
