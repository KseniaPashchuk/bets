package com.epam.bets.command;

import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.request.RequestContent;

import javax.servlet.http.HttpServletRequest;


public interface AbstractCommand {
    PageNavigator execute(RequestContent request);
}
