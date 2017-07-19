package com.epam.bets.factory;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.command.CommandType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandFactory {
    private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);

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
