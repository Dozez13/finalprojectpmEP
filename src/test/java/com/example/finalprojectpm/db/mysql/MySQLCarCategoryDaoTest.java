package com.example.finalprojectpm.db.mysql;

import com.example.finalprojectpm.db.CarCategoryDao;
import com.example.finalprojectpm.db.entity.CarCategory;
import com.example.finalprojectpm.db.exception.DBException;
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

class MySQLCarCategoryDaoTest {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private CarCategoryDao carCategoryDao;
    private ResultSet resultSet;
    private BufferedImage bufferedImage;
    @BeforeEach
    void init() throws SQLException {
        bufferedImage = new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
        carCategoryDao = new MySQLCarCategoryDao();
        preparedStatement = mock(PreparedStatement.class);
        connection = mock(Connection.class);
        resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }
    @Test
    void insertCarCategory() throws SQLException, DBException {
        CarCategory carCategory = new CarCategory();
        carCategory.setCarCategoryName("Business");
        carCategory.setDiscount(15.0);
        carCategory.setCostPerOneKilometer(25.0);
        carCategory.setCarCategoryImage(bufferedImage);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean result =carCategoryDao.insertCarCategory(connection,carCategory);
        assertTrue(result);
    }

    @Test
    void deleteCarCategory() throws SQLException, DBException {
        String carCategory = "Business";
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean result =carCategoryDao.deleteCarCategory(connection,carCategory);
        assertTrue(result);
    }

    @Test
    void findCarCategory() throws SQLException, DBException, IOException {
        String carCategory = "Business";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("carCategory")).thenReturn(carCategory);
        when(resultSet.getDouble(anyString())).thenReturn(15.0);
        when(resultSet.getBytes("carCategoryImage")).thenReturn(bytes);
        CarCategory result =carCategoryDao.findCarCategory(connection,carCategory);
        assertNotNull(result);
    }
    @Test
    void findCarCategoryWhenNull() throws SQLException, DBException {
        String carCategory = "Business";
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        CarCategory result =carCategoryDao.findCarCategory(connection,carCategory);
        assertNull(result);
    }

    @Test
    void updateCarCategory() throws SQLException, DBException {
        String carCategory = "Business";
        double newPrice = 15.0;
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean result =carCategoryDao.updateCarCategory(connection,carCategory,newPrice);
        assertTrue(result);
    }

    @Test
    void findAllCarC() throws SQLException, IOException, DBException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("carCategory")).thenReturn("Business");
        when(resultSet.getDouble(anyString())).thenReturn(15.0);
        when(resultSet.getBytes("carCategoryImage")).thenReturn(bytes);
        List<CarCategory> carCategoryList = carCategoryDao.findAllCarC(connection);
        assertNotNull(carCategoryList);
    }

    @Test
    void findExistingCarC() throws IOException, SQLException, DBException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("carCategory")).thenReturn("Business");
        when(resultSet.getDouble(anyString())).thenReturn(15.0);
        when(resultSet.getBytes("carCategoryImage")).thenReturn(bytes);
        List<CarCategory> carCategoryList = carCategoryDao.findExistingCarC(connection);
        assertNotNull(carCategoryList);
    }
}