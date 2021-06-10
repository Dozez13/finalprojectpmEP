package com.example.finalprojectpm.db.service;

import com.example.finalprojectpm.db.CarCategoryDao;
import com.example.finalprojectpm.db.entity.CarCategory;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.DBException;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import hthurow.tomcatjndi.TomcatJNDI;
import org.junit.jupiter.api.*;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaxiServiceCarCategoryTest {
    private CarCategoryDao carCategoryDao;
    private TaxiServiceCarCategory serviceCarCategory;
    private static TomcatJNDI tomcatJNDI;
    @BeforeAll
    static void initJNDI(){
        tomcatJNDI = new TomcatJNDI();
        tomcatJNDI.processContextXml(new File("src/main/webapp/META-INF/context.xml"));
        tomcatJNDI.start();
    }
    @BeforeEach
    void initMocks(){
        carCategoryDao = mock(CarCategoryDao.class);
        serviceCarCategory = new TaxiServiceCarCategory(carCategoryDao);
    }
    @AfterAll
    static void clearEnv(){
        tomcatJNDI.tearDown();
    }
    @Test
    void insertCarCategory() throws DBException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        CarCategory carCategory = new CarCategory();
        when(carCategoryDao.insertCarCategory(any(Connection.class),any(CarCategory.class))).thenReturn(true);
        assertTrue(serviceCarCategory.insertCarCategory(carCategory));
    }
    @Test
    void insertCarCategoryException() throws DBException, SQLException {
        CarCategory carCategory = new CarCategory();
        when(carCategoryDao.insertCarCategory(any(Connection.class),any(CarCategory.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()-> serviceCarCategory.insertCarCategory(carCategory));
        assertEquals("can't close some resources",thrown.getMessage());
    }

    @Test
    void deleteCarCategory() throws DBException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        String carCategory = "Business";
        when(carCategoryDao.deleteCarCategory(any(Connection.class),anyString())).thenReturn(true);
        assertTrue(serviceCarCategory.deleteCarCategory(carCategory));
    }
    @Test
    void deleteCarCategoryException() throws DBException, SQLException {
        String carCategory = "Business";
        when(carCategoryDao.deleteCarCategory(any(Connection.class),anyString())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()-> serviceCarCategory.deleteCarCategory(carCategory));
        assertEquals("can't close some resources",thrown.getMessage());
    }

    @Test
    void findCarCategory() throws DBException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        String carCategory = "Business";
        when(carCategoryDao.findCarCategory(any(Connection.class),anyString())).thenReturn(new CarCategory());
        assertNotNull(serviceCarCategory.findCarCategory(carCategory));
    }
    @Test
    void findCarCategoryException() throws DBException, SQLException {
        String carCategory = "Business";
        when(carCategoryDao.findCarCategory(any(Connection.class),anyString())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()-> serviceCarCategory.findCarCategory(carCategory));
        assertEquals("can't close some resources",thrown.getMessage());
    }

    @Test
    void updateCarCategory() throws DBException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        String carCategory = "Business";
        int newPrice = 15;
        when(carCategoryDao.updateCarCategory(any(Connection.class),anyString(),anyDouble())).thenReturn(true);
        assertTrue(serviceCarCategory.updateCarCategory(carCategory,newPrice));
    }
    @Test
    void updateCarCategoryException() throws DBException, SQLException {
        String carCategory = "Business";
        int newPrice = 15;
        when(carCategoryDao.updateCarCategory(any(Connection.class),anyString(),anyDouble())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()-> serviceCarCategory.updateCarCategory(carCategory,newPrice));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void findAllCarC() throws DBException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        when(carCategoryDao.findAllCarC(any(Connection.class))).thenReturn(new ArrayList<>());
        assertNotNull(serviceCarCategory.findAllCarC());
    }
    @Test
    void findAllCarCException() throws DBException, SQLException {
        when(carCategoryDao.findAllCarC(any(Connection.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()-> serviceCarCategory.findAllCarC());
        assertEquals("can't close some resources",thrown.getMessage());
    }

    @Test
    void findExistingCarC() throws DBException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        when(carCategoryDao.findExistingCarC(any(Connection.class))).thenReturn(new ArrayList<>());
        assertNotNull(serviceCarCategory.findExistingCarC());
    }
    @Test
    void findExistingCarCException() throws DBException, SQLException{
        when(carCategoryDao.findExistingCarC(any(Connection.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()-> serviceCarCategory.findExistingCarC());
        assertEquals("can't close some resources",thrown.getMessage());
    }
}