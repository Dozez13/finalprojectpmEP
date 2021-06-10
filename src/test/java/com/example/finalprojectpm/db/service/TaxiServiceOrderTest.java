package com.example.finalprojectpm.db.service;

import com.example.finalprojectpm.db.OrderDao;
import com.example.finalprojectpm.db.entity.Order;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.DBException;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import hthurow.tomcatjndi.TomcatJNDI;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaxiServiceOrderTest {
    private OrderDao orderDao;
    private TaxiServiceOrder taxiServiceOrder;
    private static TomcatJNDI tomcatJNDI;
    @BeforeAll
    static void initJNDI(){
        tomcatJNDI = new TomcatJNDI();
        tomcatJNDI.processContextXml(new File("src/main/webapp/META-INF/context.xml"));
        tomcatJNDI.start();
    }
    @BeforeEach
    void initTaxiService() {
        orderDao = mock(OrderDao.class);
        taxiServiceOrder = new TaxiServiceOrder(orderDao);
    }
    @AfterAll
    static void dataSourceClearEnv(){
        tomcatJNDI.tearDown();
    }
    @Test
    void insertOrder() throws DBException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        Order order = new Order();
        when(orderDao.insertOrder(any(Connection.class),any(Order.class))).thenReturn(true);
        assertTrue(taxiServiceOrder.insertOrder(order));
    }
    @Test
    void insertOrderException() throws DBException, SQLException {
        Order order = new Order();
        when(orderDao.insertOrder(any(Connection.class),any(Order.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()-> taxiServiceOrder.insertOrder(order));
        assertEquals("can't close some resources",thrown.getMessage());
    }

    @Test
    void insertOrders() throws DBException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        Order[]orders = new Order[5];
        when(orderDao.insertOrders(any(Connection.class),any())).thenReturn(true);
        assertTrue(taxiServiceOrder.insertOrders(orders));
    }
    @Test
    void insertOrdersException() throws DBException, SQLException{
        Order[]orders = new Order[5];
        when(orderDao.insertOrders(any(Connection.class),any())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()->taxiServiceOrder.insertOrders(orders));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void deleteOrder() throws DBException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        Order order = new Order();
        when(orderDao.deleteOrder(any(Connection.class),any(Order.class))).thenReturn(true);
        assertTrue(taxiServiceOrder.deleteOrder(order));
    }
    @Test
    void deleteOrderException() throws DBException, SQLException{
        Order order = new Order();
        when(orderDao.deleteOrder(any(Connection.class),any(Order.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()->taxiServiceOrder.deleteOrder(order));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void findOrder() throws ApplicationEXContainer.ApplicationCanNotChangeException, DBException, SQLException {
        Order order = new Order();
        when(orderDao.findOrder(any(Connection.class),any(Order.class))).thenReturn(new Order());
        assertNotNull(taxiServiceOrder.findOrder(order));
    }
    @Test
    void findOrderException() throws DBException, SQLException {
        Order order = new Order();
        when(orderDao.findOrder(any(Connection.class),any(Order.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()->taxiServiceOrder.findOrder(order));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void updateOrder() throws DBException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
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
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()->taxiServiceOrder.updateOrder(order,newCarId));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void findAllOrders() throws DBException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        when(orderDao.findAllOrders(any(Connection.class))).thenReturn(new ArrayList<>());
        assertNotNull(taxiServiceOrder.findAllOrders());
    }
    @Test
    void findAllOrdersException() throws DBException, SQLException {
        when(orderDao.findAllOrders(any(Connection.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()->taxiServiceOrder.findAllOrders());
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void orderCount() throws DBException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        when(orderDao.orderCount(any(Connection.class))).thenReturn(1);
        assertEquals(1,taxiServiceOrder.orderCount());
    }
    @Test
    void orderCountException() throws DBException, SQLException {
        when(orderDao.orderCount(any(Connection.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()->taxiServiceOrder.orderCount());
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void findFilSortOrders() throws DBException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        Map<String,String> columnsValues = new HashMap<>();
        String sortedColumn = "someColumn";
        int startRow = 1;
        int rowsPerPage = 3;
        when(orderDao.findFilSortOrders(any(Connection.class), ArgumentMatchers.<Map<String,String>>any(),anyString(),anyBoolean(),anyInt(),anyInt())).thenReturn(new ArrayList<>());
        assertNotNull(taxiServiceOrder.findFilSortOrders(columnsValues,sortedColumn, true,startRow,rowsPerPage));
    }
    @Test
    void findFilSortOrdersException() throws DBException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        Map<String,String> columnsValues = new HashMap<>();
        String sortedColumn = "someColumn";
        int startRow = 1;
        int rowsPerPage = 3;
        when(orderDao.findFilSortOrders(any(Connection.class), ArgumentMatchers.<Map<String,String>>any(),anyString(),anyBoolean(),anyInt(),anyInt())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()->taxiServiceOrder.findFilSortOrders(columnsValues,sortedColumn, true,startRow,rowsPerPage));
        assertEquals("can't close some resources",thrown.getMessage());
    }
}