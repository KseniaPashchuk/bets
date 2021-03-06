package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.request.RequestContent;

import static com.epam.bets.constant.PageConstant.NEWS_PAGE;
/**
 * Class provides showing news page operation.
 *
 * @author Pashchuk Ksenia
 * @see AbstractCommand
 */
public class ShowNewsPageCommand implements AbstractCommand {
    private static final String NEXT_PAGE = NEWS_PAGE;
    /**
     * Provides showing news page operation.
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
