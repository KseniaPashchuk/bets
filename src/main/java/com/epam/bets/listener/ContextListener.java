package com.epam.bets.listener;

import com.epam.bets.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ContextListener.class);
    public void contextInitialized(ServletContextEvent ev) {
        ConnectionPool.getInstance();
        ev.getServletContext().log("ConnectionPool was initialized");

    }

    public void contextDestroyed(ServletContextEvent ev) {
        LOGGER.log(Level.INFO, "Start destroying servlet container");
        ConnectionPool.getInstance().closePool();
        LOGGER.log(Level.INFO, "ConnectionPool was destroyed");
    }
}

