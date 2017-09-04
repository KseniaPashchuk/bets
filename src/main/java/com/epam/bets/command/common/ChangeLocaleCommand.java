package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.request.RequestContent;

import static com.epam.bets.constant.PageConstant.INDEX_PAGE;
/**
 * Class provides changing locale operation for user.
 *
 * @author Pashchuk Ksenia
 * @see AbstractCommand
 */
public class ChangeLocaleCommand implements AbstractCommand {

    private UserReceiver receiver = new UserReceiverImpl();
    /**
     * Provides changing locale operation for user.
     * Takes as parameter {@link RequestContent} and pass it to the Receiver layer  {@link UserReceiver}.
     * Takes Receiver operation result, navigates to previous request page saved in session.
     *
     * @param requestContent ({@link RequestContent}) request from client to get parameters to work with
     * @return {@link PageNavigator} with response parameters.
     */
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
