package com.epam.bets.servlet;

import com.epam.bets.command.AjaxCommand;
import com.epam.bets.command.ShowNewsCommand;
import com.epam.bets.entity.News;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.epam.bets.factory.AjaxCommandFactory;
import com.google.gson.Gson;

@WebServlet("/ajax")
public class AjaxServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commandName = request.getParameter("command");
        AjaxCommand command = new AjaxCommandFactory().initCommand(commandName);
        //ShowNewsCommand command = new ShowNewsCommand();
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(command.execute(request)));
    }
}
