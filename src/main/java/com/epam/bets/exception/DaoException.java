package com.epam.bets.exception;

/**
 * The class provides custom DAO Exception to throw at  classes implementing {@link com.epam.bets.dao.AbstractDAO}
 *
 * @author Pashchuk Ksenia
 * @see Exception
 */
public class DaoException extends Exception {

    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
