package com.epam.bets.command;

import com.epam.bets.command.common.ShowMatchResultsCommand;
import com.epam.bets.command.common.ShowMatchesCommand;
import com.epam.bets.command.common.ShowNewsCommand;
import com.epam.bets.command.common.ShowUserBetsCommand;

public enum AjaxCommandType {
    SHOW_NEWS(new ShowNewsCommand()),
    SHOW_MATCHES(new ShowMatchesCommand()),
    SHOW_MATCH_RESULTS(new ShowMatchResultsCommand()),
    SHOW_BETS(new ShowUserBetsCommand());
    private AjaxCommand command;

    AjaxCommandType(AjaxCommand command) {
        this.command = command;
    }

    public AjaxCommand getCommand() {
        return command;
    }
}
