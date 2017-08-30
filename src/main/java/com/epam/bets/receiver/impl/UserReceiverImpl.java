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
import com.epam.bets.validator.UserValidator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.epam.bets.constant.ErrorConstant.ERROR_LIST_NAME;
import static com.epam.bets.constant.ErrorConstant.UserError.*;
import static com.epam.bets.constant.RequestParamConstant.CommonParam.DATE_PATTERN;
import static com.epam.bets.constant.RequestParamConstant.MatchParam.PARAM_NAME_MATCH_ID;
import static com.epam.bets.constant.RequestParamConstant.MatchParam.PARAM_NAME_MAX_BET;
import static com.epam.bets.constant.RequestParamConstant.UserParam.*;


public class UserReceiverImpl implements UserReceiver {

    private static final Logger LOGGER = LogManager.getLogger(UserReceiverImpl.class);

    @Override
    public void signIn(RequestContent requestContent) throws ReceiverException {
        User user = null;
        List<String> errors = new ArrayList<>();
        String login = requestContent.findParameterValue(PARAM_NAME_LOGIN);
        String password = requestContent.findParameterValue(PARAM_NAME_PASSWORD);
        try (DaoFactory factory = new DaoFactory()) {
            if (new UserValidator().validateSignInParams(login, password, errors)) {
                UserDAO userDAO = factory.getUserDao();
                String realPassword = userDAO.findPasswordByLogin(login);
                if (DigestUtils.md5Hex(password).equals(realPassword)) {
                    user = userDAO.findUserByLogin(login);
                    if (user != null) {
                        requestContent.insertSessionAttribute(PARAM_NAME_USER_ID, user.getId());
                        requestContent.insertSessionAttribute(PARAM_NAME_LOGIN, user.getLogin());
                        requestContent.insertSessionAttribute(PARAM_NAME_ROLE, user.getRole());
                    } else {
                        errors.add(SIGN_IN_ERROR);
                    }
                } else {
                    errors.add(INVALID_PARAMS_ERROR);
                }

            } else {
                errors.add(INVALID_PARAMS_ERROR);
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

        List<String> errors = new ArrayList<>();
        User user = new User();
        user.setLogin(requestContent.findParameterValue(PARAM_NAME_LOGIN));
        user.setPassword(requestContent.findParameterValue(PARAM_NAME_PASSWORD));
        user.setFirstName(requestContent.findParameterValue(PARAM_NAME_FIRST_NAME));
        user.setLastName(requestContent.findParameterValue(PARAM_NAME_LAST_NAME));
        LocalDate birthDate = LocalDate.parse(requestContent.findParameterValue(PARAM_NAME_BIRTH_DATE), DateTimeFormatter.ofPattern(DATE_PATTERN));
        user.setBirthDate(birthDate);
        CreditCards creditCards = new CreditCards();
        creditCards.addCreditCard(requestContent.findParameterValue(PARAM_NAME_CREDIT_CARD));
        user.setCreditCards(creditCards);
        if (new UserValidator().validateSignUpParams(user, errors)) {
            user.setPassword(DigestUtils.md5Hex(requestContent.findParameterValue(PARAM_NAME_PASSWORD)));
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
                        errors.add(SIGN_UP_ERROR);
                    }
                } else {
                    manager.rollback();
                    errors.add(EXISTING_USER_ERROR);
                }
            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }

            if (!errors.isEmpty()) {
                requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
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
        List<String> errors = new ArrayList<>();
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
                errors.add(INVALID_PARAMS_ERROR);
            }
            if (!errors.isEmpty()) {
                requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void editProfile(RequestContent requestContent) throws ReceiverException {
        User user = new User();
        List<String> errors = new ArrayList<>();
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
        user.setCreditCards(creditCards);
        if (new UserValidator().validatePersonalParams(user, errors)) {
            UserDAO userDAO = new UserDAOImpl();
            CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(userDAO, creditCardDAO);
            try {
                if (userDAO.update(user) && creditCardDAO.update(creditCards)) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.add(CHANGE_PROFILE_ERROR);
                }

            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }
        }
        if (!errors.isEmpty()) {
            requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
        } else {
            requestContent.insertRequestAttribute(CHANGE_PROFILE_SUCCESS, CHANGE_PROFILE_SUCCESS);
        }
    }

    @Override
    public void editPassword(RequestContent requestContent) throws ReceiverException {
        String login = (String) requestContent.findSessionAttribute(PARAM_NAME_LOGIN);
        String oldPassword = requestContent.findParameterValue(PARAM_NAME_OLD_PASSWORD);
        String newPassword = requestContent.findParameterValue(PARAM_NAME_NEW_PASSWORD);
        boolean isValid = true;
        List<String> errors = new ArrayList<>();
        if (!new UserValidator().validatePassword(oldPassword)) {
            isValid = false;
            errors.add(INVALID_CURRENT_PASSWORD_ERROR);
        }
        if (!new UserValidator().validatePassword(newPassword)) {
            isValid = false;
            errors.add(INVALID_NEW_PASSWORD_ERROR);
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
                        errors.add(CHANGE_PASSWORD_ERROR);
                    }
                } else {
                    errors.add(NOT_EQUAL_CURRENT_PASSWORD_ERROR);
                }

            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }
        }
        if (!errors.isEmpty()) {
            requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
        } else {
            requestContent.insertRequestAttribute(CHANGE_PASSWORD_SUCCESS, CHANGE_PASSWORD_SUCCESS);
        }
    }

    @Override
    public void makeBet(RequestContent requestContent) throws ReceiverException {//TODO check bet date
        List<Bet> bets = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        String[] matchIds = requestContent.findParameterValues(PARAM_NAME_MATCH_ID);
        String[] summs = requestContent.findParameterValues(PARAM_NAME_SUMM);
        String[] betTypes = requestContent.findParameterValues(PARAM_NAME_BET_TYPE);
        String[] maxBets = requestContent.findParameterValues(PARAM_NAME_MAX_BET);
        BigDecimal balance;
        BigDecimal betsSumm = new BigDecimal("0");
        int userId = (int) requestContent.findSessionAttribute(PARAM_NAME_USER_ID);
        try (DaoFactory factory = new DaoFactory()) {
            UserDAO creditCardDAO = factory.getUserDao();
            balance = creditCardDAO.findBalance(userId);
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
        boolean isValid = true;
        BigDecimal sumParam;
        for (int i = 0; i < matchIds.length; i++) {
            sumParam = new BigDecimal(summs[i]);
            betsSumm = betsSumm.add(sumParam);
            if (sumParam.signum() == -1 || sumParam.signum() == 0) {
                isValid = false;
                errors.add(SUMM_NOT_POSITIVE);
                break;
            }
            if (sumParam.compareTo(new BigDecimal(maxBets[i])) == 1) {
                isValid = false;
                errors.add(TOO_BIG_BET_SUMM);
                break;
            }
        }
        if (balance.compareTo(betsSumm) == -1) {
            isValid = false;
            errors.add(NOT_ENOUGH_MONEY);
        }
        if (isValid) {
            BetDAO betDAO = new BetDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(betDAO);
            try {
                for (int i = 0; i < matchIds.length; i++) {
                    bets.add(new Bet(new BigDecimal(summs[i]), Integer.parseInt(matchIds[i]), BetType.valueOf(betTypes[i]), userId));
                }
                if (betDAO.createBets(bets)) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.add(MAKE_BET_ERROR);
                }
                if (!errors.isEmpty()) {
                    requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
                }
            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }
        }
        if (!errors.isEmpty()) {
            requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
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
        List<String> errors = new ArrayList<>();
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
                    errors.add(RECOVER_PASSWORD_ERROR);
                }
            } else {
                manager.rollback();
                errors.add(RECOVER_PASSWORD_ERROR);
            }
            if (!errors.isEmpty()) {
                requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
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
        List<String> errors = new ArrayList<>();
        int userId = (int) requestContent.findSessionAttribute(PARAM_NAME_USER_ID);
        try (DaoFactory factory = new DaoFactory()) {
            CreditCardDAO creditCardDAO = factory.gerCreditCardDao();
            cards = creditCardDAO.findCardsByUserId(userId);
            if (cards == null) {
                errors.add(RECOVER_PASSWORD_ERROR);
                requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
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
        switch (prevLocale) {
            case "RU": {
                requestContent.insertSessionAttribute(PARAM_NAME_LOCALE, "en_EN");
                break;
            }
            case "EN": {
                requestContent.insertSessionAttribute(PARAM_NAME_LOCALE, "ru_RU");
                break;
            }
            default: {
                requestContent.insertSessionAttribute(PARAM_NAME_LOCALE, "en_EN");
                break;
            }
        }
    }


}
