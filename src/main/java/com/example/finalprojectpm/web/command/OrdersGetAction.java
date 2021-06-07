package com.example.finalprojectpm.web.command;


import com.example.finalprojectpm.db.DAOFactory;
import com.example.finalprojectpm.db.OrderDao;
import com.example.finalprojectpm.db.entity.Order;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import com.example.finalprojectpm.db.service.TaxiServiceOrder;
import com.example.finalprojectpm.web.view.View;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class OrdersGetAction implements Action{
    private static final Logger LOGGER = LogManager.getLogger(OrdersGetAction.class);
    @Override
    public void execute(View view) throws Exception {
        LOGGER.info("OrdersGetAction is invoked");
        HttpServletRequest request = view.getRequest();
        DAOFactory dao =(MySQLDAOFactory)request.getServletContext().getAttribute("MySQLFactory");
        ObjectMapper mapper = new ObjectMapper();
        OrderDao daoOrder = dao.getOrderDao();
        TaxiServiceOrder taxiServiceOrder = new TaxiServiceOrder(daoOrder);
        Map<String,String> filters= null ;
        String sortedColumn = request.getParameter("sortBy");
        String filter = request.getParameter("filter");
        boolean descending = Boolean.parseBoolean(request.getParameter("descending"));
        int startRow = Integer.parseInt(request.getParameter("startRow"));
        int rowsPerPage = Integer.parseInt(request.getParameter("rowsPerPage"));
        if(!filter.equals("{}")){
            filters = mapper.readValue(filter, new TypeReference<Map<String, String>>() {});
        }
        List<Order> getOrders = taxiServiceOrder.findFilSortOrders(filters,sortedColumn,descending,startRow,rowsPerPage);
        LOGGER.info("Filtered and Sorted Order count is {}",getOrders.size());
        request.setAttribute("ordersJsonValues",mapper.writeValueAsString(getOrders));
        view.setView(request.getPathInfo());
    }
}
