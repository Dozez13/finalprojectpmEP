package com.example.finalprojectpm.db.service;

import com.example.finalprojectpm.db.OrderDao;
import com.example.finalprojectpm.db.entity.Order;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.DBException;

import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaxiServiceOrderTest {
    private OrderDao orderDao;
    private TaxiServiceOrder taxiServiceOrder;

    @BeforeAll
    static void initTestMode(){
        MySQLDAOFactory.setTestOn();
    }
    @BeforeEach
    void initTaxiService() {
        orderDao = mock(OrderDao.class);
        taxiServiceOrder = new TaxiServiceOrder(orderDao);
        Connection connection = mock(Connection.class);
        MySQLDAOFactory.setTestConnection(connection);
    }
    @AfterAll
    public static void setTestModeOff(){
        MySQLDAOFactory.setTestOff();
    }
    @Test
    void insertOrder() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        Order order = new Order();
        when(orderDao.insertOrder(any(Connection.class),any(Order.class))).thenReturn(true);
        assertTrue(taxiServiceOrder.insertOrder(order));
    }
    @Test
    void insertOrderException() throws DBException, SQLException {
        Order order = new Order();
        when(orderDao.insertOrder(any(Connection.class),any(Order.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()-> taxiServiceOrder.insertOrder(order));
        assertEquals("can't close some resources",thrown.getMessage());
    }

    @Test
    void insertOrders() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        Order[]orders = new Order[5];
        when(orderDao.insertOrders(any(Connection.class),any())).thenReturn(true);
        assertTrue(taxiServiceOrder.insertOrders(orders));
    }
    @Test
    void insertOrdersException() throws DBException, SQLException{
        Order[]orders = new Order[5];
        when(orderDao.insertOrders(any(Connection.class),any())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()->taxiServiceOrder.insertOrders(orders));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void deleteOrder() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        Order order = new Order();
        when(orderDao.deleteOrder(any(Connection.class),any(Order.class))).thenReturn(true);
        assertTrue(taxiServiceOrder.deleteOrder(order));
    }
    @Test
    void deleteOrderException() throws DBException, SQLException{
        Order order = new Order();
        when(orderDao.deleteOrder(any(Connection.class),any(Order.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()->taxiServiceOrder.deleteOrder(order));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void findOrder() throws ApplicationEXContainer.ApplicationCantRecoverException, DBException, SQLException {
        Order order = new Order();
        when(orderDao.findOrder(any(Connection.class),any(Order.class))).thenReturn(new Order());
        assertNotNull(taxiServiceOrder.findOrder(order));
    }
    @Test
    void findOrderException() throws DBException, SQLException {
        Order order = new Order();
        when(orderDao.findOrder(any(Connection.class),any(Order.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()->taxiServiceOrder.findOrder(order));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void updateOrder() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        Order order = new Order();
        int newCarId = 5;
        when(orderDao.updateOrder(any(Connection.class),any(Order.class),anyInt())).thenReturn(true);
        assertTrue(taxiServiceOrder.updateOrder(order,newCarId));
    }
    @Test
    void updateOrderException() throws DBException, SQLException {
        Order order = new Order();
        int newCarId = 5;
        when(orderDao.updateOrder(any(Connection.class),any(Order.class),anyInt())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()->taxiServiceOrder.updateOrder(order,newCarId));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void findAllOrders() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        when(orderDao.findAllOrders(any(Connection.class))).thenReturn(new ArrayList<>());
        assertNotNull(taxiServiceOrder.findAllOrders());
    }
    @Test
    void findAllOrdersException() throws DBException, SQLException {
        when(orderDao.findAllOrders(any(Connection.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()->taxiServiceOrder.findAllOrders());
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void orderCount() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        when(orderDao.orderCount(any(Connection.class))).thenReturn(1);
        assertEquals(1,taxiServiceOrder.orderCount());
    }
    @Test
    void orderCountException() throws DBException, SQLException {
        when(orderDao.orderCount(any(Connection.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()->taxiServiceOrder.orderCount());
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void findFilSortOrders() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        Map<String,String> columnsValues = new HashMap<>();
        String sortedColumn = "someColumn";
        int startRow = 1;
        int rowsPerPage = 3;
        when(orderDao.findFilSortOrders(any(Connection.class), ArgumentMatchers.<Map<String,String>>any(),anyString(),anyBoolean(),anyInt(),anyInt())).thenReturn(new ArrayList<>());
        assertNotNull(taxiServiceOrder.findFilSortOrders(columnsValues,sortedColumn, true,startRow,rowsPerPage));
    }
    @Test
    void findFilSortOrdersException() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        Map<String,String> columnsValues = new HashMap<>();
        String sortedColumn = "someColumn";
        int startRow = 1;
        int rowsPerPage = 3;
        when(orderDao.findFilSortOrders(any(Connection.class), ArgumentMatchers.<Map<String,String>>any(),anyString(),anyBoolean(),anyInt(),anyInt())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()->taxiServiceOrder.findFilSortOrders(columnsValues,sortedColumn, true,startRow,rowsPerPage));
        assertEquals("can't close some resources",thrown.getMessage());
    }
}