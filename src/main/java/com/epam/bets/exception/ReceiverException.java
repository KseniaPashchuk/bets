package com.epam.bets.exception;

/**
 * The class provides custom receiver Exception to throw at  classes implementing {@link com.epam.bets.receiver.Receiver}
 *
 * @author Pashchuk Ksenia
 * @see Exception
 */
public class ReceiverException extends Exception {
    public ReceiverException() {
        super();
    }

    public ReceiverException(String message) {
        super(message);
    }

    public ReceiverException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceiverException(Throwable cause) {
        super(cause);
    }
}
