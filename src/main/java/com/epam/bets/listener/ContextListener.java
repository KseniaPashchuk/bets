package com.epam.bets.listener;

import com.epam.bets.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * The class provides listener of servlet container initialization and destruction.
 *
 * @author Pashchuk Ksenia
 * @see ServletContextListener
 * @see WebListener
 */
@WebListener
public class ContextListener implements ServletContextListener {
    /**
     * Receives notification that the web application initialization process is starting.
     * Inits {@link ConnectionPool}.
     *
     * @param ev the ServletContextEvent containing the ServletContext to be initialized
     */
    public void contextInitialized(ServletContextEvent ev) {
        ConnectionPool.getInstance();
        ev.getServletContext().log("ConnectionPool was initialized");
    }

    /**
     * Receives notification that the ServletContext is about to be
     * shut down.
     * <p>
     * Destroys {@link ConnectionPool} and shut down {@link LogManager}.
     *
     * @param ev the ServletContextEvent containing the ServletContext to be destroyed
     */
    public void contextDestroyed(ServletContextEvent ev) {
        ConnectionPool.getInstance().closePool();
        ev.getServletContext().log("ConnectionPool was destroyed");
        LogManager.shutdown();
    }
}

