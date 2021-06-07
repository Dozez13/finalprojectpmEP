package com.example.finalprojectpm.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SessionUserFilter")
public class SessionUserFilter implements Filter {
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
}
