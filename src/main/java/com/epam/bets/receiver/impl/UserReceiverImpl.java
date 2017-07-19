package com.epam.bets.receiver.impl;

import com.epam.bets.dao.TransactionManager;
import com.epam.bets.dao.UserDAO;
import com.epam.bets.dao.impl.UserDAOImpl;
import com.epam.bets.entity.User;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.pool.ConnectionPool;
import com.epam.bets.pool.ProxyConnection;
import com.epam.bets.receiver.UserReceiver;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class UserReceiverImpl implements UserReceiver {
    private static final Logger LOGGER = LogManager.getLogger(UserReceiverImpl.class);

    @Override
    public boolean checkExistingUser(String login) throws ReceiverException {
        boolean alreadyExist = false;
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        try (UserDAO userDAO = new UserDAOImpl(connection)) {
            if (userDAO.findUserByLogin(login) != null) {
                alreadyExist = true;
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
        return alreadyExist;
    }

    public User signIn(String login, String password) throws ReceiverException {
        User user = null;
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        try (UserDAO userDAO = new UserDAOImpl(connection)) {
            String realPassword = userDAO.findPasswordByLogin(login);
            if (DigestUtils.md5Hex(password).equals(realPassword)) {
                user = userDAO.findUserByLogin(login);
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
        return user;
    }

    public boolean signUp(User user) throws ReceiverException {
        boolean isSignUp;
        UserDAO userDAO = new UserDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(userDAO);
        String password = user.getPassword();
        user.setPassword(DigestUtils.md5Hex(password));
        try {
            isSignUp = userDAO.create(user);
            if (isSignUp) {
                manager.commit();
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return isSignUp;
    }

    @Override
    public User showProfileInfo(String login) throws ReceiverException {
        User user;
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        try (UserDAO userDAO = new UserDAOImpl(connection)) {
            user = userDAO.findUserByLogin(login);
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
        return user;
    }
}
