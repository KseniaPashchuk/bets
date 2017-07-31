package com.epam.bets.command;

public enum AjaxCommandType {
    SHOW_NEWS(new ShowNewsCommand()),
    SHOW_MATCHES(new ShowMatchesCommand()),
    SHOW_MATCH_RESULTS(new ShowMatchResultsCommand());
    private AjaxCommand command;

    AjaxCommandType(AjaxCommand command) {
        this.command = command;
    }

    public AjaxCommand getCommand() {
        return command;
    }
}
