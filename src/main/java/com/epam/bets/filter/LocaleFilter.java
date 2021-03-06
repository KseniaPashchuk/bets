package com.epam.bets.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The class provides user locale filter for user session which saves default locale i not exists in session.
 *
 * @author Pashchuk Ksenia
 * @see Filter
 * @see WebFilter
 */
@WebFilter(
        urlPatterns = {"/*"},
        servletNames = {"BetServlet", "AjaxServlet"},
        initParams = {@WebInitParam(name = "locale", value = "locale")},
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
)
public class LocaleFilter implements Filter {
    private static final String DEFAULT_LOCALE = "en_EN";
    private String locale;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        locale = filterConfig.getInitParameter("locale");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();
        String userLocale = (String) session.getAttribute(locale);
        if (userLocale == null) {
            userLocale = DEFAULT_LOCALE;
            session.setAttribute(locale, userLocale);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        locale = null;
    }
}
