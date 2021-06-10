package com.example.finalprojectpm.db.mysql;

import com.example.finalprojectpm.db.OrderDao;
import com.example.finalprojectpm.db.entity.Order;
import com.example.finalprojectpm.db.exception.DBException;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MySQLOrderDaoTest {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private OrderDao orderDao;
    private ResultSet resultSet;
    @BeforeEach
    void init() throws SQLException {
        orderDao = new MySQLOrderDao();
        preparedStatement = mock(PreparedStatement.class);
        connection = mock(Connection.class);
        resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

    }


    @Test
    void deleteOrder() throws SQLException, DBException {
        Order order = new Order();
        order.setOrderId(1);
        order.setUserDestination("dest");
        order.setUserAddress("uAddress");
        order.setOrderDate(LocalDateTime.of(2015,6,15,22,35,55));
        order.setCarId(1);
        order.setUserId(1);
        order.setOrderCost(25);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean result = orderDao.deleteOrder(connection,order);
        assertTrue(result);
    }

    @Test
    void findOrder() throws SQLException, DBException {
        Order order = new Order();
        order.setOrderId(1);
        order.setUserDestination("dest");
        order.setUserAddress("uAddress");
        order.setOrderDate(LocalDateTime.of(2015,6,15,22,35,55));
        order.setCarId(1);
        order.setUserId(1);
        order.setOrderCost(25);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("orderId")).thenReturn(1);
        when(resultSet.getInt("userId")).thenReturn(1);
        when(resultSet.getInt("carid")).thenReturn(1);
        when(resultSet.getString(anyString())).thenReturn("SomeAddress");
        when(resultSet.getDouble("orderCost")).thenReturn(15.0);
        when(resultSet.getTimestamp("orderDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        Order result = orderDao.findOrder(connection,order);
        assertNotNull(result);
    }

    @Test
    void updateOrder() throws SQLException, DBException {
        Order order = new Order();
        order.setOrderId(1);
        order.setUserDestination("dest");
        order.setUserAddress("uAddress");
        order.setOrderDate(LocalDateTime.of(2015,6,15,22,35,55));
        order.setCarId(1);
        order.setUserId(1);
        order.setOrderCost(25);
        int newCarId = 5;
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean result = orderDao.updateOrder(connection,order,newCarId);
        assertTrue(result);
    }

    @Test
    void findAllOrders() throws SQLException, DBException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("orderId")).thenReturn(1);
        when(resultSet.getInt("userId")).thenReturn(1);
        when(resultSet.getInt("carid")).thenReturn(1);
        when(resultSet.getString(anyString())).thenReturn("SomeAddress");
        when(resultSet.getDouble("orderCost")).thenReturn(15.0);
        when(resultSet.getTimestamp("orderDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        List<Order> result = orderDao.findAllOrders(connection);
        assertNotNull(result);
    }

    @Test
    void orderCount() throws SQLException, DBException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);
        int count = orderDao.orderCount(connection);
        assertEquals(1,count);

    }

    @Test
    void findFilSortOrders() throws DBException, SQLException {
        Map<String,String> filters= new HashMap<>();
        filters.put("columnName","str");
        String sortedColumn = "columnName";
        int page = 3;
        int rowsPerPage = 3;
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("orderId")).thenReturn(1);
        when(resultSet.getInt("userId")).thenReturn(1);
        when(resultSet.getInt("carId")).thenReturn(1);
        when(resultSet.getString(anyString())).thenReturn("SomeAddress");
        when(resultSet.getDouble("orderCost")).thenReturn(15.0);
        when(resultSet.getTimestamp("orderDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        List<Order> orders = orderDao.findFilSortOrders(connection,filters,sortedColumn,true,page,rowsPerPage);
        assertNotNull(orders);
    }
}