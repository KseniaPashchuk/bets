package com.epam.bets.servlet;

import com.epam.bets.command.AjaxCommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.epam.bets.exception.CommandException;
import com.epam.bets.factory.AjaxCommandFactory;
import com.epam.bets.request.RequestContent;
import com.google.gson.Gson;

import static com.epam.bets.constant.PageConstant.SERVER_ERROR_PAGE;

/**
 * The class provides controller for ajax requests at MVC pattern of application.
 *
 * @author Pashchuk Ksenia
 * @see HttpServlet
 */

@WebServlet(name = "AjaxServlet", urlPatterns = {"/ajax"})
public class AjaxServlet extends HttpServlet {


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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Processes requests.
     * Defines command by getting command name in {@link HttpServletRequest#getAttribute(String)} and send it to
     * {@link AjaxCommandFactory}.
     * Creates new {@link RequestContent} and send it as param to defined command.
     * Writes object list, returned from {@link AjaxCommand#execute(RequestContent)}, to {@link HttpServletResponse}
     * in JSON format using {@link Gson#toJson(Object)}
     *
     * @param request  request from client to get parameters to work with
     * @param response response to client with parameters to work with on client side
     * @throws IOException      if an input or output error is detected when the servlet handles the request
     * @throws ServletException if the request could not be handled
     * @see com.epam.bets.factory.AjaxCommandFactory
     * @see com.epam.bets.command.type.AjaxCommandType
     */

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commandName = request.getParameter("command");
        AjaxCommand command = new AjaxCommandFactory().initCommand(commandName);
        RequestContent content = new RequestContent();
        content.extractValues(request);
        response.setContentType("application/json");
        try {
            response.getWriter().write(new Gson().toJson(command.execute(content)));
            content.insertValues(request);
        } catch (CommandException e) {
            response.sendRedirect(SERVER_ERROR_PAGE);
        }
    }
}
