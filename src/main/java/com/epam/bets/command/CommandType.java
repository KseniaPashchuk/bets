package com.epam.bets.command;

public enum CommandType {
    SIGNIN(new SignInCommand()),
    SIGNUP(new SignUpCommand()),
    LOGOUT(new LogOutCommand()),
    SHOW_PROFILE(new ShowProfileCommand()),
    SHOW_PIECE_OF_NEWS(new ShowPieceOfNewsCommand()),
    SHOW_FAQ(new ShowFAQCommand());
    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }
}
