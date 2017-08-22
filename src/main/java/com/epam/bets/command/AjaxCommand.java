package com.epam.bets.command;

import com.epam.bets.entity.Entity;
import com.epam.bets.exception.CommandException;
import com.epam.bets.request.RequestContent;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AjaxCommand<T extends Entity> {
    List<T> execute(RequestContent request) throws CommandException;
}
