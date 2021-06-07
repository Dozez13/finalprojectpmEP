package com.example.finalprojectpm.web.command;


import com.example.finalprojectpm.db.DAOFactory;
import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import com.example.finalprojectpm.db.service.TaxiServiceUser;
import com.example.finalprojectpm.web.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DoRegistrationAction implements Action{
    private static final Logger LOGGER = LogManager.getLogger(DoRegistrationAction.class);
    @Override
    public void execute(View view) throws Exception {
        LOGGER.info("DoRegistrationAction is invoked");
        HttpServletRequest request = view.getRequest();
        DAOFactory factory =(MySQLDAOFactory)request.getServletContext().getAttribute("MySQLFactory");
        UserDao dao =  factory.getUserDao();
        TaxiServiceUser taxiServiceUser = new TaxiServiceUser(dao);
        String login = request.getParameter("login");
        String email = request.getParameter("Email");
        String psw = request.getParameter("psw");
        User user = new User();
        user.setLogin(login);
        user.setUserEmail(email);
        user.setPassword(psw);
        user.setUserType("client");
        view.setView(request.getContextPath() + "/pages/index");
        try{
            taxiServiceUser.insertUser(user);
        }catch (ApplicationEXContainer.ApplicationCanChangeException e) {

            view.setView(request.getContextPath() + "/pages/guest/registration?registrationMessage=" + e.getMessage());
        }

    }
}
