package com.epam.bets.command;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    LOG_OUT(new LogOutCommand()),
    SHOW_PIECE_OF_NEWS(new ShowPieceOfNewsCommand()),
    SHOW_FAQ(new ShowFAQCommand()),
    SHOW_USER_PROFILE(new ShowUserProfileCommand()),
    EDIT_PROFILE(new EditProfileCommand()),
    EDIT_FAQ(new EditFAQCommand());
    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }
}
