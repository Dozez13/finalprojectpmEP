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
 * Security filter for admin pages
 * @author Pavlo Manuilenko
 */
@WebFilter(filterName = "SessionAdminFilter")
public class SessionAdminFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(SessionAdminFilter.class);
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
        if (!session.getAttribute("userType").equals("administrator")) {
            res.sendRedirect(req.getContextPath() + "/pages/index");
            // No logged-in as administrator found, so redirect to login page.
        } else {
            chain.doFilter(request, response); // Logged-in userAdmin found, so just continue request.
        }
    }
    @Override
    public void destroy() {
        LOGGER.debug("Filter destruction starts");
        //do nothing
        LOGGER.debug("Filter destruction ends");
    }
}

