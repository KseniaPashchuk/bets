package com.epam.bets;

import com.epam.bets.dao.FaqDAO;
import com.epam.bets.dao.TransactionManager;
import com.epam.bets.dao.UserDAO;
import com.epam.bets.dao.impl.FaqDAOImpl;
import com.epam.bets.dao.impl.UserDAOImpl;
import com.epam.bets.entity.FAQ;
import com.epam.bets.entity.User;
import com.epam.bets.entity.UserRole;
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
        //UserDAO dao = new UserDAOImpl(ConnectionPool.getInstance().takeConnection());
        FaqDAO dao = new FaqDAOImpl(ConnectionPool.getInstance().takeConnection());
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(dao);
        FAQ faq = new FAQ();
        faq.setQuestion("lala");
        faq.setAnswer("LSKFDJG;AJKRG;");
//        User user = new User();
//        user.setRole(UserRole.ADMIN);
//        user.setLogin("ksenia@gmail.com");
//        user.setPassword(DigestUtils.md5Hex("Tomoe127"));
//        user.setFirstName("Ksenia");
//        user.setLastName("Pashchuk");
//        user.setBirthDate(LocalDate.of(1996, Month.OCTOBER, 2));
        try {
            dao.create(faq);
            manager.commit();

        } catch (DaoException e) {
            System.err.println(e);
            manager.rollback();
        }
        finally {
            manager.close();
        }
    }
}

