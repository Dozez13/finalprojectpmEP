package com.example.finalprojectpm.db.service;


import com.example.finalprojectpm.db.AutoRollback;
import com.example.finalprojectpm.db.CarCategoryDao;
import com.example.finalprojectpm.db.entity.CarCategory;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.DBException;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Service layer for CarCategory entity
 */
@Service
public class TaxiServiceCarCategory {
    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceCarCategory.class);

    private final CarCategoryDao mySQLCarCategoryDao;

    /**
     * Sets dao
     * @param carCategoryDao object which will be used
     */
    @Autowired
    public TaxiServiceCarCategory(CarCategoryDao carCategoryDao){
        this.mySQLCarCategoryDao = carCategoryDao;
    }

    /**
     * Inserts CarCategory into table
     * @param carCategory that object should be inserted into table
     * @return true if insert operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public boolean insertCarCategory(CarCategory carCategory) throws ApplicationEXContainer.ApplicationCantRecoverException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = mySQLCarCategoryDao.insertCarCategory(connection,carCategory);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }
        return result;
    }

    /**
     * Deletes CarCategory from table
     * @param carCategory property by which entity will be found and deleted
     * @return true if delete operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public boolean deleteCarCategory(String carCategory) throws ApplicationEXContainer.ApplicationCantRecoverException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result= mySQLCarCategoryDao.deleteCarCategory(connection,carCategory);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }
        return result;
    }

    /**
     * Finds CarCategory in table
     * @param carCategory property by which entity will be found and deleted
     * @return CarCategory object if it exists in table and otherwise null
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public CarCategory findCarCategory(String carCategory) throws ApplicationEXContainer.ApplicationCantRecoverException {
        CarCategory foundCarCategory;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            foundCarCategory = mySQLCarCategoryDao.findCarCategory(connection,carCategory);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }
        return foundCarCategory;
    }

    /**
     * Update CarCategory by setting to it new price
     * @param carCategory property by which entity will be found
     * @param newPrice property that will be changed
     * @return true if update operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public boolean updateCarCategory(String carCategory, double newPrice) throws ApplicationEXContainer.ApplicationCantRecoverException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result= mySQLCarCategoryDao.updateCarCategory(connection,carCategory,newPrice);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }
        return result;
    }

    /**
     * Returns all categories
     * @return List of all CarCategories
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public List<CarCategory> findAllCarC() throws ApplicationEXContainer.ApplicationCantRecoverException {
        List<CarCategory> carCategoryList;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            carCategoryList= mySQLCarCategoryDao.findAllCarC(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }
        return carCategoryList;
    }

    /**
     * Returns existing categories
     * @return List of existing Categories
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public List<CarCategory> findExistingCarC() throws ApplicationEXContainer.ApplicationCantRecoverException {
        List<CarCategory> existingCarC;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            existingCarC = mySQLCarCategoryDao.findExistingCarC(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }
        return existingCarC;
    }

}
