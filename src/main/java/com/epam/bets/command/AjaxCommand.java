package com.epam.bets.command;

import com.epam.bets.entity.Entity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ASUS on 25.07.2017.
 */
public interface AjaxCommand<T extends Entity> {
    List<T> execute(HttpServletRequest request);
}
