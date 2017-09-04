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
import com.epam.bets.validator.MailValidator;
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
import static com.epam.bets.constant.RequestParamConstant.UserParam.*;

/**
 * The class provides {@link UserReceiver} implementation.
 *
 * @author Pashchuk Ksenia
 */
public class UserReceiverImpl implements UserReceiver {

    private static final Logger LOGGER = LogManager.getLogger(UserReceiverImpl.class);

    /**
     * Provides signing in operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see UserValidator
     * @see DaoFactory
     * @see UserDAO
     */
    @Override
    public void signIn(RequestContent requestContent) throws ReceiverException {
        User user = null;
        List<String> errors = new ArrayList<>();
        try (DaoFactory factory = new DaoFactory()) {
            if (new UserValidator().validateSignInParams(requestContent, errors)) {
                String login = requestContent.findParameterValue(PARAM_NAME_LOGIN);
                String password = requestContent.findParameterValue(PARAM_NAME_PASSWORD);
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

    /**
     * Provides signing up operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see UserValidator
     * @see TransactionManager
     * @see UserDAO
     * @see CreditCardDAO
     */
    @Override
    public void signUp(RequestContent requestContent) throws ReceiverException {

        List<String> errors = new ArrayList<>();
        if (new UserValidator().validateSignUpParams(requestContent, errors)) {
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

    /**
     * Provides log out operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    @Override
    public void logout(RequestContent requestContent) {
        requestContent.removeSessionAttribute(PARAM_NAME_USER_ID);
        requestContent.removeSessionAttribute(PARAM_NAME_LOGIN);
        requestContent.removeSessionAttribute(PARAM_NAME_ROLE);
    }

    /**
     * Provides showing user profile operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see UserValidator
     * @see DaoFactory
     * @see UserDAO
     * @see CreditCardDAO
     */
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
                errors.add(SHOW_PROFILE_ERROR);
            }
            if (!errors.isEmpty()) {
                requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Provides editing user profile operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see UserValidator
     * @see TransactionManager
     * @see UserDAO
     */
    @Override
    public void editProfile(RequestContent requestContent) throws ReceiverException {
        User user = new User();
        List<String> errors = new ArrayList<>();
        if (new UserValidator().validatePersonalParams(requestContent, errors)) {
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
        }
    }

    /**
     * Provides editing user password operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see UserValidator
     * @see TransactionManager
     * @see UserDAO
     */
    @Override
    public void editPassword(RequestContent requestContent) throws ReceiverException {
        String login = (String) requestContent.findSessionAttribute(PARAM_NAME_LOGIN);
        List<String> errors = new ArrayList<>();
        if (new UserValidator().validateEditPasswordParams(requestContent, errors)) {
            String oldPassword = requestContent.findParameterValue(PARAM_NAME_OLD_PASSWORD);
            String newPassword = requestContent.findParameterValue(PARAM_NAME_NEW_PASSWORD);
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
        }
    }

    /**
     * Provides making bet operation for user
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see UserValidator
     * @see DaoFactory
     * @see UserDAO
     * @see TransactionManager
     */
    @Override
    public void makeBet(RequestContent requestContent) throws ReceiverException {
        List<Bet> bets = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        BigDecimal balance;
        int userId = (int) requestContent.findSessionAttribute(PARAM_NAME_USER_ID);
        try (DaoFactory factory = new DaoFactory()) {
            UserDAO creditCardDAO = factory.getUserDao();
            balance = creditCardDAO.findBalance(userId);
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
        if (new UserValidator().validateMakeBetParams(requestContent, balance, errors)) {
            String[] matchIds = requestContent.findParameterValues(PARAM_NAME_MATCH_ID);
            String[] summs = requestContent.findParameterValues(PARAM_NAME_SUMM);
            String[] betTypes = requestContent.findParameterValues(PARAM_NAME_BET_TYPE);
            BigDecimal sumParam;
            BigDecimal betsSumm = new BigDecimal("0");
            BetDAO betDAO = new BetDAOImpl();
            UserDAO userDao = new UserDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(betDAO, userDao);
            try {
                for (String summ : summs) {
                    sumParam = new BigDecimal(summ);
                    betsSumm = betsSumm.add(sumParam);
                }
                for (int i = 0; i < matchIds.length; i++) {
                    bets.add(new Bet(new BigDecimal(summs[i]), Integer.parseInt(matchIds[i]), BetType.valueOf(betTypes[i]), userId));
                }
                if (userDao.makeBet(userId, betsSumm) && betDAO.createBets(bets)) {
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

    /**
     * Provides showing user bets operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see UserValidator
     * @see DaoFactory
     * @see UserDAO
     */
    @Override
    public List<Bet> showBets(RequestContent requestContent) throws ReceiverException {
        List<Bet> bets = null;
        int userId;

        String type = requestContent.findParameterValue(PARAM_NAME_USER_BETS_TYPE);
        try (DaoFactory factory = new DaoFactory()) {
            if (UserRole.valueOf(requestContent.findSessionAttribute(PARAM_NAME_ROLE).toString()).equals(UserRole.ADMIN)) {
                UserDAO userDAO = factory.getUserDao();
                userId = userDAO.findUserIdByLogin(requestContent.findParameterValue(PARAM_NAME_EMAIL));
            } else {
                userId = (int) requestContent.findSessionAttribute(PARAM_NAME_USER_ID);
            }
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

    /**
     * Provides refill cash operation for user
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see UserValidator
     * @see TransactionManager
     * @see UserDAO
     */
    @Override
    public void refillCash(RequestContent requestContent) throws ReceiverException {
        List<String> errors = new ArrayList<>();
        if (new UserValidator().validateRefillAmount(requestContent, errors)) {
            int userId = (int) requestContent.findSessionAttribute(PARAM_NAME_USER_ID);
            BigDecimal amount = new BigDecimal(requestContent.findParameterValue(PARAM_NAME_REFILL_AMOUNT));
            UserDAO userDAO = new UserDAOImpl();
            TransactionManager manager = new TransactionManager();
            try {
                manager.beginTransaction(userDAO);
                if (userDAO.updateBalance(userId, amount)) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.add(REFILL_CASH_ERROR);
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

    /**
     * Provides recovering user password operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see UserValidator
     * @see TransactionManager
     * @see UserDAO
     */
    @Override
    public void recoverPassword(RequestContent requestContent) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        List<String> errors = new ArrayList<>();
        String email = requestContent.findParameterValue(PARAM_NAME_EMAIL);
        if (new MailValidator().validateEmail(email, errors)) {
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(userDAO);
            String newPassword = PasswordGenerator.generatePassword();
            String emailText = PASSWORD_RECOVER_MAIL_TEXT_START + newPassword + PASSWORD_RECOVER_MAIL_TEXT_END;
            try {
                if (userDAO.loginExists(email)) {
                    if (userDAO.updatePasswordByLogin(email, DigestUtils.md5Hex(newPassword))) {
                        if (EmailSender.sendMail(email, PASSWORD_RECOVER_MAIL_SUBJECT, emailText)) {
                            manager.commit();
                        } else {
                            manager.rollback();
                            errors.add(RECOVER_PASSWORD_ERROR);
                        }
                    } else {
                        manager.rollback();
                        errors.add(RECOVER_PASSWORD_ERROR);
                    }
                } else {
                    errors.add(NO_SUCH_USER_ERROR);
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

    /**
     * Provides calculationg user gain operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see UserValidator
     * @see DaoFactory
     * @see UserDAO
     * @see TransactionManager
     * @see BetDAO
     */
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

    /**
     * Provides finding all user credit cards operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see UserValidator
     * @see DaoFactory
     * @see UserDAO
     */
    @Override
    public void findAllCreditCards(RequestContent requestContent) throws ReceiverException {
        CreditCards cards;
        List<String> errors = new ArrayList<>();
        int userId = (int) requestContent.findSessionAttribute(PARAM_NAME_USER_ID);
        try (DaoFactory factory = new DaoFactory()) {
            CreditCardDAO creditCardDAO = factory.gerCreditCardDao();
            cards = creditCardDAO.findCardsByUserId(userId);
            if (cards == null) {
                errors.add(SHOW_CREDIT_CARD_ERROR);
                requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
            }
            requestContent.insertRequestAttribute(PARAM_NAME_CREDIT_CARDS, cards);
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Provides finding user balance operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see UserValidator
     * @see DaoFactory
     * @see UserDAO
     */
    @Override
    public void findUserBalance(RequestContent requestContent) throws ReceiverException {
        BigDecimal balance;
        int userId = (int) requestContent.findSessionAttribute(PARAM_NAME_USER_ID);
        try (DaoFactory factory = new DaoFactory()) {
            UserDAO userDAO = factory.getUserDao();
            balance = userDAO.findBalance(userId);
            requestContent.insertRequestAttribute(PARAM_NAME_BALANCE, balance);
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Provides chanding locale operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
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

    /**
     * Provides showing user info operation for admin.
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see UserValidator
     * @see DaoFactory
     * @see UserDAO
     * @see CreditCardDAO
     */
    @Override
    public void showUserInfo(RequestContent requestContent) throws ReceiverException {
        User user;
        List<String> errors = new ArrayList<>();
        String userLogin = requestContent.findParameterValue(PARAM_NAME_EMAIL);
        if (new UserValidator().validateLogin(userLogin, errors)) {
            try (DaoFactory factory = new DaoFactory()) {
                UserDAO userDAO = factory.getUserDao();
                CreditCardDAO cardDAO = factory.gerCreditCardDao();
                user = userDAO.findUserByLogin(userLogin);
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
                    errors.add(NO_SUCH_USER_ERROR);
                }
                if (!errors.isEmpty()) {
                    requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
                }
            } catch (DaoException e) {
                throw new ReceiverException(e);
            }
        }
        if (!errors.isEmpty()) {
            requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
        }
    }
}
