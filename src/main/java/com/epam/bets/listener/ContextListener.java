package com.epam.bets.listener;

import com.epam.bets.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent ev) {
        ConnectionPool.getInstance();
        ev.getServletContext().log("ConnectionPool was initialized");
    }

    public void contextDestroyed(ServletContextEvent ev) {
        ConnectionPool.getInstance().closePool();
        ev.getServletContext().log("ConnectionPool was destroyed");
        LogManager.shutdown();
    }
}

