package com.epam.bets.command;

public enum CommandType {
    SIGNIN(new SignInCommand()),
    SIGNUP(new SignUpCommand()),
    LOGOUT(new LogOutCommand()),
    SHOW_PROFILE(new ShowProfileCommand());
    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }
}
