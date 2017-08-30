package com.epam.bets.command;

import com.epam.bets.entity.Entity;
import com.epam.bets.exception.CommandException;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.request.RequestContent;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * Common interface for Ajax Commands suitable to use with {@link com.epam.bets.servlet.AjaxServlet}.
 *
 * @author Pashchuk Ksenia
 */
public interface AjaxCommand<T extends Entity> {
    /**
     * Executes definite operation with data parsed from {@link RequestContent} and returns {@link List} data.
     *
     * @param requestContent request from client to get parameters to work with.
     * @return {@link List} data.
     */
    String PREV_REQUEST = "prevRequest";
    List<T> execute(RequestContent requestContent) throws CommandException;
}
