package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.request.RequestContent;

import static com.epam.bets.constant.PageConstant.BETS_PAGE;

/**
 * Class provides showing user bets page operation.
 *
 * @author Pashchuk Ksenia
 * @see AbstractCommand
 */

public class ShowBetsPageCommand implements AbstractCommand {
    private static final String NEXT_PAGE = BETS_PAGE;
    /**
     * Provides showing user bets page operation.
     * Navigates to {@link com.epam.bets.constant.PageConstant#REGISTRATION_PAGE}
     *
     * @param requestContent ({@link RequestContent}) request from client to get parameters to work with
     * @return {@link PageNavigator} with response parameters.
     */
    @Override
    public PageNavigator execute(RequestContent requestContent) {
        return new PageNavigator(NEXT_PAGE, PageNavigator.PageType.FORWARD);
    }
}
