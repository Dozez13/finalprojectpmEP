package com.example.finalprojectpm.db.service;

import com.example.finalprojectpm.db.CarCategoryDao;
import com.example.finalprojectpm.db.CarDao;
import com.example.finalprojectpm.db.OrderDao;
import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.entity.Car;
import com.example.finalprojectpm.db.entity.CarCategory;
import com.example.finalprojectpm.db.entity.Order;
import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import hthurow.tomcatjndi.TomcatJNDI;
import org.junit.jupiter.api.*;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaxiServiceMakeOrderTest {
    private OrderDao orderDao;
    private CarDao carDao;
    private CarCategoryDao categoryDao;
    private UserDao userDao;
    private TaxiServiceMakeOrder taxiServiceMakeOrder;
    private static TomcatJNDI tomcatJNDI;
    @BeforeAll
    static void initJNDI(){
        tomcatJNDI = new TomcatJNDI();
        tomcatJNDI.processContextXml(new File("src/main/webapp/META-INF/context.xml"));
        tomcatJNDI.start();
    }
    @BeforeEach
    void initTaxiService() {
        userDao = mock(UserDao.class);
        orderDao = mock(OrderDao.class);
        carDao = mock(CarDao.class);
        categoryDao = mock(CarCategoryDao.class);
        taxiServiceMakeOrder = new TaxiServiceMakeOrder(carDao,orderDao,categoryDao,userDao);
    }
    @AfterAll
    static void dataSourceClearEnv(){
        tomcatJNDI.tearDown();
    }
    @Test
    void makeOrder() throws MySQLEXContainer.MySQLDBExecutionException, SQLException, MySQLEXContainer.MySQLDBLargeDataException, ApplicationEXContainer.ApplicationCanNotChangeException, ApplicationEXContainer.ApplicationCanChangeException {
        when(userDao.findUser(any(Connection.class),anyString())).thenReturn(new User());
        when(carDao.findCar(any(Connection.class), anyInt(),anyString())).thenReturn(new Car());
        when(carDao.updateCar(any(Connection.class), anyInt(), anyString())).thenReturn(true);
        when(categoryDao.findCarCategory(any(Connection.class),anyString())).thenReturn(new CarCategory());
        when(orderDao.insertOrder(any(Connection.class),any(Order.class))).thenReturn(true);
        assertNotNull(taxiServiceMakeOrder.makeOrder(new String[]{"5","5"},new String[]{"Business","Standard"},"Україна, Дніпро, Кулагіна вулиця, 33","Україна, Дніпро, Кулагіна вулиця, 33","Login"));
    }
    @Test
    void makeOrderException() throws MySQLEXContainer.MySQLDBExecutionException, SQLException, MySQLEXContainer.MySQLDBLargeDataException{
        when(userDao.findUser(any(Connection.class),anyString())).thenReturn(new User());
        when(carDao.findCar(any(Connection.class), anyInt(),anyString())).thenReturn(null);
        when(carDao.updateCar(any(Connection.class), anyInt(), anyString())).thenReturn(true);
        when(categoryDao.findCarCategory(any(Connection.class),anyString())).thenReturn(new CarCategory());
        when(orderDao.insertOrder(any(Connection.class),any(Order.class))).thenReturn(true);
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanChangeException.class,()->taxiServiceMakeOrder.makeOrder(new String[]{"5","5"},new String[]{"Business","Standard"},"Україна, Дніпро, Кулагіна вулиця, 33","Україна, Дніпро, Кулагіна вулиця, 33","Login"));
        assertEquals("Business 5",thrown.getMessage());
    }
}