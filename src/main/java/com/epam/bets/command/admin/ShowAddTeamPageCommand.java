package com.epam.bets.command.admin;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.receiver.MatchReceiver;
import com.epam.bets.receiver.impl.MatchReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.bets.constant.ErrorConstant.ERROR_LIST_NAME;
import static com.epam.bets.constant.PageConstant.CREATE_TEAM_PAGE;
import static com.epam.bets.constant.PageConstant.SERVER_ERROR_PAGE;
import static com.epam.bets.constant.PageConstant.SHOW_CREATE_TEAM_PAGE;

public class ShowAddTeamPageCommand implements AbstractCommand {
    private static final String NEXT_PAGE = CREATE_TEAM_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(ShowAddTeamPageCommand.class);

    private MatchReceiver receiver = new MatchReceiverImpl();

    @Override
    public PageNavigator execute(RequestContent requestContent) {
        PageNavigator navigator;
        try {
            receiver.showAllConfederations(requestContent);
            navigator = new PageNavigator(NEXT_PAGE, PageNavigator.PageType.FORWARD);
            requestContent.insertSessionAttribute(PREV_REQUEST, SHOW_CREATE_TEAM_PAGE);
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(SERVER_ERROR_PAGE, PageNavigator.PageType.REDIRECT);
        }
        return navigator;
    }

}
