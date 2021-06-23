package com.example.finalprojectpm.web.command;

import com.example.finalprojectpm.db.DAOFactory;
import com.example.finalprojectpm.db.OrderDao;
import com.example.finalprojectpm.db.entity.Order;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import com.example.finalprojectpm.db.service.TaxiServiceOrder;
import com.example.finalprojectpm.web.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MyOrders implements Action{
    private static final Logger LOGGER = LogManager.getLogger(MyOrders.class);
    @Override
    public void execute(View view) throws Exception {
        LOGGER.info("LoginAction is invoked");
        HttpServletRequest request = view.getRequest();
        DAOFactory dao =(MySQLDAOFactory)request.getServletContext().getAttribute("MySQLFactory");
        OrderDao orderDao = dao.getOrderDao();
        TaxiServiceOrder taxiServiceOrder = new TaxiServiceOrder(orderDao);
        int startRow = 0;
        int rowsPerPage = 3;
        int currentPage = 1;
        String strStartRow;
        String strCurrentPage;
        if((strStartRow =request.getParameter("startRow"))!=null){
            try{
                int temp = Integer.parseInt(strStartRow);
                startRow = Math.max(temp, 0);
            }catch (NumberFormatException e){
                LOGGER.error(e.getMessage());
                throw new ApplicationEXContainer.ApplicationCanNotChangeException(e.getMessage());
            }
        }
        if((strCurrentPage =request.getParameter("currentPage"))!=null){
            try{
                int temp = Integer.parseInt(strCurrentPage);
                currentPage = temp>0?temp:1;
            }catch (NumberFormatException e){
                LOGGER.error(e.getMessage());
                throw new ApplicationEXContainer.ApplicationCanNotChangeException(e.getMessage());
            }
        }
        List<Order> orders = taxiServiceOrder.findOrdersByUser(((int)(request.getSession().getAttribute("userId"))),startRow,rowsPerPage);
        int orderCount = taxiServiceOrder.orderCountByUser(((int)(request.getSession().getAttribute("userId"))));
        int numOfPages = (int)(Math.ceil(orderCount/ (double) rowsPerPage));
        System.out.println("startRow "+startRow+" currentPage "+currentPage+" total order "+numOfPages+" rowsPerPage"+rowsPerPage);
          request.setAttribute("myOrders",orders);
          request.setAttribute("startRow",startRow);
          request.setAttribute("currentPage",currentPage);
          request.setAttribute("totalOrders",numOfPages);
        view.setView(request.getPathInfo());
    }
}
