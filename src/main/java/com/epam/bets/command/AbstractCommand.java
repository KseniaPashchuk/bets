package com.epam.bets.command;

import com.epam.bets.navigator.PageNavigator;

import javax.servlet.http.HttpServletRequest;

public interface AbstractCommand {
    PageNavigator execute(HttpServletRequest request);
}
