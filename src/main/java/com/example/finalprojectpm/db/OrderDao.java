package com.example.finalprojectpm.db;

import com.example.finalprojectpm.db.entity.Order;
import com.example.finalprojectpm.db.exception.DBException;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OrderDao {
    boolean insertOrder(Connection connection, Order order) throws DBException, SQLException;
    boolean insertOrders(Connection connection,Order... orders) throws DBException, SQLException;
    boolean deleteOrder(Connection connection,Order order)throws DBException, SQLException;
    Order findOrder(Connection connection,Order order) throws DBException, SQLException;
    boolean updateOrder(Connection connection,Order order,int newCarId) throws DBException, SQLException;
    List<Order> findAllOrders(Connection connection) throws DBException, SQLException;
    int orderCount(Connection connection) throws DBException, SQLException;
    List<Order> findFilSortOrders(Connection connection, Map<String,String> filters, String sortedColumn, boolean descending, int startRow, int rowsPerPage) throws DBException, SQLException;
}
