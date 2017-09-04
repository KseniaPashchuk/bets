package com.epam.bets.dao;

import com.epam.bets.dao.impl.*;
import com.epam.bets.pool.ConnectionPool;
import com.epam.bets.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * The class provides factory for DAO layer classes.
 *
 * @author Pashchuk Ksenia
 * @see AutoCloseable
 */
public class DaoFactory implements AutoCloseable {

    private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class);

    /**
     * Field used to connect to database and execute queries.
     *
     * @see ProxyConnection
     */
    private ProxyConnection connection;


    /**
     * Create DaoFactory object by taking {@link ProxyConnection} object from {@link ConnectionPool}.
     *
     * @see ConnectionPool
     */
    public DaoFactory() {
        connection = ConnectionPool.getInstance().takeConnection();
    }


    /**
     * Provides creating {@link  NewsDAO} object.
     *
     * @return {@link  NewsDAO} object.
     */
    public NewsDAO getNewsDao() {
        return new NewsDAOImpl(connection);
    }


    /**
     * Provides creating {@link  UserDAO} object.
     *
     * @return {@link  UserDAO} object.
     */
    public UserDAO getUserDao() {
        return new UserDAOImpl(connection);
    }


    /**
     * Provides creating {@link  CreditCardDAO} object.
     *
     * @return {@link  CreditCardDAO} object.
     */
    public CreditCardDAO gerCreditCardDao() {
        return new CreditCardDAOImpl(connection);
    }


    /**
     * Provides creating {@link  FaqDAO} object.
     *
     * @return {@link  FaqDAO} object.
     */
    public FaqDAO getFaqDao() {
        return new FaqDAOImpl(connection);
    }


    /**
     * Provides creating {@link  MatchDAO} object.
     *
     * @return {@link  MatchDAO} object.
     */
    public MatchDAO getMatchDao() {
        return new MatchDAOImpl(connection);
    }


    /**
     * Provides creating {@link  GainCoefficientDAO} object.
     *
     * @return {@link  GainCoefficientDAO} object.
     */
    public GainCoefficientDAO getGainCoefficientDao() {
        return new GainCoefficientDAOImpl(connection);
    }


    /**
     * Provides creating {@link  BetDAO} object.
     *
     * @return {@link  BetDAO} object.
     */
    public BetDAO getBetDao() {
        return new BetDAOImpl(connection);
    }


    /**
     * Provides creating {@link  MailDAO} object.
     *
     * @return {@link  MailDAO} object.
     */
    public MailDAO getMailDao() {
        return new MailDAOImpl(connection);
    }

    @Override
    public void close() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Can't can't close connection - " + e, e);
        }
    }
}
