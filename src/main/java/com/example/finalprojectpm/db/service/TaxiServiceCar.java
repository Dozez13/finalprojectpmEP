package com.example.finalprojectpm.db.service;


import com.example.finalprojectpm.db.AutoRollback;
import com.example.finalprojectpm.db.CarDao;
import com.example.finalprojectpm.db.entity.Car;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TaxiServiceCar {
    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceCar.class);
    private final CarDao carDao;
    public TaxiServiceCar(CarDao carDao){
        this.carDao = carDao;
    }
    public boolean insertCar(Car car) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result =  carDao.insertCar(connection,car);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }

        return result;
    }


    public boolean deleteCar(int carId) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result ;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result =  carDao.deleteCar(connection,carId);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }


    public Car findCar(int carId) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        Car car;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            car = carDao.findCar(connection,carId);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return car;
    }


    public Car findCar(int numOfPas, String carCategory) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        Car car ;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            car = carDao.findCar(connection,numOfPas,carCategory);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return car;
    }


    public boolean updateCar(int carId, String newCarCategory) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result ;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = carDao.updateCar(connection,carId,newCarCategory);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }


    public List<Car> findAllCars() throws ApplicationEXContainer.ApplicationCanNotChangeException {
        List<Car>carList;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            carList= carDao.findAllCars(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return carList;
    }


    public List<Integer> findNumberCarByCat() throws ApplicationEXContainer.ApplicationCanNotChangeException {
        List<Integer> categoryNumbers;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            categoryNumbers = carDao.findNumberCarByCat(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return categoryNumbers;
    }
}
