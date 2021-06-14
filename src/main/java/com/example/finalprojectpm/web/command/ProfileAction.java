package com.example.finalprojectpm.web.command;

import com.example.finalprojectpm.db.DAOFactory;
import com.example.finalprojectpm.db.ProfileDao;
import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.entity.Profile;
import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import com.example.finalprojectpm.db.service.TaxiServiceProfile;
import com.example.finalprojectpm.db.service.TaxiServiceUser;
import com.example.finalprojectpm.web.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Profile Action
 * @author Pavlo Manuilenko
 */
public class ProfileAction implements Action{
    private static final Logger LOGGER = LogManager.getLogger(ProfileAction.class);
    @Override
    public void execute(View view) throws Exception {
        LOGGER.info("ProfileAction is invoked");
        HttpServletRequest request = view.getRequest();
        DAOFactory dao =(MySQLDAOFactory)request.getServletContext().getAttribute("MySQLFactory");
        ProfileDao profileDao = dao.getProfileDao();
        UserDao userDao = dao.getUserDao();
        TaxiServiceProfile taxiServiceProfile = new TaxiServiceProfile(profileDao);
        TaxiServiceUser taxiServiceUser = new TaxiServiceUser(userDao);
        HttpSession session = request.getSession();
        User user = taxiServiceUser.findUser((String) session.getAttribute("Login"));
        LOGGER.info("User has id {}",user.getUserId());
        Profile profile = taxiServiceProfile.findProfile(user.getUserId());
        request.setAttribute("profileFirstName",profile.getUserFirstName());
        LOGGER.info("User has profileFirstName : {}",profile.getUserFirstName());
        request.setAttribute("profileSurName",profile.getUserSurName());
        LOGGER.info("User has profileSurName : {}",profile.getUserSurName());
        request.setAttribute("profileRegistrationDate",profile.getUserRegistrationDate().toString());
        LOGGER.info("User has registration date : {}",profile.getUserRegistrationDate());
        request.setAttribute("profileAccountBalance",profile.getAccountBalance());
        view.setView(request.getPathInfo());
    }
}
