package com.example.finalprojectpm.db.service;


import com.example.finalprojectpm.db.AutoRollback;
import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TaxiServiceUser {
    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceUser.class);
    private final UserDao userDao;
    public TaxiServiceUser(UserDao userDao){
        this.userDao = userDao;
    }
    public boolean insertUser(User user) throws ApplicationEXContainer.ApplicationCanNotChangeException, ApplicationEXContainer.ApplicationCanChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result =  userDao.insertUser(connection,user);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        } catch (MySQLEXContainer.MySQLDBLargeDataException | MySQLEXContainer.MySQLDBNotUniqueException mySQLDBExceptionCanChange) {
            LOGGER.error(mySQLDBExceptionCanChange.getMessage());
            throw new ApplicationEXContainer.ApplicationCanChangeException(mySQLDBExceptionCanChange.getMessage(),mySQLDBExceptionCanChange);
        }
        return result;
    }


    public boolean deleteUser(String login) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = userDao.deleteUser(connection,login);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }

        return result;
    }


    public boolean validateUser(String login, String password) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = userDao.validateUser(connection,login,password);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }


    public User findUser(String login) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        User user ;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            user = userDao.findUser(connection,login);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }

        return user;
    }


    public boolean updateUser(String login, String newPassword) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = userDao.updateUser(connection,login,newPassword);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }


    public List<User> findAllUser() throws ApplicationEXContainer.ApplicationCanNotChangeException {
        List<User> users;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            users = userDao.findAllUser(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return users;
    }
}
