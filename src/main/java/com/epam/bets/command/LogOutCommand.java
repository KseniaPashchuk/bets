package com.epam.bets.command;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bets.constant.PageConstant.INDEX_PAGE;

public class LogOutCommand implements AbstractCommand {

    private static  final String NEXT_PAGE = INDEX_PAGE;

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return new PageNavigator(NEXT_PAGE, PageType.REDIRECT);
    }
}
