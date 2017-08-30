package com.epam.bets.command.common;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.navigator.PageType;
import com.epam.bets.request.RequestContent;

import static com.epam.bets.constant.PageConstant.NEWS_PAGE;

public class ShowNewsPageCommand implements AbstractCommand {
    private static final String NEXT_PAGE = NEWS_PAGE;

    @Override
    public PageNavigator execute(RequestContent requestContent) {
        return new PageNavigator(NEXT_PAGE, PageType.FORWARD);
    }
}
