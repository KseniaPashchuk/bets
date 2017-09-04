package com.epam.bets.dao;

import com.epam.bets.pool.ConnectionPool;
import com.epam.bets.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * The class provides transaction operations  for DAO layer classes.
 *
 * @author Pashchuk Ksenia
 * @see AutoCloseable
 */

public class TransactionManager implements AutoCloseable {
    /**
     * Field used to connect to database and execute queries.
     *
     * @see ProxyConnection
     */
    private ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
    private static final Logger LOGGER = LogManager.getLogger(TransactionManager.class);

    /**
     * Getter for {@link #connection}.
     */
    public ProxyConnection getConnection() {
        return connection;
    }


    /**
     * Starts transaction for multiple dao.
     *
     * @see ProxyConnection#setAutoCommit(boolean)
     */

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

    /**
     * Returns {@link #connection} to {@link ConnectionPool}.
     */
    public void close() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Can't end transaction: can't close connection - " + e);
        }
    }

    /**
     * Commits database changes made during transaction.     *
     *
     * @see ProxyConnection#setAutoCommit(boolean)
     */
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Can't commit transaction: " + e);
        }
    }

    /**
     * Rollbacks database changes made during transaction.
     *
     * @see ProxyConnection#setAutoCommit(boolean)
     */
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Can't rollback transaction: " + e);
        }
    }
}
