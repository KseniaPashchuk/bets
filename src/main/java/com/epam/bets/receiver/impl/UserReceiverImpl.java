package com.epam.bets.receiver.impl;

import com.epam.bets.generator.PasswordGenerator;
import com.epam.bets.dao.*;
import com.epam.bets.dao.impl.BetDAOImpl;
import com.epam.bets.dao.impl.CreditCardDAOImpl;
import com.epam.bets.dao.impl.UserDAOImpl;
import com.epam.bets.mail.EmailSender;
import com.epam.bets.entity.Bet;
import com.epam.bets.entity.CreditCards;
import com.epam.bets.entity.Match;
import com.epam.bets.entity.User;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.UserReceiver;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;


public class UserReceiverImpl implements UserReceiver {
    private static final Logger LOGGER = LogManager.getLogger(UserReceiverImpl.class);
    private static final String EMAIL_SUBJECT = "Password Recovery";
    private static final String EMAIL_TEXT_START = "We've received a requset to reset your password. Your new password: ";
    private static final String EMAIL_TEXT_END = "\n The BETS Team.";
    private static final String FIRST_TEAM = "first_team";
    private static final String SECOND_TEAM = "second_team";

    @Override
    public User signIn(String login, String password) throws ReceiverException {
        User user = null;
        try (DaoFactory factory = new DaoFactory()) {
            UserDAO userDAO = factory.getUserDao();
            String realPassword = userDAO.findPasswordByLogin(login);
            if (DigestUtils.md5Hex(password).equals(realPassword)) {
                user = userDAO.findUserByLogin(login);
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
        return user;
    }

    @Override
    public int signUp(User user) throws ReceiverException {
        int userIndex;
        UserDAO userDAO = new UserDAOImpl();
        CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(userDAO, creditCardDAO);
        String password = user.getPassword();
        user.setPassword(DigestUtils.md5Hex(password));
        try {
            userIndex = userDAO.create(user);
            if (userIndex != 0) {
                user.getCreditCards().setUserId(userIndex);
                int cardIndex = creditCardDAO.create(user.getCreditCards());
                if (cardIndex != 0) {
                    manager.commit();
                } else {
                    manager.rollback();
                }
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return userIndex;
    }

    @Override
    public User showProfileInfo(int userId) throws ReceiverException {
        User user;
        try (DaoFactory factory = new DaoFactory()) {
            UserDAO userDAO = factory.getUserDao();
            CreditCardDAO cardDAO = factory.gerCreditCardDao();
            user = userDAO.findUserById(userId);
            CreditCards creditCards = cardDAO.findCardsByUserId(user.getId());
            user.setCreditCards(creditCards);
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
        return user;
    }

    @Override
    public boolean editProfile(User user) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(userDAO, creditCardDAO);
        try {
            if (userDAO.update(user) && creditCardDAO.update(user.getCreditCards())) {
                manager.commit();
                return true;
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return false;
    }

    @Override
    public boolean editPassword(String login, String oldPassword, String newPassword) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(userDAO);
        try {
            String realPassword = userDAO.findPasswordByLogin(login);
            if (DigestUtils.md5Hex(oldPassword).equals(realPassword)) {
                if (userDAO.updatePasswordByLogin(login, DigestUtils.md5Hex(newPassword))) {
                    manager.commit();
                    return true;
                } else {
                    manager.rollback();
                }
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return false;
    }

    @Override
    public boolean editAvatar(int userId, String avatarUrl) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(userDAO);
        try {
            if (userDAO.updateAvatar(userId, avatarUrl)) {
                manager.commit();
                return true;
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return false;
    }

    @Override
    public boolean makeBet(List<Bet> bets) throws ReceiverException {
        BetDAO betDAO = new BetDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(betDAO);
        try {
            if (betDAO.createBets(bets)) {
                manager.commit();
                return true;
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return false;
    }

    @Override
    public List<Bet> showBets(int userId, String type) throws ReceiverException {
        List<Bet> bets = null;
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
    public boolean recoverPassword(String login) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(userDAO);
        String newPassword = new PasswordGenerator().generatePassword();
        String emailText = EMAIL_TEXT_START + newPassword + EMAIL_TEXT_END;
        try {
            if (userDAO.updatePasswordByLogin(login, DigestUtils.md5Hex(newPassword))) {
                if (EmailSender.sendMail(login, EMAIL_SUBJECT, emailText)) {
                    manager.commit();
                    return true;
                } else {
                    manager.rollback();
                }
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return false;
    }

    @Override
    public boolean calculateGain(int matchId) throws ReceiverException {
        List<Bet> bets;
        Match match;
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
            return true;
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
    }
}
