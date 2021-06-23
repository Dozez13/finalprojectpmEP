package com.example.finalprojectpm.web.command;


import com.example.finalprojectpm.db.DAOFactory;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import com.example.finalprojectpm.db.service.TaxiServiceMakeOrder;
import com.example.finalprojectpm.web.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Order make action that invokes after button click
 * @author Pavlo Manuilenko
 */
public class OrderMAction implements Action{
    private static final Logger LOGGER = LogManager.getLogger(OrderMAction.class);
    @Override
    public void execute(View view) throws Exception {
        LOGGER.info("OrderMAction is invoked");
        HttpServletRequest request = view.getRequest();
        DAOFactory dao =(MySQLDAOFactory)request.getServletContext().getAttribute("MySQLFactory");
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("Login");
        String userAddress = request.getParameter("userAddress");
        String userDestination = request.getParameter("userDestination");
        String[] stingNumbers = request.getParameterValues("numOfPas");
        String[] categories = request.getParameterValues("categories");
        if(stingNumbers != null && stingNumbers.length > 0&&categories != null && categories.length > 0) {
            String message;
            TaxiServiceMakeOrder orderService = new TaxiServiceMakeOrder(dao.getCarDao(),dao.getOrderDao(),dao.getCarCategoryDao(),dao.getUserDao(),dao.getProfileDao());
            try{
                message = orderService.makeOrder(stingNumbers,categories,userAddress,userDestination,login);
                view.setView(request.getContextPath() + "/pages/user/order?takenTime="+message);
            }catch (ApplicationEXContainer.ApplicationCanChangeException e){
                LOGGER.error(e.getMessage());
                message = e.getMessage();
                view.setView(request.getContextPath() + "/pages/user/order?NotAvailable=fail&orderData="+message);
            }

        }
    }
}
