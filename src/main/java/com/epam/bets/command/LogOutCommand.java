package com.epam.bets.command;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bets.JspConstant.INDEX_PAGE;

public class LogOutCommand implements AbstractCommand {

    private static  final String NEXT_PAGE = INDEX_PAGE;
    // TODO check!!!!!!!

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
    }
}
