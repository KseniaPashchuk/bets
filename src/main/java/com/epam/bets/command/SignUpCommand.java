package com.epam.bets.command;

import com.epam.bets.entity.CreditCards;
import com.epam.bets.entity.User;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.validator.LoginValidator;
import com.epam.bets.validator.PasswordValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.bets.constant.ErrorConstant.*;
import static com.epam.bets.constant.PageConstant.MAIN_PAGE;
import static com.epam.bets.constant.PageConstant.REGISTRATION_PAGE;

public class SignUpCommand implements AbstractCommand {

    private static final String PARAM_NAME_ID = "userId";
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_ROLE = "role";
    private static final String PARAM_NAME_FIRST_NAME = "name";
    private static final String PARAM_NAME_LAST_NAME = "surname";
    private static final String PARAM_NAME_BIRTH_DATE = "birth_date";
    private static final String PARAM_NAME_CREDIT_CARD = "credit_card";
    private static final String NEXT_PAGE = MAIN_PAGE;
    private static final String ERROR_PAGE = REGISTRATION_PAGE;
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final Locale locale = java.util.Locale.US;//TODO
    private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class);

//TODO проверка на дату рождения

    private UserReceiverImpl receiver = new UserReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);//TODO check birth date
        try {
            if (new LoginValidator().validate(login)) {
                if (new PasswordValidator().validate(password)) {
                    User user = new User();
                    user.setLogin(login);
                    user.setPassword(password);
                    user.setFirstName(request.getParameter(PARAM_NAME_FIRST_NAME));
                    user.setLastName(request.getParameter(PARAM_NAME_LAST_NAME));
                    String birthDate = request.getParameter(PARAM_NAME_BIRTH_DATE);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN, locale);
                    user.setBirthDate(LocalDate.parse(birthDate, formatter));
                    CreditCards creditCards = new CreditCards();
                    creditCards.addCreditCard(request.getParameter(PARAM_NAME_CREDIT_CARD));
                    user.setCreditCards(creditCards);
                    int userId = receiver.signUp(user);
                    if (userId != 0) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute(PARAM_NAME_ID, userId);
                        session.setAttribute(PARAM_NAME_LOGIN, user.getLogin());
                        session.setAttribute(PARAM_NAME_ROLE, user.getRole());
                        navigator = new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
                    } else {
                        request.setAttribute(EXISTING_USER_ERROR, EXISTING_USER_MESSAGE);
                        navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
                    }
                } else {
                    request.setAttribute(INVALID_PASSWORD_ERROR, INVALID_PASSWORD_MESSAGE);
                    navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
                }
            } else {
                request.setAttribute(INVALID_LOGIN_ERROR, INVALID_LOGIN_MESSAGE);
                navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
        }
        return navigator;
    }
}

