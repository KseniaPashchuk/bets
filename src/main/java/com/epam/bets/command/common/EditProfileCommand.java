package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.bets.constant.ErrorConstant.ERROR_MAP_NAME;
import static com.epam.bets.constant.PageConstant.*;

public class EditProfileCommand implements AbstractCommand {

    private static final String NEXT_PAGE = AFTER_EDIT_PROFILE_PAGE;
    private static final String ERROR_PAGE = MAIN_PAGE;//TODO
    private UserReceiver receiver = new UserReceiverImpl();
    private static final Logger LOGGER = LogManager.getLogger(EditProfileCommand.class);


    @Override
    public PageNavigator execute(RequestContent requestContent) {
        PageNavigator navigator;
        try {

            receiver.editProfile(requestContent);
            if (requestContent.findRequestAttribute(ERROR_MAP_NAME) == null) {
                navigator = new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
            } else {
                navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }
        } catch (ReceiverException e){
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(SERVER_ERROR_PAGE, PageType.REDIRECT);
        }
        return navigator;
    }
}