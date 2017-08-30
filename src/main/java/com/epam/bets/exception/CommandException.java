package com.epam.bets.exception;

/**
 * The class provides custom command Exception to throw at classes implementing {@link com.epam.bets.command.AjaxCommand}
 *
 * @author Pashchuk Ksenia
 * @see Exception
 */
public class CommandException extends Exception {

    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}
