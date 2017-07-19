package com.epam.bets;

import com.epam.bets.dao.UserDAO;
import com.epam.bets.dao.impl.UserDAOImpl;
import com.epam.bets.entity.User;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.pool.ConnectionPool;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;

public class Main {
    public static void main(String[] args) {
        UserReceiver receiver = new UserReceiverImpl();
        UserDAO dao = new UserDAOImpl(ConnectionPool.getInstance().takeConnection());

        User user = new User();
//        user.setLogin("ks@tut.by");
//        user.setPassword("pass");

        try {
            user = dao.findUserByLogin("ks@gmail.ocm");

        } catch (DaoException e) {
            System.err.println(e);
        }
        finally {
            dao.close();
        }
    }
}

