package com.epam.bets.command.type;

import com.epam.bets.command.*;
import com.epam.bets.command.admin.*;
import com.epam.bets.command.admin.AddFootballTeamCommand;
import com.epam.bets.command.bookmaker.CreateMatchCommand;
import com.epam.bets.command.bookmaker.EditMatchCommand;
import com.epam.bets.command.bookmaker.SetMatchScoreCommand;
import com.epam.bets.command.common.*;

/**
 * Enumeration of all available commands
 *
 * @author Pashchuk Ksenia
 */

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    LOG_OUT(new LogOutCommand()),
    SHOW_PIECE_OF_NEWS(new ShowPieceOfNewsCommand()),
    SHOW_FAQ(new ShowFAQCommand()),
    SHOW_USER_PROFILE(new ShowUserProfileCommand()),
    EDIT_PROFILE(new EditProfileCommand()),
    EDIT_USER_AVATAR(new EditUserAvatarCommand()),
    CREATE_FAQ(new CreateFAQCommand()),
    EDIT_FAQ(new EditFAQCommand()),
    DELETE_FAQ(new DeleteFAQCommand()),
    CREATE_NEWS(new CreateNewsCommand()),
    EDIT_NEWS(new EditNewsCommand()),
    EDIT_NEWS_PICTURE(new EditNewsPictureCommand()),
    DELETE_NEWS(new DeleteNewsCommand()),
    SHOW_MATCHES_PAGE(new ShowMatchesPageCommand()),
    CREATE_MATCH(new CreateMatchCommand()),
    EDIT_MATCH(new EditMatchCommand()),
    SET_SCORE(new SetMatchScoreCommand()),
    MAKE_BET(new MakeBetCommand()),
    CHANGE_PASSWORD(new EditPasswordCommand()),
    PASSWORD_RECOVER(new PasswordRecoverCommand()),
    CALCULATE_GAIN(new CalculateUserGainCommand()),
    REFILL_CASH(new RefillCashCommand()),
    CREATE_REFILL_CASH_PAGE(new ShowRefillCashPageCommand()),
    SHOW_MAIN_PAGE(new ShowMainPageCommand()),
    ADD_TEAM(new AddFootballTeamCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    SHOW_NEWS_PAGE(new ShowNewsPageCommand()),
    SHOW_MATCH_RESULTS_PAGE(new ShowMatchResultsPageCommand()),
    ADD_CONFEDERATION(new AddConfederationCommand()),
    SHOW_SUPPORT_PAGE(new ShowSupportPageCommand()),
    SHOW_SUPPORT_CHAT(new ShowSupportChatCommand());
    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }
    /**
     * Getter of current {@link #command}
     *
     * @return {@link AbstractCommand}
     */
    public AbstractCommand getCommand() {
        return command;
    }
}
