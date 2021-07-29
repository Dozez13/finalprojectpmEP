package com.example.finalprojectpm.db.service;

import com.example.finalprojectpm.db.CarDao;
import com.example.finalprojectpm.db.entity.Car;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.DBException;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaxiServiceCarTest {
    private CarDao carDao;
    private TaxiServiceCar carService;

    @BeforeAll
    static void initTestMode(){
        MySQLDAOFactory.setTestOn();
    }

    @BeforeEach
    void initTaxiService() {
        carDao = mock(CarDao.class);
        carService = new TaxiServiceCar(carDao);
        Connection connection = mock(Connection.class);
        MySQLDAOFactory.setTestConnection(connection);

    }
    @AfterAll
    public static void setTestModeOff(){
        MySQLDAOFactory.setTestOff();
    }

    @Test
    void insertCar() throws SQLException, DBException, ApplicationEXContainer.ApplicationCantRecoverException {

        Car car = new Car();
        when(carDao.insertCar(any(Connection.class), any(Car.class))).thenReturn(true);
        assertTrue(carService.insertCar(car));
    }
    @Test
    void insertCarException() throws DBException, SQLException {
        Car car = new Car();
        when(carDao.insertCar(any(Connection.class), any(Car.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable exception = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()-> carService.insertCar(car));
        assertEquals("can't close some resources",exception.getMessage());
    }



    @Test
    void deleteCar() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        int carId = 5;
        when(carDao.deleteCar(any(Connection.class),anyInt())).thenReturn(true);
        assertTrue(carService.deleteCar(carId));
    }

    @Test
    void deleteCarException() throws DBException, SQLException {
        int carId = 5;
        when(carDao.deleteCar(any(Connection.class),anyInt())).thenThrow(new SQLException("can't close some resources"));
        Throwable exception = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()-> carService.deleteCar(carId));
        assertEquals("can't close some resources",exception.getMessage());
    }
    @Test
    void findCar() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        int carId = 5;
        when(carDao.findCar(any(Connection.class), anyInt())).thenReturn(new Car());
        assertNotNull(carService.findCar(carId));
    }
    @Test
    void findCarException() throws DBException, SQLException {
        int carId = 5;
        when(carDao.findCar(any(Connection.class),anyInt())).thenThrow(new SQLException("can't close some resources"));
        Throwable exception = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()-> carService.findCar(carId));
        assertEquals("can't close some resources",exception.getMessage());
    }
    @Test
    void testFindCar() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        int numOfPass = 5;
        String carCategory = "Business";
        when(carDao.findCar(any(Connection.class), anyInt(), anyString())).thenReturn(new Car());
        assertNotNull(carService.findCar(numOfPass, carCategory));
    }
    @Test
    void testFindCarException() throws DBException, SQLException {
        int numOfPass = 5;
        String carCategory = "Business";
        when(carDao.findCar(any(Connection.class), anyInt(), anyString())).thenThrow(new SQLException("can't close some resources"));
        Throwable exception = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()-> carService.findCar(numOfPass,carCategory));
        assertEquals("can't close some resources",exception.getMessage());
    }
    @Test
    void updateCar() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        int numOfPass = 5;
        String carCategory = "Business";
        when(carDao.updateCar(any(Connection.class), anyInt(), anyString())).thenReturn(true);
        assertTrue(carService.updateCar(numOfPass, carCategory));
    }
    @Test
    void updateCarException() throws DBException, SQLException {
        int numOfPass = 5;
        String carCategory = "Business";
        when(carDao.updateCar(any(Connection.class), anyInt(), anyString())).thenThrow(new SQLException("can't close some resources"));
        Throwable exception = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()-> carService.updateCar(numOfPass,carCategory));
        assertEquals("can't close some resources",exception.getMessage());
    }
    @Test
    void findAllCars() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        when(carDao.findAllCars(any(Connection.class))).thenReturn(new ArrayList<>());
        assertNotNull(carService.findAllCars());
    }
    @Test
    void findAllCarsException() throws DBException, SQLException {
        when(carDao.findAllCars(any(Connection.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable exception = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()-> carService.findAllCars());
        assertEquals("can't close some resources",exception.getMessage());
    }
    @Test
    void findNumberCarByCat() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        when(carDao.findNumberCarByCat(any(Connection.class))).thenReturn(new ArrayList<>());
        assertNotNull(carService.findNumberCarByCat());
    }
    @Test
    void findNumberCarByCatException() throws DBException, SQLException {
        when(carDao.findNumberCarByCat(any(Connection.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable exception = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()-> carService.findNumberCarByCat());
        assertEquals("can't close some resources",exception.getMessage());
    }
}