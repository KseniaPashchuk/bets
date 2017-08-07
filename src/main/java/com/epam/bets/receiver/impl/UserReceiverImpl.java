package com.epam.bets.receiver.impl;

import com.epam.bets.dao.CreditCardDAO;
import com.epam.bets.dao.DaoFactory;
import com.epam.bets.dao.TransactionManager;
import com.epam.bets.dao.UserDAO;
import com.epam.bets.dao.impl.CreditCardDAOImpl;
import com.epam.bets.dao.impl.UserDAOImpl;
import com.epam.bets.entity.CreditCards;
import com.epam.bets.entity.User;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.UserReceiver;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class UserReceiverImpl implements UserReceiver {
    private static final Logger LOGGER = LogManager.getLogger(UserReceiverImpl.class);

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
            user = userDAO.findEntityById(userId);
            CreditCards creditCards = cardDAO.findEntityById(user.getId());
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
                if (userDAO.updatePasswordByLogin(login, newPassword)) {
                    manager.commit();
                    return true;
                } else {
                    manager.rollback();
                }
            }
            } catch(DaoException e){
                manager.rollback();
                throw new ReceiverException(e);
            } finally{
                manager.close();
            }
            return false;
        }

        @Override
        public boolean editAvatar ( int userId, String avatarUrl) throws ReceiverException {
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
    }
