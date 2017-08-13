package com.epam.bets.command.admin;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.entity.News;
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
import java.time.LocalDate;

import static com.epam.bets.constant.ErrorConstant.INVALID_PARAMS_ERROR;
import static com.epam.bets.constant.ErrorConstant.INVALID_PARAMS_MESSAGE;
import static com.epam.bets.constant.PageConstant.*;

public class CreateNewsCommand implements AbstractCommand {

    private static final String PARAM_NAME_TITLE = "news_title";
    private static final String PARAM_NAME_PICTURE = "news_picture";//TODO
    private static final String PARAM_NAME_TEXT = "news_text";
    private static final String NEWS_UPLOAD_DIR = "\\news";
    private static final String UPLOAD_DIR = "uploads.dir";
    private static final String NEXT_PAGE = NEWS_PAGE;
    private static final String ERROR_PAGE = NEWS_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(CreateNewsCommand.class);
    private NewsReceiverImpl receiver = new NewsReceiverImpl();
    private LoadReceiverImpl loadReceiver = new LoadReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        String uploadDir = request.getServletContext().getInitParameter(UPLOAD_DIR) + NEWS_UPLOAD_DIR;
        try {
            Part part = request.getPart(PARAM_NAME_PICTURE);
            News news = new News();
            news.setTitle(request.getParameter(PARAM_NAME_TITLE));
            news.setDate(LocalDate.now());
            news.setPictureUrl(part.getSubmittedFileName());
            news.setText(request.getParameter(PARAM_NAME_TEXT));
            if (receiver.createNews(news) && loadReceiver.loadPicture(part, uploadDir)) {
                navigator = new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
            } else {
                request.setAttribute(INVALID_PARAMS_ERROR, INVALID_PARAMS_MESSAGE);
                navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }
        } catch (ReceiverException |ServletException | IOException e){
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
        }
        return navigator;
    }
}
