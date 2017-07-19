package com.epam.bets.command;

import com.epam.bets.entity.User;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.bets.JspConstant.INDEX_PAGE;
import static com.epam.bets.JspConstant.PROFILE_PAGE;

public class ShowProfileCommand implements AbstractCommand {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_FIRST_NAME = "name";
    private static final String PARAM_NAME_LAST_NAME = "surname";
    private static final String PARAM_NAME_CREDIT_CARD = "credit_card";
    private static final String PARAM_NAME_BIRTH_DATE = "birth_date";
    private static final String PARAM_NAME_AVATAR_URL = "avatar_url";
    private static final String NEXT_PAGE = PROFILE_PAGE;
    private static final String ERROR_PAGE = INDEX_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(ShowProfileCommand.class);
    private UserReceiverImpl receiver = new UserReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = new PageNavigator(NEXT_PAGE, PageType.FORWARD);
        HttpSession session = request.getSession();
        User user;
        try {
            user = receiver.showProfileInfo((String) session.getAttribute(PARAM_NAME_LOGIN));
            if (user != null) {
                request.setAttribute(PARAM_NAME_LOGIN, user.getLogin());
                request.setAttribute(PARAM_NAME_FIRST_NAME, user.getFirstName());
                request.setAttribute(PARAM_NAME_LAST_NAME, user.getLastName());
                request.setAttribute(PARAM_NAME_CREDIT_CARD, user.getCreditCard());
                request.setAttribute(PARAM_NAME_BIRTH_DATE, user.getBirthDate());
                request.setAttribute(PARAM_NAME_AVATAR_URL, user.getAvatarUrl());
            }
            else{
                navigator.setPageUrl(ERROR_PAGE);
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e);
            navigator.setPageUrl(ERROR_PAGE);
        }
        return navigator;
    }
}
