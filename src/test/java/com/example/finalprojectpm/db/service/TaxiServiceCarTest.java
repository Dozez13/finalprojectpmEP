package com.example.finalprojectpm.db.service;

import com.example.finalprojectpm.db.CarDao;
import com.example.finalprojectpm.db.entity.Car;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
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
class TaxiServiceCarTest {
    private CarDao carDao;
    private TaxiServiceCar carService;
    private static TomcatJNDI tomcatJNDI;
    @BeforeAll
    static void initJNDI(){
        tomcatJNDI = new TomcatJNDI();
        tomcatJNDI.processContextXml(new File("src/main/webapp/META-INF/context.xml"));
        tomcatJNDI.start();
    }
    @BeforeEach
    void initTaxiService() {
        carDao = mock(CarDao.class);
        carService = new TaxiServiceCar(carDao);
    }
    @AfterAll
    static void dataSourceClearEnv(){
        tomcatJNDI.tearDown();
    }

    @Test
    void insertCar() throws SQLException, MySQLEXContainer.MySQLDBExecutionException, ApplicationEXContainer.ApplicationCanNotChangeException {
        Car car = new Car();
        when(carDao.insertCar(any(Connection.class), any(Car.class))).thenReturn(true);
        assertTrue(carService.insertCar(car));
    }
    @Test
    void insertCarException() throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        Car car = new Car();
        when(carDao.insertCar(any(Connection.class), any(Car.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable exception = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()-> carService.insertCar(car));
        assertEquals("can't close some resources",exception.getMessage());
    }



    @Test
    void deleteCar() throws MySQLEXContainer.MySQLDBExecutionException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        int carId = 5;
        when(carDao.deleteCar(any(Connection.class),anyInt())).thenReturn(true);
        assertTrue(carService.deleteCar(carId));
    }

    @Test
    void deleteCarException() throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        int carId = 5;
        when(carDao.deleteCar(any(Connection.class),anyInt())).thenThrow(new SQLException("can't close some resources"));
        Throwable exception = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()-> carService.deleteCar(carId));
        assertEquals("can't close some resources",exception.getMessage());
    }
    @Test
    void findCar() throws MySQLEXContainer.MySQLDBExecutionException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        int carId = 5;
        when(carDao.findCar(any(Connection.class), anyInt())).thenReturn(new Car());
        assertNotNull(carService.findCar(carId));
    }
    @Test
    void findCarException() throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        int carId = 5;
        when(carDao.findCar(any(Connection.class),anyInt())).thenThrow(new SQLException("can't close some resources"));
        Throwable exception = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()-> carService.findCar(carId));
        assertEquals("can't close some resources",exception.getMessage());
    }
    @Test
    void testFindCar() throws MySQLEXContainer.MySQLDBExecutionException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        int numOfPass = 5;
        String carCategory = "Business";
        when(carDao.findCar(any(Connection.class), anyInt(), anyString())).thenReturn(new Car());
        assertNotNull(carService.findCar(numOfPass, carCategory));
    }
    @Test
    void testFindCarException() throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        int numOfPass = 5;
        String carCategory = "Business";
        when(carDao.findCar(any(Connection.class), anyInt(), anyString())).thenThrow(new SQLException("can't close some resources"));
        Throwable exception = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()-> carService.findCar(numOfPass,carCategory));
        assertEquals("can't close some resources",exception.getMessage());
    }
    @Test
    void updateCar() throws MySQLEXContainer.MySQLDBExecutionException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        int numOfPass = 5;
        String carCategory = "Business";
        when(carDao.updateCar(any(Connection.class), anyInt(), anyString())).thenReturn(true);
        assertTrue(carService.updateCar(numOfPass, carCategory));
    }
    @Test
    void updateCarException() throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        int numOfPass = 5;
        String carCategory = "Business";
        when(carDao.updateCar(any(Connection.class), anyInt(), anyString())).thenThrow(new SQLException("can't close some resources"));
        Throwable exception = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()-> carService.updateCar(numOfPass,carCategory));
        assertEquals("can't close some resources",exception.getMessage());
    }
    @Test
    void findAllCars() throws MySQLEXContainer.MySQLDBExecutionException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        when(carDao.findAllCars(any(Connection.class))).thenReturn(new ArrayList<>());
        assertNotNull(carService.findAllCars());
    }
    @Test
    void findAllCarsException() throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        when(carDao.findAllCars(any(Connection.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable exception = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()-> carService.findAllCars());
        assertEquals("can't close some resources",exception.getMessage());
    }
    @Test
    void findNumberCarByCat() throws MySQLEXContainer.MySQLDBExecutionException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        when(carDao.findNumberCarByCat(any(Connection.class))).thenReturn(new ArrayList<>());
        assertNotNull(carService.findNumberCarByCat());
    }
    @Test
    void findNumberCarByCatException() throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        when(carDao.findNumberCarByCat(any(Connection.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable exception = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()-> carService.findNumberCarByCat());
        assertEquals("can't close some resources",exception.getMessage());
    }
}