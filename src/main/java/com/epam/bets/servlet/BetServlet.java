package com.epam.bets.servlet;


import com.epam.bets.factory.CommandFactory;
import com.epam.bets.command.AbstractCommand;
import com.epam.bets.command.PageNavigator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.bets.constant.PageConstant.SERVER_ERROR_PAGE;

/**
 *
 */
@WebServlet("/controller")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5,// Максимальный буфера данных в байтах,
        // при его привышении данные начнут записываться на диск во временную директорию
        // устанавливаем один мегабайт
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)//TODO add error page and runtime page
public class BetServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(BetServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commandName = request.getParameter("command");
        AbstractCommand command = new CommandFactory().initCommand(commandName);
        PageNavigator navigator = command.execute(request);
        if (navigator != null) {
            switch (navigator.getType()) {
                case FORWARD: {
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navigator.getPageUrl());
                    dispatcher.forward(request, response);
                    break;
                }
                case REDIRECT: {
                    response.sendRedirect(navigator.getPageUrl());
                    break;
                }
                default: {
                    response.sendRedirect(SERVER_ERROR_PAGE);
                }
            }
        } else {
            response.sendRedirect(SERVER_ERROR_PAGE);
        }
    }
}