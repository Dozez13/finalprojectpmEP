package com.example.finalprojectpm.db.mysql;

import com.example.finalprojectpm.db.CarCategoryDao;
import com.example.finalprojectpm.db.entity.CarCategory;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import com.example.finalprojectpm.db.util.ConnectionUtil;
import com.example.finalprojectpm.db.util.ImageUtil;
import com.example.finalprojectpm.db.util.QueriesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLCarCategoryDao implements CarCategoryDao {
    private static final Logger LOGGER = LogManager.getLogger(MySQLCarCategoryDao.class);

    @Override
    public boolean insertCarCategory(Connection connection, CarCategory carCategory) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Insert CarCategory method Is started");
        int rowNum;
        Connection con;
        PreparedStatement statement = null;
        try{
            String query = QueriesUtil.getQuery("insertCarCategory");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setString(1, carCategory.getCarCategoryName());
            statement.setDouble(2, carCategory.getCostPerOneKilometer());
            statement.setDouble(3, carCategory.getDiscount());
            statement.setBytes(4, ImageUtil.imageToByte(carCategory.getCarCategoryImage()));
            rowNum = statement.executeUpdate();
            LOGGER.debug("CarCategory Is Inserted");
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug("Close all resources");
        }
        return rowNum > 0;
    }

    @Override
    public boolean deleteCarCategory(Connection connection, String carCategory) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Delete CarCategory method Is started");
        int rowNum;
        Connection con;
        PreparedStatement statement = null;
        try {
            String query = QueriesUtil.getQuery("deleteCarCategory");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setString(1,carCategory);
            rowNum = statement.executeUpdate();
            LOGGER.debug("CarCategory Is deleted");
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug("Close all resources");
        }
        return rowNum > 0;
    }

    @Override
    public CarCategory findCarCategory(Connection connection, String carCategory) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Find CarCategory method Is started");
        CarCategory foundCarCategory = null;
        Connection con;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = QueriesUtil.getQuery("findCarCategory");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setString(1, carCategory);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foundCarCategory = new CarCategory();
                foundCarCategory.setCarCategoryName(resultSet.getString("carCategory"));
                foundCarCategory.setCostPerOneKilometer(resultSet.getDouble("costPerOneKilometer"));
                foundCarCategory.setDiscount(resultSet.getDouble("discountPerPrice"));
                foundCarCategory.setCarCategoryImage(ImageUtil.byteToImage(resultSet.getBytes("carCategoryImage")));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);

        } finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug("Close all resources");
        }
        return foundCarCategory;
    }

    @Override
    public boolean updateCarCategory(Connection connection, String carCategory, double newPrice) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Update CarCategory method Is started");
        int rowNum;
        Connection con;
        PreparedStatement statement = null;
        try {
            String query = QueriesUtil.getQuery("updateCarCategory");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setDouble(1,newPrice);
            statement.setString(2,carCategory);
            rowNum = statement.executeUpdate();
            LOGGER.debug("CarCategory Is updated");
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug("Close all resources");
        }
        return rowNum>0;
    }

    @Override
    public List<CarCategory> findAllCarC(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Find All CarCategory method Is started");
        ResultSet resultSet = null;
        Connection con;
        PreparedStatement statement = null;
        List<CarCategory> carCategories = new ArrayList<>();
        try{
            String query = QueriesUtil.getQuery("findAllCarC");
            con = connection;
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                CarCategory carCategory = new CarCategory();
                carCategory.setCarCategoryName(resultSet.getString("carCategory"));
                carCategory.setCostPerOneKilometer(resultSet.getDouble("costPerOneKilometer"));
                carCategory.setDiscount(resultSet.getDouble("discountPerPrice"));
                carCategory.setCarCategoryImage(ImageUtil.byteToImage(resultSet.getBytes("carCategoryImage")));
                carCategories.add(carCategory);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        } finally {
            ConnectionUtil.closeResources(resultSet, statement, null);
            LOGGER.debug("Close all resources");
        }
        return carCategories;
    }
    @Override
    public List<CarCategory> findExistingCarC(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Find Existing CarCategory method Is started");
        ResultSet resultSet = null;
        Connection con;
        PreparedStatement statement = null;
        List<CarCategory> carCategories = new ArrayList<>();
        try {
            String query = QueriesUtil.getQuery("findExistingCarC");
            con = connection;
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                CarCategory carCategory = new CarCategory();
                carCategory.setCarCategoryName(resultSet.getString("carCategory"));
                carCategory.setCostPerOneKilometer(resultSet.getDouble("costPerOneKilometer"));
                carCategory.setDiscount(resultSet.getDouble("discountPerPrice"));
                carCategory.setCarCategoryImage(ImageUtil.byteToImage(resultSet.getBytes("carCategoryImage")));
                carCategories.add(carCategory);
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug("Close all resources");
        }
        return carCategories;
    }
}