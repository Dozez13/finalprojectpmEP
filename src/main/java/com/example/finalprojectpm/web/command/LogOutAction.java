package com.example.finalprojectpm.web.command;


import com.example.finalprojectpm.web.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogOutAction implements Action{
    private static final Logger LOGGER = LogManager.getLogger(LogOutAction.class);
    @Override
    public void execute(View view) throws Exception {
        LOGGER.info("LogOutAction is invoked");
        HttpServletRequest request = view.getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            LOGGER.info("Invalidated user {}",session.getAttribute("Login"));
            session.removeAttribute("Login");
            session.invalidate();

        }
        view.setView(request.getContextPath()+"/pages/index");
    }
}
