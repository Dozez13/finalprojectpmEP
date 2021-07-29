package com.example.finalprojectpm.db.mysql;

import com.example.finalprojectpm.db.CarDao;
import com.example.finalprojectpm.db.Fields;
import com.example.finalprojectpm.db.entity.Car;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import com.example.finalprojectpm.db.util.ConnectionUtil;
import com.example.finalprojectpm.db.util.ImageUtil;
import com.example.finalprojectpm.db.util.QueriesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for Car related entities
 */
@Repository
public class MySQLCarDao implements CarDao {
    private static final Logger LOGGER = LogManager.getLogger(MySQLCarDao.class);

    /**
     * Inserts Car entity into database table
     * @param connection object with database
     * @param car entity to be inserted
     * @return true if insert operation went without exception and false otherwise
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public boolean insertCar(Connection connection, Car car) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Insert Car method Is started");
        int rowNum ;
        Connection con;
        PreparedStatement statement = null;
        try {
            String query = QueriesUtil.getQuery("insertCar");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setInt(1,car.getCarId());
            statement.setString(2,car.getCarCategory());
            statement.setInt(3,car.getNumOfPas());
            statement.setString(4,car.getCarState());
            statement.setString(5,car.getCarName());
            statement.setBytes(6, ImageUtil.imageToByte(car.getCarImage()));
            rowNum = statement.executeUpdate();
            LOGGER.debug("Car Is inserted");
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return rowNum>0;
    }

    /**
     * Deletes Car entity from database table by using carId
     * @param connection object with database
     * @param carId Car entity id
     * @return true if delete operation went without exception and false otherwise
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public boolean deleteCar(Connection connection,int carId) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Delete Car method Is started");
        int rowNum;
        Connection con;
        PreparedStatement statement = null;
        try{
            String query = QueriesUtil.getQuery("deleteCar");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setInt(1,carId);
            rowNum = statement.executeUpdate();
            LOGGER.debug("Car Is deleted");
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return rowNum > 0;
    }

    /**
     * Finds Car entity from database table by using numOfPas and Category properties
     * @param connection object with database
     * @param numOfPas Car's property number of passenger by which it will be found
     * @param carCategory Car's property category by which it will be found
     * @return Car object If it was found in table and null otherwise
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public Car findCar(Connection connection,int numOfPas,String carCategory) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Find Car method Is started");
        Car foundCar = null;
        ResultSet resultSet = null;
        Connection con;
        PreparedStatement statement = null;
        try {
            String query = QueriesUtil.getQuery("findCar");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setInt(1,numOfPas);
            statement.setString(2,carCategory);
            resultSet =statement.executeQuery();
            if(resultSet.next()){
                foundCar = new Car();
                foundCar.setCarId(resultSet.getInt(Fields.CAR_CAR_ID));
                foundCar.setCarCategory(resultSet.getString(Fields.CAR_CAR_CATEGORY));
                foundCar.setNumOfPas(resultSet.getInt(Fields.CAR_CAR_PAS));
                foundCar.setCarState(resultSet.getString(Fields.CAR_CAR_STATE));
                foundCar.setCarName(resultSet.getString(Fields.CAR_CAR_NAME));
                foundCar.setCarImage(ImageUtil.byteToImage(resultSet.getBytes(Fields.CAR_CAR_IMAGE)));
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return foundCar;
    }

    /**
     * Returns Car entity from database table by using carId
     * @param connection object with database
     * @param carId Car's property by which it will be found
     * @return Car object if it's found and null otherwise
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public Car findCar(Connection connection,int carId) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Find Car method by carId Is started");
        Car foundCar = null;
        ResultSet resultSet = null;
        Connection con;
        PreparedStatement statement = null;
        try{
            String query = QueriesUtil.getQuery("findCarById");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setInt(1,carId);
            resultSet =statement.executeQuery();
            if(resultSet.next()){
                foundCar = new Car();
                foundCar.setCarId(resultSet.getInt(Fields.CAR_CAR_ID));
                foundCar.setCarCategory(resultSet.getString(Fields.CAR_CAR_CATEGORY));
                foundCar.setNumOfPas(resultSet.getInt(Fields.CAR_CAR_PAS));
                foundCar.setCarState(resultSet.getString(Fields.CAR_CAR_STATE));
                foundCar.setCarName(resultSet.getString(Fields.CAR_CAR_NAME));
                foundCar.setCarImage(ImageUtil.byteToImage(resultSet.getBytes(Fields.CAR_CAR_IMAGE)));
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return foundCar;
    }

    /**
     * Updates Car entity by setting new CarState
     * @param connection object with database
     * @param carId Car's property by which it will be found
     * @param newCarState Car's property which should be updated
     * @return true if update operation went without exception and false otherwise
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public boolean updateCar(Connection connection,int carId,String newCarState) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Update Car method Is started");
        int rowNum ;
        Connection con;
        PreparedStatement statement = null;
        try{
            String query = QueriesUtil.getQuery("updateCar");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setString(1,newCarState);
            statement.setInt(2,carId);
            rowNum = statement.executeUpdate();
            LOGGER.debug("Car Is updated");
        } catch (SQLException e) {
            LOGGER.error(e);

            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {

            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);

        }
        return rowNum>0;
    }

    /**
     * Returns List of All Cars
     * @param connection object with database
     * @return List of All Cars
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public List<Car> findAllCars(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Find All Cars method Is started");
        ResultSet resultSet = null;
        Connection con;
        PreparedStatement statement = null;
        List<Car> cars = new ArrayList<>();
        try {
            String query = QueriesUtil.getQuery("findAllCars");
            con = connection;
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                Car car = new Car();
                car.setCarId(resultSet.getInt(Fields.CAR_CAR_ID));
                car.setCarCategory(resultSet.getString(Fields.CAR_CAR_CATEGORY));
                car.setCarName(resultSet.getString(Fields.CAR_CAR_NAME));
                car.setCarState(resultSet.getString(Fields.CAR_CAR_STATE));
                car.setNumOfPas(resultSet.getInt(Fields.CAR_CAR_PAS));
                car.setCarImage(ImageUtil.byteToImage(resultSet.getBytes(Fields.CAR_CAR_IMAGE)));
                cars.add(car);
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return cars;
    }

    /**
     * Returns Number Car Grouped by CarCategory as List of integers
     * @param connection object with database
     * @return Number Car Grouped by CarCategory as List of integers
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public List<Integer> findNumberCarByCat(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Find number Car By CarCategory method Is started");
        ResultSet resultSet = null;
        Connection con;
        PreparedStatement statement = null;
        List<Integer> counts = new ArrayList<>();
        try {
            String query = QueriesUtil.getQuery("findNumberCarByCat");
            con = connection;
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                counts.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return counts;
    }

}
