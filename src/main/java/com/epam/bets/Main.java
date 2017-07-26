package com.epam.bets;

import com.epam.bets.dao.TransactionManager;
import com.epam.bets.dao.UserDAO;
import com.epam.bets.dao.impl.UserDAOImpl;
import com.epam.bets.entity.User;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.pool.ConnectionPool;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDate;
import java.time.Month;

public class Main {
    public static void main(String[] args) {
        UserReceiver receiver = new UserReceiverImpl();
        UserDAO dao = new UserDAOImpl(ConnectionPool.getInstance().takeConnection());
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(dao);
        User user = new User();
        user.setLogin("ks@gmail.com");
        user.setPassword(DigestUtils.md5Hex("Tomoe127"));
        user.setFirstName("Ksenia");
        user.setLastName("Pashchuk");
        user.setBirthDate(LocalDate.of(2014, Month.JANUARY, 1));

        try {
            dao.delete(1);
            manager.rollback();

        } catch (DaoException e) {
            System.err.println(e);
        }
        finally {
            manager.close();
        }
    }
}

