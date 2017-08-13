package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.entity.FAQ;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.FAQReceiver;
import com.epam.bets.receiver.impl.FAQReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.epam.bets.constant.ErrorConstant.NO_FAQ_ERROR;
import static com.epam.bets.constant.ErrorConstant.NO_FAQ_MESSAGE;
import static com.epam.bets.constant.PageConstant.FAQ_PAGE;
import static com.epam.bets.constant.PageConstant.MAIN_PAGE;


public class ShowFAQCommand implements AbstractCommand {

    private static final Logger LOGGER = LogManager.getLogger(ShowFAQCommand.class);
    private static final String NEXT_PAGE = FAQ_PAGE;
    private static final String ERROR_PAGE = MAIN_PAGE;
    private static final String PARAM_NAME_FAQ_LIST = "faqList";

    private FAQReceiver receiver = new FAQReceiverImpl();

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        List<FAQ> faqList;
        try {
            faqList = receiver.showAllFAQ();
            if (faqList != null && !faqList.isEmpty()) {
                request.setAttribute(PARAM_NAME_FAQ_LIST, faqList);
                navigator = new PageNavigator(NEXT_PAGE, PageType.FORWARD);
            } else {
                request.setAttribute(NO_FAQ_ERROR, NO_FAQ_MESSAGE);
                navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
            }

        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e, e);
            request.setAttribute(NO_FAQ_ERROR, NO_FAQ_MESSAGE);
            navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
        }
        return navigator;
    }
}
