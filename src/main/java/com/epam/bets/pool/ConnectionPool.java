package com.epam.bets.pool;

import com.epam.bets.exception.DBException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class provides singleton container for {@link ProxyConnection} objects.
 *
 * @author Pashchuk Ksenia
 */

public class ConnectionPool {
    /**
     * Class singleton instance.
     */
    private static ConnectionPool instance;
    /**
     * Marker to check if {@link #instance} is created.
     */
    private static AtomicBoolean flag = new AtomicBoolean();
    private static Lock lock = new ReentrantLock();
    private BlockingQueue<ProxyConnection> connections;
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private PoolManager manager;

    private ConnectionPool() {
        init();
    }

    private void init() {
        manager = new PoolManager();
        connections = new ArrayBlockingQueue<>(manager.poolSize);
        for (int i = 0; i < manager.poolSize; i++) {
            try {
                connections.add(manager.getConnection());
            } catch (DBException e) {
                LOGGER.log(Level.ERROR, e.getMessage());
            }
        }
        if (connections.isEmpty()) {
            LOGGER.log(Level.FATAL, "Can not create database connection pool");
            throw new RuntimeException("Can not create database connection pool");
        }
        int sizeDiff = manager.poolSize - connections.size();
        if (sizeDiff > 0) {
            for (int i = 0; i < sizeDiff; i++) {
                try {
                    connections.add(manager.getConnection());
                } catch (DBException e) {
                    LOGGER.log(Level.ERROR, "Can not create connection " + e.getMessage());
                }
            }
        }
        sizeDiff = manager.poolSize - connections.size();
        if (sizeDiff > 0 && sizeDiff < manager.poolSize / 2) {
            LOGGER.log(Level.WARN, "Connection pool size is smaller than required; Attempt to continue working...");
        } else if(sizeDiff > manager.poolSize / 2) {
            LOGGER.log(Level.FATAL, "Connection pool size is too small: size - " + connections.size());
            throw new RuntimeException("Connection pool size is too small: size - " + connections.size());
        }
    }


    public static ConnectionPool getInstance() {
        if (!flag.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    flag.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public ProxyConnection takeConnection() {
        ProxyConnection connection = null;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "Can not take connection from pool: " + e);
        }
        return connection;
    }

    public void putConnection(ProxyConnection connection) {
        try {
            connections.put(connection);
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void closePool() {
        try {
            for (int i = 0; i < connections.size(); i++) {
                connections.take().realClose();
            }
            manager.deregisterDrivers();
        } catch (DBException | SQLException | InterruptedException e) {
            LOGGER.log(Level.ERROR, "Can not close connection pool: " + e.getMessage());
        }
    }
}
