package com.example.finalprojectpm.web.command;


import com.example.finalprojectpm.web.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Registration action that send us to registration page
 * @author Pavlo Manuilenko
 */
public class RegistrationAction implements Action{
    private static final Logger LOGGER = LogManager.getLogger(RegistrationAction.class);
    @Override
    public void execute(View view) throws Exception {
        LOGGER.info("RegistrationAction is invoked");
        HttpServletRequest request = view.getRequest();
        view.setView(request.getPathInfo());
    }
}
