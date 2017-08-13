package com.epam.bets.command.admin;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.FAQReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bets.constant.ErrorConstant.INVALID_PARAMS_ERROR;
import static com.epam.bets.constant.ErrorConstant.INVALID_PARAMS_MESSAGE;
import static com.epam.bets.constant.PageConstant.AFTER_EDIT_FAQ_PAGE;
import static com.epam.bets.constant.PageConstant.FAQ_PAGE;

public class DeleteFAQCommand implements AbstractCommand {

    private static final String PARAM_NAME_QUESTION = "delete_question";
    private static final String NEXT_PAGE = AFTER_EDIT_FAQ_PAGE;
    private static final String ERROR_PAGE = FAQ_PAGE;
    private static final Logger LOGGER = LogManager.getLogger(DeleteFAQCommand.class);
    private FAQReceiverImpl receiver = new FAQReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        try {
            if (receiver.deleteFAQ(request.getParameter(PARAM_NAME_QUESTION))) {
                navigator = new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
            } else {
                request.setAttribute(INVALID_PARAMS_ERROR, INVALID_PARAMS_MESSAGE);
                navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
        }
        return navigator;
    }
}
