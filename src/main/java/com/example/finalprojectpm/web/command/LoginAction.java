package com.example.finalprojectpm.web.command;


import com.example.finalprojectpm.db.DAOFactory;
import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import com.example.finalprojectpm.db.service.TaxiServiceUser;
import com.example.finalprojectpm.web.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Login action
 * @author Pavlo Manuilenko
 */
public class LoginAction implements Action{
    private static final Logger LOGGER = LogManager.getLogger(LoginAction.class);
    @Override
    public void execute(View view) throws Exception {
        LOGGER.info("LoginAction is invoked");
        HttpServletRequest request = view.getRequest();
        DAOFactory dao =(MySQLDAOFactory)request.getServletContext().getAttribute("MySQLFactory");
        UserDao userDao = dao.getUserDao();
        TaxiServiceUser taxiServiceUser = new TaxiServiceUser(userDao);
        String login = request.getParameter("login");
        String psw = request.getParameter("psw");
        User user = taxiServiceUser.findUser(login);
        if(user!=null&&taxiServiceUser.validateUser(login,psw)) {
            LOGGER.debug("Get Login from user, Login Is {}",login);
            LOGGER.debug("Get Password from user, Password Is {}",psw);
            HttpSession session = request.getSession();
            session.setAttribute("Login",login);
            session.setAttribute("userType",user.getUserType());
            session.setAttribute("userId",user.getUserId());
            view.setView(request.getContextPath()+"/pages/index");
        } else{
            String error= "Your login or password is wrong";
            view.setView(request.getContextPath()+"/pages/index?errorMessage="+error);
        }
    }
}
