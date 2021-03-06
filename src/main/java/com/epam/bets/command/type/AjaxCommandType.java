package com.epam.bets.command.type;

import com.epam.bets.command.AjaxCommand;
import com.epam.bets.command.admin.ShowSupportPageCommand;
import com.epam.bets.command.common.ShowMatchResultsCommand;
import com.epam.bets.command.common.ShowMatchesCommand;
import com.epam.bets.command.common.ShowNewsCommand;
import com.epam.bets.command.common.ShowBetsCommand;

/**
 * Enumeration of all available ajax commands
 *
 * @author Pashchuk Ksenia
 */
public enum AjaxCommandType {
    SHOW_NEWS(new ShowNewsCommand()),
    SHOW_MATCHES(new ShowMatchesCommand()),
    SHOW_MATCH_RESULTS(new ShowMatchResultsCommand()),
    SHOW_SUPPORT_PAGE(new ShowSupportPageCommand()),
    SHOW_BETS(new ShowBetsCommand());
    private AjaxCommand command;

    AjaxCommandType(AjaxCommand command) {
        this.command = command;
    }
    /**
     * Getter of current {@link #command}
     *
     * @return {@link AjaxCommand}
     */
    public AjaxCommand getCommand() {
        return command;
    }
}
