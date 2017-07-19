package com.epam.bets.command;

import javax.servlet.http.HttpServletRequest;

public interface AbstractCommand {
    PageNavigator execute(HttpServletRequest request);
}
