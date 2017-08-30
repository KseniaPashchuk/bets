package com.epam.bets.command;

import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.request.RequestContent;

import javax.servlet.http.HttpServletRequest;

/**
 * Common interface for Commands suitable to use with {@link com.epam.bets.servlet.BetServlet}
 *
 * @author Pashchuk Ksenia
 */

public interface AbstractCommand {
    /**
     * Executes definite operation with data parsed from {@link RequestContent} and returns {@link PageNavigator} data.
     *
     * @param requestContent request from client to get parameters to work with.
     * @return {@link PageNavigator} with parameters next page and response type.
     */
    String PREV_REQUEST = "prevRequest";

    PageNavigator execute(RequestContent requestContent);
}
