package com.example.finalprojectpm.db.mysql;

import com.example.finalprojectpm.db.CarDao;
import com.example.finalprojectpm.db.entity.Car;
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

public class MySQLCarDao implements CarDao {
    private static final Logger LOGGER = LogManager.getLogger(MySQLCarDao.class);

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
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug("Close all resources");
        }
        return rowNum>0;
    }

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
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug("Close all resources");
        }
        return rowNum > 0;
    }
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
                foundCar.setCarId(resultSet.getInt("carId"));
                foundCar.setCarCategory(resultSet.getString("carCategory"));
                foundCar.setNumOfPas(resultSet.getInt("numOfPas"));
                foundCar.setCarState(resultSet.getString("carState"));
                foundCar.setCarName(resultSet.getString("carName"));
                foundCar.setCarImage(ImageUtil.byteToImage(resultSet.getBytes("carImage")));
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug("Close all resources");
        }
        return foundCar;
    }
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
                foundCar.setCarId(resultSet.getInt("carId"));
                foundCar.setCarCategory(resultSet.getString("carCategory"));
                foundCar.setNumOfPas(resultSet.getInt("numOfPas"));
                foundCar.setCarState(resultSet.getString("carState"));
                foundCar.setCarName(resultSet.getString("carName"));
                foundCar.setCarImage(ImageUtil.byteToImage(resultSet.getBytes("carImage")));
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug("Close all resources");
        }
        return foundCar;
    }

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

            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {

            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug("Close all resources");

        }
        return rowNum>0;
    }

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
                car.setCarId(resultSet.getInt("carId"));
                car.setCarCategory(resultSet.getString("carCategory"));
                car.setCarName(resultSet.getString("carName"));
                car.setCarState(resultSet.getString("carState"));
                car.setNumOfPas(resultSet.getInt("numOfPas"));
                car.setCarImage(ImageUtil.byteToImage(resultSet.getBytes("carImage")));
                cars.add(car);
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug("Close all resources");
        }
        return cars;
    }
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
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug("Close all resources");
        }
        return counts;
    }

}
