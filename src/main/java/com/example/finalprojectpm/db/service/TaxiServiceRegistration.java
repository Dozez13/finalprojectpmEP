package com.example.finalprojectpm.db.service;

import com.example.finalprojectpm.db.AutoRollback;
import com.example.finalprojectpm.db.ProfileDao;
import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.entity.Profile;
import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TaxiServiceRegistration {
    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceRegistration.class);
    private UserDao userDao;
    private ProfileDao profileDao;
    public TaxiServiceRegistration(UserDao userDao,ProfileDao profileDao){
        this.userDao = userDao;
        this.profileDao = profileDao;
    }
    public void doRegistration(String firstName,String surName,String login , String email,String psw) throws ApplicationEXContainer.ApplicationCanNotChangeException, ApplicationEXContainer.ApplicationCanChangeException {

        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            User user = new User();
            user.setLogin(login);
            user.setUserEmail(email);
            user.setPassword(psw);
            user.setUserType("client");
            userDao.insertUser(connection,user);
            Profile profile = new Profile();
            profile.setUserRegistrationDate(LocalDateTime.now());
            profile.setUserId(user.getUserId());
            profile.setUserSurName(surName);
            profile.setUserFirstName(firstName);
            profileDao.insertProfile(connection,profile);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }catch (MySQLEXContainer.MySQLDBLargeDataException | MySQLEXContainer.MySQLDBNotUniqueException mySQLDBExceptionCanChange) {
            LOGGER.error(mySQLDBExceptionCanChange.getMessage());
            throw new ApplicationEXContainer.ApplicationCanChangeException(mySQLDBExceptionCanChange.getMessage(),mySQLDBExceptionCanChange);
        }


    }
}
