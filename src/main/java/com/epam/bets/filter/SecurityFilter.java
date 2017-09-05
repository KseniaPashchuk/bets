package com.epam.bets.filter;


import com.epam.bets.entity.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.bets.constant.PageConstant.INDEX_PAGE;
/**
 * The class provides security filter for servlet container which checks user role and sended command, and if they are null,
 * or command not equal to sign in or sign up commands, navigates to index page.
 *
 * @author Pashchuk Ksenia
 * @see Filter
 * @see WebFilter
 */

@WebFilter(urlPatterns = {"/*"},
        servletNames = {"BetServlet", "AjaxServlet"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = INDEX_PAGE)}
)
public class SecurityFilter implements Filter {

    private String indexPath;
    private static final String SIGN_IN = "sign_in";
    private static final String SIGN_UP = "sign_up";
    private static final String PARAM_NAME_ROLE = "role";
    private static final String PARAM_NAME_COMMAND = "command";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter("INDEX_PATH");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        boolean resourceRequest = httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + "/resources");
        HttpSession session = httpRequest.getSession();
        UserRole type = (UserRole) session.getAttribute(PARAM_NAME_ROLE);
        String command = httpRequest.getParameter(PARAM_NAME_COMMAND);
        if (!resourceRequest) {
            if (type == null) {
                if (command == null || (!command.equalsIgnoreCase(SIGN_IN) && !command.equalsIgnoreCase(SIGN_UP))) {
                    RequestDispatcher dispatcher = servletRequest.getServletContext().getRequestDispatcher(indexPath);
                    dispatcher.forward(servletRequest, servletResponse);
                    return;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
