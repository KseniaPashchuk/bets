package com.epam.bets.factory;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.command.type.CommandType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * The factory of Commands suitable to use with {@link com.epam.bets.servlet.BetServlet}.
 *
 * @author Pashchuk Ksenia
 * @see AbstractCommand
 */
public class CommandFactory {

    private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);
    /**
     * Defines {@link AbstractCommand} due to {@link String} cmd (should be equal to
     * {@link CommandType})
     *
     * @param cmd - name of the command
     * @return defined {@link AbstractCommand} or  null
     */
    public AbstractCommand initCommand(String cmd) {
        AbstractCommand command = null;
        try {
            CommandType cmdEnum = CommandType.valueOf(cmd.toUpperCase());
            command = cmdEnum.getCommand();
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.ERROR, "Can't define command: " + e);
        }
        return command;
    }
}
