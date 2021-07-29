package com.example.finalprojectpm.db.service;

import com.example.finalprojectpm.db.*;
import com.example.finalprojectpm.db.entity.Car;
import com.example.finalprojectpm.db.entity.CarCategory;
import com.example.finalprojectpm.db.entity.Order;
import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.DBException;

import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.junit.jupiter.api.*;


import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaxiServiceMakeOrderTest {
    private OrderDao orderDao;
    private CarDao carDao;
    private CarCategoryDao categoryDao;
    private UserDao userDao;
    private ProfileDao profileDao;
    private TaxiServiceMakeOrder taxiServiceMakeOrder;

    @BeforeAll
    static void initTestMode(){
        MySQLDAOFactory.setTestOn();
    }
    @BeforeEach
    void initTaxiService() {
        userDao = mock(UserDao.class);
        orderDao = mock(OrderDao.class);
        carDao = mock(CarDao.class);
        categoryDao = mock(CarCategoryDao.class);
        profileDao = mock(ProfileDao.class);
        taxiServiceMakeOrder = new TaxiServiceMakeOrder(carDao,orderDao,categoryDao,userDao,profileDao);
        Connection connection = mock(Connection.class);
        MySQLDAOFactory.setTestConnection(connection);
    }
    @AfterAll
    public static void setTestModeOff(){
        MySQLDAOFactory.setTestOff();
    }
    @Test
    void makeOrder() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        when(userDao.findUser(any(Connection.class),anyString())).thenReturn(new User());
        when(carDao.findCar(any(Connection.class), anyInt(),anyString())).thenReturn(new Car());
        when(carDao.updateCar(any(Connection.class), anyInt(), anyString())).thenReturn(true);
        when(categoryDao.findCarCategory(any(Connection.class),anyString())).thenReturn(new CarCategory());
        when(orderDao.insertOrder(any(Connection.class),any(Order.class))).thenReturn(true);
        when(profileDao.updateProfileSWithdrawBalance(any(Connection.class),anyInt(),anyDouble())).thenReturn(true);
        assertNotNull(taxiServiceMakeOrder.makeOrder(new String[]{"5","5"},new String[]{"Business","Standard"},"Україна, Дніпро, Кулагіна вулиця, 33","Україна, Дніпро, Кулагіна вулиця, 33","Login"));
    }
    @Test
    void makeOrderException() throws DBException, SQLException {
        when(userDao.findUser(any(Connection.class),anyString())).thenReturn(new User());
        when(carDao.findCar(any(Connection.class), anyInt(),anyString())).thenReturn(null);
        when(carDao.updateCar(any(Connection.class), anyInt(), anyString())).thenReturn(true);
        when(categoryDao.findCarCategory(any(Connection.class),anyString())).thenReturn(new CarCategory());
        when(orderDao.insertOrder(any(Connection.class),any(Order.class))).thenReturn(true);
        when(profileDao.updateProfileSWithdrawBalance(any(Connection.class),anyInt(),anyDouble())).thenReturn(true);
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationSendOrderMessageException.class,()->taxiServiceMakeOrder.makeOrder(new String[]{"5","5"},new String[]{"Business","Standard"},"Україна, Дніпро, Кулагіна вулиця, 33","Україна, Дніпро, Кулагіна вулиця, 33","Login"));
        assertEquals("Business 5",thrown.getMessage());
    }
}