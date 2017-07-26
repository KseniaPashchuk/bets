package com.epam.bets.command;

import com.epam.bets.entity.CreditCards;
import com.epam.bets.entity.User;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.validator.PasswordValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.epam.bets.constant.PageConstant.*;

public class EditProfileCommand implements AbstractCommand {

    private static final String PARAM_NAME_ID = "userId";
    private static final String PARAM_NAME_FIRST_NAME = "edit_name";
    private static final String PARAM_NAME_LAST_NAME = "edit_surname";
    private static final String PARAM_NAME_BIRTH_DATE = "edit_birth_date";
    private static final String PARAM_NAME_CREDIT_CARD = "credit_card";
    private static final String NEXT_PAGE = AFTER_EDIT_PROFILE_PAGE;
    private static final String ERROR_PAGE = MAIN_PAGE;//TODO
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private UserReceiverImpl receiver = new UserReceiverImpl();
    private static final Locale locale = java.util.Locale.US;//TODO
    private static final Logger LOGGER = LogManager.getLogger(SignInCommand.class);


    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        int userId = (int) request.getSession().getAttribute(PARAM_NAME_ID);
        String birthDate = request.getParameter(PARAM_NAME_BIRTH_DATE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN, locale);//TODO
        try {
            User user = new User();
            user.setId(userId);
            user.setFirstName(request.getParameter(PARAM_NAME_FIRST_NAME));
            user.setLastName(request.getParameter(PARAM_NAME_LAST_NAME));
            user.setBirthDate(LocalDate.parse(birthDate, formatter));
            String[] creditCardArray = request.getParameterValues(PARAM_NAME_CREDIT_CARD);
            CreditCards creditCards = new CreditCards();
            for (String card : creditCardArray) {
                if (card != null && !card.isEmpty()) {
                    creditCards.addCreditCard(card);
                }
            }
            user.setCreditCards(creditCards);
            if (receiver.editProfile(user)) {
                navigator = new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
            } else {
                navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
        }
        return navigator;
    }
}
