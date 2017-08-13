package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.entity.User;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.time.format.DateTimeFormatter;

import static com.epam.bets.constant.PageConstant.INDEX_PAGE;
import static com.epam.bets.constant.PageConstant.PROFILE_PAGE;

public class ShowUserProfileCommand implements AbstractCommand {

    private static final String PARAM_NAME_ID = "userId";
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_FIRST_NAME = "name";
    private static final String PARAM_NAME_LAST_NAME = "surname";
    private static final String PARAM_NAME_CREDIT_CARDS = "creditCardList";
    private static final String PARAM_NAME_BIRTH_DATE = "birthDate";
    private static final String PARAM_NAME_AVATAR_URL = "avatarUrl";
    private static final String PARAM_NAME_BALANCE = "balance";
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final String NEXT_PAGE = PROFILE_PAGE;
    private static final String ERROR_PAGE = INDEX_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(ShowUserProfileCommand.class);
    private UserReceiver receiver = new UserReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        HttpSession session = request.getSession();
        User user;
        try {
            user = receiver.showProfileInfo((int) session.getAttribute(PARAM_NAME_ID));
            if (user != null) {
                request.setAttribute(PARAM_NAME_LOGIN, user.getLogin());
                request.setAttribute(PARAM_NAME_FIRST_NAME, user.getFirstName());
                request.setAttribute(PARAM_NAME_LAST_NAME, user.getLastName());
                request.setAttribute(PARAM_NAME_CREDIT_CARDS, user.getCreditCards());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
                request.setAttribute(PARAM_NAME_BIRTH_DATE, user.getBirthDate().format(formatter));
                request.setAttribute(PARAM_NAME_AVATAR_URL, user.getAvatarUrl());
                request.setAttribute(PARAM_NAME_BALANCE, user.getBalance());
                navigator = new PageNavigator(NEXT_PAGE, PageType.FORWARD);
            }
            else{
                navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
        }
        return navigator;
    }
}
