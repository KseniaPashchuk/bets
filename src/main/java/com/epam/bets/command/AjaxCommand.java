package com.epam.bets.command;

import com.epam.bets.entity.Entity;
import com.epam.bets.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AjaxCommand<T extends Entity> {
    List<T> execute(HttpServletRequest request) throws CommandException;
}
