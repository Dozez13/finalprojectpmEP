package com.example.finalprojectpm.db.service;

import com.example.finalprojectpm.db.AutoRollback;
import com.example.finalprojectpm.db.ProfileDao;
import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.entity.Profile;
import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.DBException;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Service layer for registration
 */
@Service
public class TaxiServiceRegistration {
    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceRegistration.class);

    private UserDao mySQLUserDao;
    private ProfileDao mySQLProfileDao;

    /**
     * Sets dao
     * @param mySQLUserDao object which will be used to manipulate User
     * @param mySQLProfileDao object which will be used to manipulate Profile
     */
    @Autowired
    public TaxiServiceRegistration(UserDao mySQLUserDao, ProfileDao mySQLProfileDao){
        this.mySQLUserDao = mySQLUserDao;
        this.mySQLProfileDao = mySQLProfileDao;
    }

    /**
     * Insert profile and user into tables
     * @param firstName first name of new account
     * @param surName surname of new account
     * @param login login of new account
     * @param email email of new account
     * @param psw password of new account
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if dao methods throw exception
     * @throws ApplicationEXContainer.ApplicationSendRegistrationMessageException if dao methods throw exception with 1062,1406 error code
     */
    public void doRegistration(String firstName,String surName,String login , String email,String psw) throws ApplicationEXContainer.ApplicationCantRecoverException, ApplicationEXContainer.ApplicationSendRegistrationMessageException {

        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            User user = new User();
            user.setLogin(login);
            user.setUserEmail(email);
            user.setPassword(psw);
            user.setUserType("client");
            mySQLUserDao.insertUser(connection,user);
            Profile profile = new Profile();
            profile.setUserRegistrationDate(LocalDateTime.now());
            profile.setUserId(user.getUserId());
            profile.setUserSurName(surName);
            profile.setUserFirstName(firstName);
            mySQLProfileDao.insertProfile(connection,profile);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }catch (DBException mySQLDBExceptionCanChange) {
            LOGGER.error(mySQLDBExceptionCanChange.getMessage());
            throw new ApplicationEXContainer.ApplicationSendRegistrationMessageException(mySQLDBExceptionCanChange.getMessage(),mySQLDBExceptionCanChange);
        }


    }
}
