package com.example.finalprojectpm.db.service;

import com.example.finalprojectpm.db.AutoRollback;
import com.example.finalprojectpm.db.ProfileDao;

import com.example.finalprojectpm.db.entity.Profile;
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

public class TaxiServiceProfile {
    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceProfile.class);
    private final ProfileDao profileDao;
    public TaxiServiceProfile(ProfileDao profileDao){
        this.profileDao = profileDao;
    }
    public boolean insertProfile(Profile profile) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result =  profileDao.insertProfile(connection,profile);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }

        return result;
    }
    public boolean deleteProfile(Profile profile) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result =  profileDao.deleteProfile(connection,profile);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }
    public boolean updateProfileAddBalance(int userId, double accountBalance) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result =  profileDao.updateProfileAddBalance(connection,userId,accountBalance);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }

        return result;
    }
    public boolean updateProfileSWithdrawBalance(int userId, double accountBalance) throws ApplicationEXContainer.ApplicationCanNotChangeException, ApplicationEXContainer.ApplicationCanChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result =  profileDao.updateProfileSWithdrawBalance(connection,userId,accountBalance);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        } catch (DBException e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationEXContainer.ApplicationCanChangeException(e.getMessage(),e);
        }

        return result;
    }
    public Profile findProfile(int profileId) throws ApplicationEXContainer.ApplicationCanNotChangeException {
       Profile profile;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            profile =  profileDao.findProfile(connection,profileId);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return profile;
    }
    public List<Profile> findAllProfiles() throws ApplicationEXContainer.ApplicationCanNotChangeException {
       List<Profile> profiles;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            profiles =  profileDao.findAllProfiles(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return profiles;
    }


}
