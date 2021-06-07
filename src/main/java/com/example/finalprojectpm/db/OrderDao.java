package com.example.finalprojectpm.db;

import com.example.finalprojectpm.db.entity.Order;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OrderDao {
    boolean insertOrder(Connection connection, Order order) throws MySQLEXContainer.MySQLDBLargeDataException, MySQLEXContainer.MySQLDBExecutionException, SQLException;
    boolean insertOrders(Connection connection,Order... orders) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    boolean deleteOrder(Connection connection,Order order) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    Order findOrder(Connection connection,Order order) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    boolean updateOrder(Connection connection,Order order,int newCarId) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    List<Order> findAllOrders(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    int orderCount(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    List<Order> findFilSortOrders(Connection connection, Map<String,String> filters, String sortedColumn, boolean descending, int startRow, int rowsPerPage) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
}
