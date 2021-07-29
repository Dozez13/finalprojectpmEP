package com.example.finalprojectpm.db.service;


import com.example.finalprojectpm.db.AutoRollback;
import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.entity.User;
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
public class TaxiServiceUser {
    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceUser.class);
    private final UserDao userDao;

    /**
     * Sets Dao
     * @param userDao object which will manipulate on Users entities
     */
    public TaxiServiceUser(UserDao userDao){
        this.userDao = userDao;
    }

    /**
     * Inserts User into database table
     * @param user entity that should be inserted
     * @return true if insert operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if dao methods throw DBException
     * @throws ApplicationEXContainer.ApplicationSendRegistrationMessageException if dao methods throw exception with 1062,1406 error code
     */
    public boolean insertUser(User user) throws ApplicationEXContainer.ApplicationCantRecoverException, ApplicationEXContainer.ApplicationSendRegistrationMessageException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result =  userDao.insertUser(connection,user);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        } catch (DBException mySQLDBExceptionCanChange) {
            LOGGER.error(mySQLDBExceptionCanChange.getMessage());
            throw new ApplicationEXContainer.ApplicationSendRegistrationMessageException(mySQLDBExceptionCanChange.getMessage(),mySQLDBExceptionCanChange);
        }
        return result;
    }


    /**
     * Deletes User into database table
     * @param login property by which entity should be deleted
     * @return true if delete operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public boolean deleteUser(String login) throws ApplicationEXContainer.ApplicationCantRecoverException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = userDao.deleteUser(connection,login);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }

        return result;
    }


    /**
     * Validates user from database table
     * @param login property by which entity should be found
     * @param password property by which entity should be validated
     * @return true if validate operation user with these login and password exists
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public boolean validateUser(String login, String password) throws ApplicationEXContainer.ApplicationCantRecoverException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = userDao.validateUser(connection,login,password);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }
        return result;
    }


    /**
     * Finds User entity by login in database table
     * @param login property by which entity should be found
     * @return User object if it exists in table and otherwise null
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public User findUser(String login) throws ApplicationEXContainer.ApplicationCantRecoverException {
        User user ;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            user = userDao.findUser(connection,login);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }

        return user;
    }


    /**
     * Updates found user by login setting new password to it
     * @param login property by which entity should be found
     * @param newPassword property that should be updated
     * @return true if update operation user with these login and password exists
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public boolean updateUser(String login, String newPassword) throws ApplicationEXContainer.ApplicationCantRecoverException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = userDao.updateUser(connection,login,newPassword);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }
        return result;
    }


    /**
     * Returns All users
     * @return list of all users
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public List<User> findAllUser() throws ApplicationEXContainer.ApplicationCantRecoverException {
        List<User> users;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            users = userDao.findAllUser(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }
        return users;
    }
}
