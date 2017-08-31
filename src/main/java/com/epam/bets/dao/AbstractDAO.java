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

public abstract class AbstractDAO<T extends Entity> implements AutoCloseable {
    protected ProxyConnection connection;
    private static final Logger LOGGER = LogManager.getLogger(AbstractDAO.class);
    protected static final int EXISTING_ENTITY_ERROR_CODE = 1062;

    protected AbstractDAO() {
    }

    protected AbstractDAO(ProxyConnection connection) {
        this.connection = connection;
    }

     public abstract int create(T entity) throws DaoException;

    public abstract boolean update(T entity) throws DaoException;

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

    void setConnection(ProxyConnection connection) {
        this.connection = connection;
    }
}
