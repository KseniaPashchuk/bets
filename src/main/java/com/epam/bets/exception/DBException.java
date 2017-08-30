package com.epam.bets.exception;

/**
 * The class provides custom database Exception to throw at  {@link com.epam.bets.pool.ConnectionPool}
 *
 * @author Pashchuk Ksenia
 * @see Exception
 */
public class DBException extends Exception {
    public DBException() {
        super();
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBException(Throwable cause) {
        super(cause);
    }
}
