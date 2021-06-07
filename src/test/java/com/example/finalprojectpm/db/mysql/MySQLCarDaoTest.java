package com.example.finalprojectpm.db.mysql;

import com.example.finalprojectpm.db.CarDao;
import com.example.finalprojectpm.db.entity.Car;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MySQLCarDaoTest {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private CarDao carDao;
    private ResultSet resultSet;
    private BufferedImage bufferedImage;
    @BeforeEach
    void init() throws SQLException {
        bufferedImage = new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
        carDao = new MySQLCarDao();
        preparedStatement = mock(PreparedStatement.class);
        connection = mock(Connection.class);
        resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }
    @Test
    void insertCarTrue() throws SQLException, MySQLEXContainer.MySQLDBExecutionException {
        Car car = new Car();
        car.setCarState("ready");
        car.setCarId(1);
        car.setNumOfPas(5);
        car.setCarName("name");
        car.setCarCategory("Business");
        car.setCarImage(bufferedImage);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean result = carDao.insertCar(connection,car);
        assertTrue(result);
    }

    @Test
    void deleteCar() throws SQLException, MySQLEXContainer.MySQLDBExecutionException {
        int carId =15;
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean result = carDao.deleteCar(connection,carId);
        assertTrue(result);
    }

    @Test
    void findCar() throws IOException, SQLException, MySQLEXContainer.MySQLDBExecutionException {
        int numOfPas = 5;
        String carCategory = "Business";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("carId")).thenReturn(1);
        when(resultSet.getString("carName")).thenReturn("BMW");
        when(resultSet.getString("carState")).thenReturn("ready");
        when(resultSet.getString("carCategory")).thenReturn(carCategory);
        when(resultSet.getInt("numOfPas")).thenReturn(5);
        when(resultSet.getBytes("carImage")).thenReturn(bytes);
        Car car = carDao.findCar(connection,numOfPas,carCategory);
        assertNotNull(car);
    }

    @Test
    void testFindCar() throws SQLException, IOException, MySQLEXContainer.MySQLDBExecutionException {
        int carId = 5;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("carId")).thenReturn(1);
        when(resultSet.getString("carName")).thenReturn("BMW");
        when(resultSet.getString("carState")).thenReturn("ready");
        when(resultSet.getString("carCategory")).thenReturn("Business");
        when(resultSet.getInt("numOfPas")).thenReturn(5);
        when(resultSet.getBytes("carImage")).thenReturn(bytes);
        Car car = carDao.findCar(connection,carId);
        assertNotNull(car);
    }

    @Test
    void updateCar() throws SQLException, MySQLEXContainer.MySQLDBExecutionException {
        int carId = 5;
        String newCarState = "on Order";
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean result = carDao.updateCar(connection,carId,newCarState);
        assertTrue(result);
    }

    @Test
    void findAllCars() throws MySQLEXContainer.MySQLDBExecutionException, SQLException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("carId")).thenReturn(1);
        when(resultSet.getString("carName")).thenReturn("BMW");
        when(resultSet.getString("carState")).thenReturn("ready");
        when(resultSet.getString("carCategory")).thenReturn("Business");
        when(resultSet.getInt("numOfPas")).thenReturn(5);
        when(resultSet.getBytes("carImage")).thenReturn(bytes);
        List<Car> cars = carDao.findAllCars(connection);
        assertNotNull(cars);
    }

    @Test
    void findNumberCarByCat() throws SQLException, MySQLEXContainer.MySQLDBExecutionException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(1)).thenReturn(1);
        List<Integer> cars = carDao.findNumberCarByCat(connection);
        assertNotNull(cars);
    }
}