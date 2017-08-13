package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.LoadReceiver;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.LoadReceiverImpl;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import java.io.IOException;

import static com.epam.bets.constant.PageConstant.*;

public class EditUserAvatarCommand implements AbstractCommand {

    private static final String PARAM_NAME_ID = "userId";
    private static final String PARAM_NAME_AVATAR = "new_user_avatar";
    private static final String USER_UPLOAD_DIR = "\\user";
    private static final String NEXT_PAGE = AFTER_EDIT_PROFILE_PAGE;
    private static final String ERROR_PAGE = MAIN_PAGE;
    private static final String UPLOAD_DIR = "uploads.dir";
    private static final Logger LOGGER = LogManager.getLogger(EditUserAvatarCommand.class);
    private UserReceiver userReceiver = new UserReceiverImpl();
    private LoadReceiver loadReceiver = new LoadReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        String uploadDir = request.getServletContext().getInitParameter(UPLOAD_DIR) + USER_UPLOAD_DIR;
        int userId = (int) request.getSession().getAttribute(PARAM_NAME_ID);
        try {
            Part part = request.getPart(PARAM_NAME_AVATAR);
            if (loadReceiver.loadPicture(part, uploadDir)) {
                if (userReceiver.editAvatar(userId, part.getSubmittedFileName())) {
                    navigator = new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
                } else {
                    navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
                }
            } else {
                navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }
        } catch (ReceiverException | IOException | ServletException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
        }
        return navigator;
    }
}
