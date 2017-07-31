package com.epam.bets.dao;

import com.epam.bets.dao.impl.*;
import com.epam.bets.pool.ConnectionPool;
import com.epam.bets.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class DaoFactory implements AutoCloseable {

    private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class);
    private ProxyConnection connection;

    public DaoFactory() {
        connection = ConnectionPool.getInstance().takeConnection();
    }

    public NewsDAO getNewsDao() {
        return new NewsDAOImpl(connection);
    }

    public UserDAO getUserDao() {
        return new UserDAOImpl(connection);
    }

    public CreditCardDAO gerCreditCardDao() {
        return new CreditCardDAOImpl(connection);
    }

    public FaqDAO getFaqDao() {
        return new FaqDAOImpl(connection);
    }

    public MatchDAO getMatchDao(){
        return new MatchDAOImpl(connection);
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
