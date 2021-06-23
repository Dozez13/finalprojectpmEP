package com.example.finalprojectpm.web.command;

import com.example.finalprojectpm.db.DAOFactory;
import com.example.finalprojectpm.db.ProfileDao;
import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import com.example.finalprojectpm.db.service.TaxiServiceProfile;
import com.example.finalprojectpm.db.service.TaxiServiceUser;
import com.example.finalprojectpm.web.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Add money make action that invoked after button click
 * @author Pavlo Manuilenko
 */
public class AddMoneyMAction implements Action{

    @Override
    public void execute(View view) throws Exception {
        HttpServletRequest request = view.getRequest();
        int amount = Integer.parseInt(request.getParameter("amountM"));
        HttpSession session = request.getSession();
        DAOFactory factory =(MySQLDAOFactory)request.getServletContext().getAttribute("MySQLFactory");
        UserDao userDao =  factory.getUserDao();
        ProfileDao profileDao = factory.getProfileDao();
        TaxiServiceUser taxiServiceUser = new TaxiServiceUser(userDao);
        TaxiServiceProfile taxiServiceProfile = new TaxiServiceProfile(profileDao);
        User user = taxiServiceUser.findUser((String)session.getAttribute("Login"));
        taxiServiceProfile.updateProfileAddBalance(user.getUserId(),amount);
        view.setView(request.getContextPath() + "/pages/index");

    }
}
