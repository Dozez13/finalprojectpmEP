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

/**
 * Service layer for Profile entity
 */
public class TaxiServiceProfile {
    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceProfile.class);
    private final ProfileDao profileDao;

    /**
     * Sets dao
     * @param profileDao object which will be used
     */
    public TaxiServiceProfile(ProfileDao profileDao){
        this.profileDao = profileDao;
    }

    /**
     * Inserts profile into table
     * @param profile that should be inserted
     * @return true if insert operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
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

    /**
     * Deletes profile from table
     * @param profile that should be updated
     * @return true if update operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
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

    /**
     * Updates profile balance adding money to account
     * @param userId userId property by which Profile will be found
     * @param accountBalance money to add
     * @return true if update operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
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

    /**
     * Updates profile balance withdrawing money from account
     * @param userId userId property by which Profile will be found
     * @param accountBalance money to withdraw
     * @return true if update operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     * @throws ApplicationEXContainer.ApplicationCanChangeException if Amount less then that withdraws
     */
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

    /**
     * Finds Profile by profile id in database table
     * @param profileId property by which Profile will be found
     * @return Profile if it exists in table and otherwise null
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if Amount less then that withdraws
     */
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

    /**
     * Returns All profiles as a list
     * @return list of all profiles
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if Amount less then that withdraws
     */
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
