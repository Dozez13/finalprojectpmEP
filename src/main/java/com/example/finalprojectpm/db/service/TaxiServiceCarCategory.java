package com.example.finalprojectpm.db.service;


import com.example.finalprojectpm.db.AutoRollback;
import com.example.finalprojectpm.db.CarCategoryDao;
import com.example.finalprojectpm.db.entity.CarCategory;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TaxiServiceCarCategory {
    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceCarCategory.class);
    private final CarCategoryDao categoryDao;
    public TaxiServiceCarCategory(CarCategoryDao carCategoryDao){
        this.categoryDao = carCategoryDao;
    }
    public boolean insertCarCategory(CarCategory carCategory) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = categoryDao.insertCarCategory(connection,carCategory);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }
    public boolean deleteCarCategory(String carCategory) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result= categoryDao.deleteCarCategory(connection,carCategory);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }
    public CarCategory findCarCategory(String carCategory) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        CarCategory foundCarCategory;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            foundCarCategory = categoryDao.findCarCategory(connection,carCategory);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return foundCarCategory;
    }
    public boolean updateCarCategory(String carCategory, double newPrice) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result= categoryDao.updateCarCategory(connection,carCategory,newPrice);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }

    public List<CarCategory> findAllCarC() throws ApplicationEXContainer.ApplicationCanNotChangeException {
        List<CarCategory> carCategoryList;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            carCategoryList= categoryDao.findAllCarC(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return carCategoryList;
    }

    public List<CarCategory> findExistingCarC() throws ApplicationEXContainer.ApplicationCanNotChangeException {
        List<CarCategory> existingCarC;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            existingCarC = categoryDao.findExistingCarC(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return existingCarC;
    }

}
