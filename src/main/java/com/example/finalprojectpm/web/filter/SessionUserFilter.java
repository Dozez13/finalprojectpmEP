package com.example.finalprojectpm.web.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Security filter for user pages
 * @author Pavlo Manuilenko
 */
@WebFilter(filterName = "SessionUserFilter")
public class SessionUserFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(SessionUserFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("Filter initialization starts");
        //do nothing
        LOGGER.debug("Filter initialization ends");
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("Login") == null||session.getAttribute("userType")==null) {
            res.sendRedirect(req.getContextPath() + "/pages/index");
            // No logged-in user found, so redirect to login page.
        } else {
            chain.doFilter(request, response); // Logged-in user found, so just continue request.
        }
    }
    @Override
    public void destroy() {
        LOGGER.debug("Filter destruction starts");
        //do nothing
        LOGGER.debug("Filter destruction ends");
    }
}
