package com.example.finalprojectpm.web.command;

import com.example.finalprojectpm.web.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * NoSuchAction is invoked if page doesn't exist
 * @author Pavlo Manuilenko
 */
public class NoSuchAction implements Action{
    private static final Logger LOGGER = LogManager.getLogger(NoSuchAction.class);
    @Override
    public void execute(View view) throws Exception {
        LOGGER.info("NoSuchAction is invoked");
        HttpServletRequest request = view.getRequest();
        view.setView(request.getContextPath()+"/pages/index");
    }
}
