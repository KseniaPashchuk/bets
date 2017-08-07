package com.epam.bets.command.admin;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.command.EditUserAvatarCommand;
import com.epam.bets.command.PageNavigator;
import com.epam.bets.command.PageType;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.LoadReceiverImpl;
import com.epam.bets.receiver.impl.NewsReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;

import static com.epam.bets.constant.PageConstant.AFTER_EDIT_PROFILE_PAGE;
import static com.epam.bets.constant.PageConstant.MAIN_PAGE;
import static com.epam.bets.constant.PageConstant.NEWS_PAGE;

public class EditNewsPictureCommand implements AbstractCommand {
    private static final String PARAM_NAME_ID = "news_id";
    private static final String PARAM_NAME_AVATAR = "newAvatarURL";
    private static final String NEWS_UPLOAD_DIR = "\\news";
    private static final String NEXT_PAGE = AFTER_EDIT_PROFILE_PAGE;
    private static final String ERROR_PAGE = NEWS_PAGE;
    private static final String UPLOAD_DIR = "uploads.dir";
    private static final Logger LOGGER = LogManager.getLogger(EditUserAvatarCommand.class);
    private NewsReceiverImpl newsReceiver = new NewsReceiverImpl();
    private LoadReceiverImpl loadReceiver = new LoadReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        String uploadDir = request.getServletContext().getInitParameter(UPLOAD_DIR) + NEWS_UPLOAD_DIR;
        int newsId = Integer.parseInt(request.getParameter(PARAM_NAME_ID));
        try {
            Part part = request.getPart(PARAM_NAME_AVATAR);
            if (loadReceiver.loadPicture(part, uploadDir)) {
                if (newsReceiver.editPicture(newsId, part.getSubmittedFileName())) {
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
