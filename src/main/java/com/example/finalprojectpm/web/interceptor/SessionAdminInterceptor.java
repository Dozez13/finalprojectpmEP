package com.example.finalprojectpm.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionAdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        if (!session.getAttribute("userType").equals("administrator")) {
            response.sendRedirect(request.getContextPath() + "/pages/index");
            // No logged-in as administrator found, so redirect to login page.
            return false;
        }
        return true;
    }
}
