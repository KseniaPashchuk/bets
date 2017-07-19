package com.epam.bets.dao;

import com.epam.bets.pool.ConnectionPool;
import com.epam.bets.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class TransactionManager implements AutoCloseable {
    private ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
    private static final Logger LOGGER = LogManager.getLogger(TransactionManager.class);

    public ProxyConnection getConnection() {
        return connection;
    }

    public void beginTransaction(AbstractDAO dao, AbstractDAO... daos) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Can't change param autoCommit: " + e);
        }
        dao.setConnection(connection);
        for (AbstractDAO d : daos) {
            d.setConnection(connection);
        }
    }

    public void close() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Can't end transaction: can't close connection - " + e);
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Can't commit transaction: " + e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Can't rollback transaction: " + e);
        }
    }
}
