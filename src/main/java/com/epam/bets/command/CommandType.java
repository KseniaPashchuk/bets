package com.epam.bets.command;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    LOG_OUT(new LogOutCommand()),
    SHOW_PIECE_OF_NEWS(new ShowPieceOfNewsCommand()),
    SHOW_FAQ(new ShowFAQCommand()),
    SHOW_USER_PROFILE(new ShowUserProfileCommand()),
    EDIT_PROFILE(new EditProfileCommand()),
    CREATE_FAQ(new CreateFAQCommand()),
    EDIT_FAQ(new EditFAQCommand()),
    DELETE_FAQ(new DeleteFAQCommand()),
    CREATE_NEWS(new CreateNewsCommand()),
    EDIT_NEWS(new EditNewsCommand()),
    DELETE_NEWS(new DeleteNewsCommand());
    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }
}
