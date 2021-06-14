package com.example.finalprojectpm.db.service;


import com.example.finalprojectpm.db.AutoRollback;
import com.example.finalprojectpm.db.CarDao;
import com.example.finalprojectpm.db.entity.Car;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.DBException;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Service layer for Car entity
 */
public class TaxiServiceCar {
    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceCar.class);
    private final CarDao carDao;

    /**
     * Sets dao
     * @param carDao object which will be used
     */
    public TaxiServiceCar(CarDao carDao){
        this.carDao = carDao;
    }

    /**
     * Inserts car into table
     * @param car that object should be inserted into table
     * @return true if insert operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
    public boolean insertCar(Car car) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result =  carDao.insertCar(connection,car);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }

        return result;
    }


    /**
     * Deletes car from table
     * @param carId property by which car will be deleted
     * @return true if delete operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
    public boolean deleteCar(int carId) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result ;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result =  carDao.deleteCar(connection,carId);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }


    /**
     * Finds car in table
     * @param carId property by which car will be found
     * @return Car if it exists in table and otherwise return null
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
    public Car findCar(int carId) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        Car car;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            car = carDao.findCar(connection,carId);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return car;
    }


    /**
     * Finds car in table
     * @param numOfPas property by which car will be found
     * @param carCategory property by which car will be found
     * @return Car if it exists in table and otherwise return null
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
    public Car findCar(int numOfPas, String carCategory) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        Car car ;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            car = carDao.findCar(connection,numOfPas,carCategory);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return car;
    }


    /**
     * Updates car in table
     * @param carId property by which car will be found
     * @param newCarCategory property that will be changed
     * @return true if update operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
    public boolean updateCar(int carId, String newCarCategory) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result ;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = carDao.updateCar(connection,carId,newCarCategory);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }


    /**
     * Returns All cars
     * @return List of all cars
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
    public List<Car> findAllCars() throws ApplicationEXContainer.ApplicationCanNotChangeException {
        List<Car>carList;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            carList= carDao.findAllCars(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return carList;
    }


    /**
     * Returns categories' number
     * @return List of number categories by car
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException
     */
    public List<Integer> findNumberCarByCat() throws ApplicationEXContainer.ApplicationCanNotChangeException {
        List<Integer> categoryNumbers;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            categoryNumbers = carDao.findNumberCarByCat(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return categoryNumbers;
    }
}
