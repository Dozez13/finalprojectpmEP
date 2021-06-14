package com.example.finalprojectpm.web.command;


import com.example.finalprojectpm.db.DAOFactory;
import com.example.finalprojectpm.db.OrderDao;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import com.example.finalprojectpm.db.service.TaxiServiceOrder;
import com.example.finalprojectpm.web.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Orders count action
 * @author Pavlo Manuilenko
 */
public class OrdersCountAction implements Action{
    private static final Logger LOGGER = LogManager.getLogger(OrdersCountAction.class);
    @Override
    public void execute(View view) throws Exception {
        LOGGER.info("OrdersCountAction is invoked");
        HttpServletRequest request = view.getRequest();
        DAOFactory dao =(MySQLDAOFactory)request.getServletContext().getAttribute("MySQLFactory");
        OrderDao daoOrder = dao.getOrderDao();
        TaxiServiceOrder taxiServiceOrder = new TaxiServiceOrder(daoOrder);
        int count = taxiServiceOrder.orderCount();
        LOGGER.info("Get Order count , count is {}",count);
        request.setAttribute("Count",count);
        view.setView(request.getPathInfo());
    }
}
