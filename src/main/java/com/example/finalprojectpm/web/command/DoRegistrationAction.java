package com.example.finalprojectpm.web.command;


import com.example.finalprojectpm.db.DAOFactory;
import com.example.finalprojectpm.db.ProfileDao;
import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import com.example.finalprojectpm.db.service.TaxiServiceRegistration;
import com.example.finalprojectpm.web.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Registration action that invoked after button click
 * @author Pavlo Manuilenko
 */
public class DoRegistrationAction implements Action{
    private static final Logger LOGGER = LogManager.getLogger(DoRegistrationAction.class);
    @Override
    public void execute(View view) throws Exception {
        LOGGER.info("DoRegistrationAction is invoked");
        HttpServletRequest request = view.getRequest();
        DAOFactory factory =(MySQLDAOFactory)request.getServletContext().getAttribute("MySQLFactory");
        UserDao userDao =  factory.getUserDao();
        ProfileDao profileDao = factory.getProfileDao();
        TaxiServiceRegistration taxiServiceRegistration = new TaxiServiceRegistration(userDao,profileDao);
        String firstName = request.getParameter("firstName");
        String surName = request.getParameter("surName");
        String login = request.getParameter("login");
        String email = request.getParameter("Email");
        String psw = request.getParameter("psw");
        view.setView(request.getContextPath() + "/pages/index");
        try{
            taxiServiceRegistration.doRegistration(firstName,surName,login,email,psw);
        }catch (ApplicationEXContainer.ApplicationCanChangeException e) {
            view.setView(request.getContextPath() + "/pages/guest/registration?registrationMessage=" + e.getMessage());
        }

    }
}
