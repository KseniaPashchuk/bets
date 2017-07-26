package com.epam.bets.command;

public enum  AjaxCommandType {
   SHOW_NEWS(new ShowNewsCommand());
    private AjaxCommand command;

    AjaxCommandType(AjaxCommand command) {
        this.command = command;
    }

    public AjaxCommand getCommand() {
        return command;
    }
}
