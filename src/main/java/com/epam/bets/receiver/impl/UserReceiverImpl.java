package com.epam.bets.receiver.impl;

import com.epam.bets.entity.*;
import com.epam.bets.generator.PasswordGenerator;
import com.epam.bets.dao.*;
import com.epam.bets.dao.impl.BetDAOImpl;
import com.epam.bets.dao.impl.CreditCardDAOImpl;
import com.epam.bets.dao.impl.UserDAOImpl;
import com.epam.bets.mail.EmailSender;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.request.RequestContent;
import com.epam.bets.validator.BirthDateValidator;
import com.epam.bets.validator.LoginValidator;
import com.epam.bets.validator.PasswordValidator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.bets.constant.ErrorConstant.*;
import static com.epam.bets.constant.RequestParamConstant.CommonParam.DATE_PATTERN;
import static com.epam.bets.constant.RequestParamConstant.MatchParam.PARAM_NAME_MATCH_ID;
import static com.epam.bets.constant.RequestParamConstant.UserParam.*;


public class UserReceiverImpl implements UserReceiver {

    private static final Logger LOGGER = LogManager.getLogger(UserReceiverImpl.class);

    @Override
    public void signIn(RequestContent requestContent) throws ReceiverException {
        User user = null;
        Map<String, String> errors = new HashMap<>();
        String login = requestContent.findParameterValue(PARAM_NAME_LOGIN);
        String password = requestContent.findParameterValue(PARAM_NAME_PASSWORD);
        try (DaoFactory factory = new DaoFactory()) {
            if (new LoginValidator().validate(login) && new PasswordValidator().validate(password)) {

                UserDAO userDAO = factory.getUserDao();
                String realPassword = userDAO.findPasswordByLogin(login);
                if (DigestUtils.md5Hex(password).equals(realPassword)) {
                    user = userDAO.findUserByLogin(login);
                    if (user != null) {
                        requestContent.insertSessionAttribute(PARAM_NAME_USER_ID, user.getId());
                        requestContent.insertSessionAttribute(PARAM_NAME_LOGIN, user.getLogin());
                        requestContent.insertSessionAttribute(PARAM_NAME_ROLE, user.getRole());
                    } else {
                        errors.put(ERROR, SIGN_IN_ERROR_MESSAGE);
                    }
                } else {
                    errors.put(INVALID_PARAMS_ERROR, INVALID_PARAMS_MESSAGE);
                }

            } else {
                errors.put(INVALID_PARAMS_ERROR, INVALID_PARAMS_MESSAGE);
            }
            if (!errors.isEmpty()) {
                requestContent.insertRequestAttribute("errors", errors);
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void signUp(RequestContent requestContent) throws ReceiverException {

        Map<String, String> errors = new HashMap<>();
        User user = new User();
        user.setLogin(requestContent.findParameterValue(PARAM_NAME_LOGIN));
        user.setPassword(DigestUtils.md5Hex(requestContent.findParameterValue(PARAM_NAME_PASSWORD)));
        user.setFirstName(requestContent.findParameterValue(PARAM_NAME_FIRST_NAME));
        user.setLastName(requestContent.findParameterValue(PARAM_NAME_LAST_NAME));
        LocalDate birthDate = LocalDate.parse(requestContent.findParameterValue(PARAM_NAME_BIRTH_DATE), DateTimeFormatter.ofPattern(DATE_PATTERN));
        user.setBirthDate(birthDate);
        CreditCards creditCards = new CreditCards();
        creditCards.addCreditCard(requestContent.findParameterValue(PARAM_NAME_CREDIT_CARD));
        user.setCreditCards(creditCards);
        boolean isValid = isValidUserParams(user, errors);
        if (isValid) {
            UserDAO userDAO = new UserDAOImpl();
            CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(userDAO, creditCardDAO);
            int userIndex;
            try {
                userIndex = userDAO.create(user);
                if (userIndex != 0) {
                    user.getCreditCards().setUserId(userIndex);
                    int cardIndex = creditCardDAO.create(user.getCreditCards());
                    if (cardIndex != 0) {
                        manager.commit();
                        requestContent.insertSessionAttribute(PARAM_NAME_USER_ID, userIndex);
                        requestContent.insertSessionAttribute(PARAM_NAME_LOGIN, user.getLogin());
                        requestContent.insertSessionAttribute(PARAM_NAME_ROLE, user.getRole());
                    } else {
                        manager.rollback();
                        errors.put(ERROR, SIGN_UP_ERROR_MESSAGE);
                    }
                } else {
                    manager.rollback();
                    errors.put(ERROR, EXISTING_USER_MESSAGE);
                }
            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }

            if (!errors.isEmpty()) {
                requestContent.insertRequestAttribute("errors", errors);
            }
        }
    }

    @Override
    public void logout(RequestContent requestContent) {
        requestContent.removeSessionAttribute(PARAM_NAME_USER_ID);
        requestContent.removeSessionAttribute(PARAM_NAME_LOGIN);
        requestContent.removeSessionAttribute(PARAM_NAME_ROLE);
    }

    @Override
    public void showProfileInfo(RequestContent requestContent) throws ReceiverException {
        User user;
        Map<String, String> errors = new HashMap<>();
        int userId = (int) requestContent.findSessionAttribute(PARAM_NAME_USER_ID);
        try (DaoFactory factory = new DaoFactory()) {
            UserDAO userDAO = factory.getUserDao();
            CreditCardDAO cardDAO = factory.gerCreditCardDao();
            user = userDAO.findUserById(userId);
            if (user != null) {
                requestContent.insertRequestAttribute(PARAM_NAME_LOGIN, user.getLogin());
                requestContent.insertRequestAttribute(PARAM_NAME_FIRST_NAME, user.getFirstName());
                requestContent.insertRequestAttribute(PARAM_NAME_LAST_NAME, user.getLastName());
                requestContent.insertRequestAttribute(PARAM_NAME_CREDIT_CARDS, cardDAO.findCardsByUserId(user.getId()));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
                requestContent.insertRequestAttribute(PARAM_NAME_BIRTH_DATE, user.getBirthDate().format(formatter));
                requestContent.insertRequestAttribute(PARAM_NAME_AVATAR_URL, user.getAvatarUrl());
                requestContent.insertRequestAttribute(PARAM_NAME_BALANCE, user.getBalance());
            } else {
                errors.put(INVALID_PARAMS_ERROR, INVALID_PARAMS_MESSAGE);//TODO
            }
            if (!errors.isEmpty()) {
                requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void editProfile(RequestContent requestContent) throws ReceiverException {
        User user = new User();
        Map<String, String> errors = new HashMap<>();
        int userId = (int) requestContent.findSessionAttribute(PARAM_NAME_USER_ID);
        String birthDate = requestContent.findParameterValue(PARAM_NAME_BIRTH_DATE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        user.setId(userId);
        user.setFirstName(requestContent.findParameterValue(PARAM_NAME_FIRST_NAME));
        user.setLastName(requestContent.findParameterValue(PARAM_NAME_LAST_NAME));
        user.setBirthDate(LocalDate.parse(birthDate, formatter));
        String[] creditCardArray = requestContent.findParameterValues(PARAM_NAME_CREDIT_CARD);
        CreditCards creditCards = new CreditCards();
        for (String card : creditCardArray) {
            if (card != null && !card.isEmpty()) {
                creditCards.addCreditCard(card);
            }
        }
        creditCards.setUserId(userId);
        boolean isValid = isValidUserParams(user, errors);
        if (isValid) {
            UserDAO userDAO = new UserDAOImpl();
            CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(userDAO, creditCardDAO);
            try {
                if (userDAO.update(user) && creditCardDAO.update(creditCards)) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.put(ERROR, CHANGE_PROFILE_ERROR_MESSAGE);
                }

            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }
        }
        if (!errors.isEmpty()) {
            requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
        } else {
            requestContent.insertRequestAttribute(SUCCESS, CHANGE_PROFILE_SUCCESS_MESSAGE);
        }
    }

    @Override
    public void editPassword(RequestContent requestContent) throws ReceiverException {
        String login = (String) requestContent.findSessionAttribute(PARAM_NAME_LOGIN);
        String oldPassword = requestContent.findParameterValue(PARAM_NAME_OLD_PASSWORD);
        String newPassword = requestContent.findParameterValue(PARAM_NAME_NEW_PASSWORD);
        boolean isValid = true;
        Map<String, String> errors = new HashMap<>();
        if (!new PasswordValidator().validate(oldPassword)) {
            isValid = false;
            errors.put(INVALID_PASSWORD_ERROR, INVALID_CURRENT_PASSWORD_MESSAGE);
        }
        if (new PasswordValidator().validate(newPassword)) {
            isValid = false;
            errors.put(INVALID_PASSWORD_ERROR, INVALID_NEW_PASSWORD_MESSAGE);
        }
        if (isValid) {
            UserDAO userDAO = new UserDAOImpl();
            TransactionManager manager = new TransactionManager();
            try {
                manager.beginTransaction(userDAO);
                String realPassword = userDAO.findPasswordByLogin(login);
                if (DigestUtils.md5Hex(oldPassword).equals(realPassword)) {
                    if (userDAO.updatePasswordByLogin(login, DigestUtils.md5Hex(newPassword))) {
                        manager.commit();
                    } else {
                        manager.rollback();
                        errors.put(ERROR, CHANGE_PASSWORD_ERROR_MESSAGE);
                    }
                } else {
                    errors.put(NOT_EQUAL_CURRENT_PASSWORD_ERROR, NOT_EQUAL_CURRENT_PASSWORD_MESSAGE);
                }

            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }
        }
        if (!errors.isEmpty()) {
            requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
        } else {
            requestContent.insertRequestAttribute(SUCCESS, CHANGE_PASSWORD_SUCCESS_MESSAGE);
        }
    }

    @Override
    public void makeBet(RequestContent requestContent) throws ReceiverException {
        List<Bet> bets = new ArrayList<>();
        Map<String, String> errors = new HashMap<>();
        String[] matchIds = requestContent.findParameterValues(PARAM_NAME_MATCH_ID);
        String[] summs = requestContent.findParameterValues(PARAM_NAME_SUMM);
        String[] betTypes = requestContent.findParameterValues(PARAM_NAME_BET_TYPE);
        int userId = (int) requestContent.findSessionAttribute(PARAM_NAME_USER_ID);
        int betLen = matchIds.length;
        for (int i = 0; i < betLen; i++) {
            bets.add(new Bet(new BigDecimal(summs[i]), Integer.parseInt(matchIds[i]), BetType.valueOf(betTypes[i]), userId));
        }
        BetDAO betDAO = new BetDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(betDAO);//TODO >MAX
        try {
            if (betDAO.createBets(bets)) {
                manager.commit();
            } else {
                manager.rollback();
                errors.put(ERROR, MAKE_BET_ERROR_MESSAGE);
            }
            if (!errors.isEmpty()) {
                requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
    }

    @Override
    public List<Bet> showBets(RequestContent requestContent) throws ReceiverException {
        List<Bet> bets = null;
        int userId = (int) requestContent.findSessionAttribute(PARAM_NAME_USER_ID);
        String type = requestContent.findParameterValue(PARAM_NAME_USER_BETS_TYPE);
        try (DaoFactory factory = new DaoFactory()) {
            BetDAO betDAO = factory.getBetDao();
            switch (type) {
                case "open": {
                    bets = betDAO.findOpenBets(userId);
                    break;
                }
                case "winned": {
                    bets = betDAO.findWinnedBets(userId);
                    break;
                }
                case "lost": {
                    bets = betDAO.findLostBets(userId);
                    break;
                }
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
        return bets;
    }

    @Override
    public void recoverPassword(RequestContent requestContent) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        Map<String, String> errors = new HashMap<>();
        String email = requestContent.findParameterValue(PARAM_NAME_EMAIL);
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(userDAO);
        String newPassword = new PasswordGenerator().generatePassword();
        String emailText = EMAIL_TEXT_START + newPassword + EMAIL_TEXT_END;
        try {
            if (userDAO.updatePasswordByLogin(email, DigestUtils.md5Hex(newPassword))) {
                if (EmailSender.sendMail(email, EMAIL_SUBJECT, emailText)) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.put(ERROR, RECOVER_PASSWORD_ERROR_MESSAGE);
                }
            } else {
                manager.rollback();
                errors.put(ERROR, RECOVER_PASSWORD_ERROR_MESSAGE);
            }
            if (!errors.isEmpty()) {
                requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
    }

    @Override
    public void calculateGain(RequestContent requestContent) throws ReceiverException {
        List<Bet> bets;
        Match match;
        int matchId = Integer.parseInt(requestContent.findParameterValue(PARAM_NAME_MATCH_ID));
        boolean isWinningBet;
        UserDAO userDAO = new UserDAOImpl();
        TransactionManager manager = new TransactionManager();
        try (DaoFactory factory = new DaoFactory()) {
            BetDAO betDAO = factory.getBetDao();
            MatchDAO matchDAO = factory.getMatchDao();
            match = matchDAO.findFinishedMatchInfo(matchId);
            bets = betDAO.findBetsByMatchId(matchId);
            for (Bet bet : bets) {
                isWinningBet = bet.getBetType().isWinningBet(match.getFirstTeamScore(), match.getSecondTeamScore(), match.getTotal());
                try {
                    manager.beginTransaction(userDAO, betDAO);
                    betDAO.updateIsBetWon(bet.getId(), isWinningBet);
                    if (isWinningBet) {
                        userDAO.updateBalance(bet.getUserId(), bet.getCoefficient().multiply(bet.getMoney()));
                    } else {
                        userDAO.updateBalance(bet.getUserId(), new BigDecimal("0"));
                    }
                    manager.commit();
                } catch (DaoException e) {
                    manager.rollback();
                    LOGGER.log(Level.ERROR, "Can't calculate gain fo user" + bet.getUserId() + ": " + e, e);
                }
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
    }

    @Override
    public void findAllCreditCards(RequestContent requestContent) throws ReceiverException {
        CreditCards cards;
        Map<String, String> errors = new HashMap<>();
        int userId = (int) requestContent.findSessionAttribute(PARAM_NAME_USER_ID);
        try (DaoFactory factory = new DaoFactory()) {
            CreditCardDAO creditCardDAO = factory.gerCreditCardDao();
            cards = creditCardDAO.findCardsByUserId(userId);
            if (cards == null) {
                errors.put(ERROR, RECOVER_PASSWORD_ERROR_MESSAGE);
                requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
            }
            requestContent.insertRequestAttribute(PARAM_NAME_CREDIT_CARDS, cards);
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void findUserBalance(RequestContent requestContent) throws ReceiverException {
        BigDecimal balance;
        int userId = (int) requestContent.findSessionAttribute(PARAM_NAME_USER_ID);
        try (DaoFactory factory = new DaoFactory()) {
            UserDAO creditCardDAO = factory.getUserDao();
            balance = creditCardDAO.findBalance(userId);
            requestContent.insertRequestAttribute(PARAM_NAME_BALANCE, balance);
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void changeLocale(RequestContent requestContent) {
        String prevLocale = requestContent.findParameterValue(PARAM_NAME_LOCALE);
        switch (prevLocale){
            case "RU":{
                requestContent.insertSessionAttribute(PARAM_NAME_LOCALE, "en_EN");
                break;
            }
            case "EN":{
                requestContent.insertSessionAttribute(PARAM_NAME_LOCALE, "ru_RU");
                break;
            }
            default:{
                requestContent.insertSessionAttribute(PARAM_NAME_LOCALE, "en_EN");
                break;
            }
        }
    }

    private boolean isValidUserParams(User user, Map<String, String> errors) {

        boolean isValid = true;
        if (!new LoginValidator().validate(user.getLogin())) {
            isValid = false;
            errors.put(INVALID_LOGIN_ERROR, INVALID_LOGIN_MESSAGE);
        }
        if (!new PasswordValidator().validate(user.getPassword())) {
            isValid = false;
            errors.put(INVALID_PASSWORD_ERROR, INVALID_PASSWORD_MESSAGE);
        }
        if (new BirthDateValidator().validate(user.getBirthDate())) {
            isValid = false;
            errors.put(INVALID_BIRTH_DATE_ERROR, INVALID_BIRTH_DATE_MESSAGE);
        }
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            isValid = false;
            errors.put(INVALID_FIRST_NAME_ERROR, INVALID_FIRST_NAME_MESSAGE);
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            isValid = false;
            errors.put(INVALID_LAST_NAME_ERROR, INVALID_LAST_NAME_MESSAGE);
        }
        if (user.getCreditCards().getCreditCarsSize() != 0) {
            boolean isCardsValid = false;
            for (String creditCard : user.getCreditCards().getCreditCardList()) {
                if (creditCard != null && !creditCard.isEmpty()) {
                    isCardsValid = true;
                }
            }
            if (!isCardsValid) {
                isValid = false;
                errors.put(INVALID_CREDIT_CARD_ERROR, INVALID_CREDIT_CARD_MESSAGE);
            }
        } else {
            isValid = false;
            errors.put(INVALID_CREDIT_CARD_ERROR, INVALID_CREDIT_CARD_MESSAGE);
        }
        return isValid;
    }
}
