package com.epam.bets.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *  The class provides controller for client requests at MVC pattern of application.
 *
 *  @author Pashchuk Ksenia
 *  @see HttpServlet
 */


@WebServlet(name = "ImageServlet", urlPatterns = {"/image/*"})
public class ImageServlet extends HttpServlet {

    public static final String PARAM_NAME_UPLOADS = "uploads.dir";


    /**
     * Processes request sent by POST method. Is unsupported in this {@link HttpServlet}.
     *
     * @param request  request from client to get parameters to work with
     * @param response response to client with parameters to work with on client side
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    /**
     * Processes request sent by GET method. Writes to {@link HttpServletResponse} image found by taken path.
     *
     * @param request  request from client to get parameters to work with
     * @param response response to client with parameters to work with on client side
     * @see HttpServletRequest
     * @see javax.servlet.ServletContext
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String filename = request.getPathInfo();
        File file = new File(getServletContext().getInitParameter(PARAM_NAME_UPLOADS) + "/", filename);
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }
}
