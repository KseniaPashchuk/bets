package com.epam.bets.command.admin;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.command.common.ShowUserProfileCommand;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.bets.constant.PageConstant.SERVER_ERROR_PAGE;
import static com.epam.bets.constant.PageConstant.SHOW_USER_INFO;
import static com.epam.bets.constant.PageConstant.USER_SETTING_PAGE;
import static com.epam.bets.constant.RequestParamConstant.UserParam.PARAM_NAME_EMAIL;

/**
 * Class provides showing user info operation for admin.
 *
 * @author Pashchuk Ksenia
 * @see AbstractCommand
 */
public class ShowUserInfoCommand implements AbstractCommand {

    private static final String NEXT_PAGE = USER_SETTING_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(ShowUserProfileCommand.class);
    private UserReceiver receiver = new UserReceiverImpl();
    /**
     * Provides showing user info  operation for admin.
     * Takes as parameter {@link RequestContent} and pass it to the Receiver layer  {@link UserReceiver}.
     * Takes Receiver operation result, navigates to {@link com.epam.bets.constant.PageConstant#USER_SETTING_PAGE}
     * and saves navigation page to the session (required for use in locale change command
     * {@link com.epam.bets.command.common.ChangeLocaleCommand}).
     * If Receiver operation throws {@link ReceiverException}  navigates to {@link com.epam.bets.constant.PageConstant#SERVER_ERROR_PAGE}
     *
     * @param requestContent ({@link RequestContent}) request from client to get parameters to work with
     * @return {@link PageNavigator} with response parameters.
     */
    @Override
    public PageNavigator execute(RequestContent requestContent) {
        PageNavigator navigator;
        try {
            receiver.showUserInfo(requestContent);
            navigator = new PageNavigator(NEXT_PAGE, PageNavigator.PageType.FORWARD);
            requestContent.insertSessionAttribute(PREV_REQUEST, SHOW_USER_INFO +
                    requestContent.findParameterValue(PARAM_NAME_EMAIL));

        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(SERVER_ERROR_PAGE, PageNavigator.PageType.REDIRECT);
        }
        return navigator;
    }

}
