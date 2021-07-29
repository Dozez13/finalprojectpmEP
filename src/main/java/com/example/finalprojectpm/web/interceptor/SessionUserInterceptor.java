package com.example.finalprojectpm.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionUserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("Login") == null||session.getAttribute("userType")==null) {
            response.sendRedirect(request.getContextPath() + "/pages/index");
            // No logged-in user found, so redirect to login page.
            return false;
        }
        return true;
    }
}
