package com.epam.bets.command;

import com.epam.bets.entity.FAQ;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.impl.FAQReceiverImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.epam.bets.JspConstant.FAQ_PAGE;
import static com.epam.bets.JspConstant.MAIN_PAGE;


public class ShowFAQCommand implements AbstractCommand {

    private static final Logger LOGGER = LogManager.getLogger(ShowFAQCommand.class);
    private static final String NEXT_PAGE = FAQ_PAGE;
    private static final String ERROR_PAGE = MAIN_PAGE;
    private static final String PARAM_NAME_FAQ_LIST = "faqList";
    private static final String NO_FAQ_ERROR = "noFAQ";
    private static final String NO_FAQ_MESSAGE = "no_faq";
    private FAQReceiverImpl receiver = new FAQReceiverImpl();

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
            LOGGER.log(Level.ERROR, e);
            request.setAttribute(NO_FAQ_ERROR, NO_FAQ_MESSAGE);
            navigator = new PageNavigator(ERROR_PAGE, PageType.FORWARD);
        }
        return navigator;
    }
}
