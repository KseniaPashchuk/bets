package com.epam.bets.command;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bets.JspConstant.REGISTRATION_PAGE;

public class EmptyCommand implements AbstractCommand {

    private static final String NEXT_PAGE = REGISTRATION_PAGE;
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        return  new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
    }
}
