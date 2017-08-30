package com.epam.bets.factory;

import com.epam.bets.command.AjaxCommand;
import com.epam.bets.command.type.AjaxCommandType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The factory of Commands suitable to use with {@link com.epam.bets.servlet.AjaxServlet}.
 *
 * @author Pashchuk Ksenia
 * @see AjaxCommand
 */
public class AjaxCommandFactory {
    private static final Logger LOGGER = LogManager.getLogger( AjaxCommandFactory.class);
    /**
     * Defines {@link AjaxCommand} due to {@link String} cmd (should be equal to
     * {@link AjaxCommandType})
     *
     * @param cmd - name of the command
     * @return defined {@link AjaxCommand} or  null
     */
    public AjaxCommand initCommand(String cmd) {
        AjaxCommand command = null;
        try {
            AjaxCommandType cmdEnum = AjaxCommandType.valueOf(cmd.toUpperCase());
            command = cmdEnum.getCommand();
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.ERROR, "Can't define command: " + e);
        }
        return command;
    }
}
