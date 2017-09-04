package com.epam.bets.dao;

import com.epam.bets.entity.Entity;
import com.epam.bets.exception.DBException;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ConnectionPool;
import com.epam.bets.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * The class provides abstraction for DAO layer classes.
 *
 * @author Pashchuk Ksenia
 */

public abstract class AbstractDAO<T extends Entity> implements AutoCloseable {

    /**
     * Field used to connect to database and execute queries.
     *
     * @see ProxyConnection
     */
    protected ProxyConnection connection;

    private static final Logger LOGGER = LogManager.getLogger(AbstractDAO.class);
    protected static final int EXISTING_ENTITY_ERROR_CODE = 1062;

    /**
     * Constructs DAO object.
     */

    protected AbstractDAO() {
    }

    /**
     * Constructs DAO object by assigning {@link AbstractDAO#connection} field definite
     * {@link ProxyConnection} object.
     *
     * @param connection {@link ProxyConnection}
     */
    protected AbstractDAO(ProxyConnection connection) {
        this.connection = connection;
    }

    /**
     * Abstract declare of entity creating operation
     *
     * @param entity is object extends {@link com.epam.bets.entity.Entity}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see ProxyConnection
     */

    public abstract int create(T entity) throws DaoException;


    /**
     * Abstract declare of entity updating operation
     *
     * @param entity is object extends {@link com.epam.bets.entity.Entity}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see ProxyConnection
     */

    public abstract boolean update(T entity) throws DaoException;

    /**
     * Closes DAO object by closing {@link ProxyConnection} object.
     */
    public void close() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Can't close connection " + e, e);
        }
    }

    /**
     * {@link AbstractDAO#connection} field setter.
     *
     * @param connection {@link ProxyConnection}
     */
    void setConnection(ProxyConnection connection) {
        this.connection = connection;
    }
}
