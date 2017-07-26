package com.epam.bets.factory;

import com.epam.bets.command.AjaxCommand;
import com.epam.bets.command.AjaxCommandType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by ASUS on 25.07.2017.
 */
public class AjaxCommandFactory {
    private static final Logger LOGGER = LogManager.getLogger( AjaxCommandFactory.class);

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
