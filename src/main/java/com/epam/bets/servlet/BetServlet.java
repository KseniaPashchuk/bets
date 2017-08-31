package com.epam.bets.servlet;


import com.epam.bets.factory.CommandFactory;
import com.epam.bets.command.AbstractCommand;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.request.RequestContent;
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
 * The class provides controller for client requests at MVC pattern of application.
 *
 * @author Pashchuk Ksenia
 * @see HttpServlet
 */
@WebServlet(name = "BetServlet", urlPatterns = {"/controller"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5,// Максимальный буфера данных в байтах,
        // при его привышении данные начнут записываться на диск во временную директорию
        // устанавливаем один мегабайт
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class BetServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(BetServlet.class);

    /**
     * Processes request sent by GET method.
     *
     * @param request  request from client to get parameters
     * @param response response to client with parameters to work with on client side
     * @throws IOException      if an input or output error is detected when the servlet handles the request
     * @throws ServletException if the request could not be handled
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see #processRequest(HttpServletRequest, HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Processes request sent by POST method.
     *
     * @param request  request from client to get parameters
     * @param response response to client with parameters to work with on client side
     * @throws IOException      if an input or output error is detected when the servlet handles the request
     * @throws ServletException if the request could not be handled
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see #processRequest(HttpServletRequest, HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Processes requests.
     * Defines command by getting command name in {@link HttpServletRequest#getAttribute(String)} and send it to
     * {@link CommandFactory}.
     * Creates new {@link RequestContent} and send it as param to defined command.
     * {@link AbstractCommand#execute(RequestContent)} returns {@link PageNavigator} object and controller
     * navigates to page according to the returned value.
     * If error occurs user navigates to server error page.
     *
     * @param request  request from client to get parameters to work with
     * @param response response to client with parameters to work with on client side
     * @throws IOException      if an input or output error is detected when the servlet handles the request
     * @throws ServletException if the request could not be handled
     * @see com.epam.bets.factory.CommandFactory
     * @see com.epam.bets.command.type.CommandType
     */

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commandName = request.getParameter("command");
        AbstractCommand command = new CommandFactory().initCommand(commandName);
        RequestContent content = new RequestContent();
        content.extractValues(request);
        PageNavigator navigator = command.execute(content);
        content.insertValues(request);
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