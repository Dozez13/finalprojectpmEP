package com.example.finalprojectpm.db.mysql;

import com.example.finalprojectpm.db.Fields;
import com.example.finalprojectpm.db.ProfileDao;
import com.example.finalprojectpm.db.entity.Profile;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import com.example.finalprojectpm.db.util.ConnectionUtil;
import com.example.finalprojectpm.db.util.QueriesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Data access object for Profile related entities
 */
public class MySQLProfileDao implements ProfileDao {
    private static final Logger LOGGER = LogManager.getLogger(MySQLProfileDao.class);

    /**
     * Inserts Profile entity into database table
     * @param connection object with database
     * @param profile entity that should be inserted into table
     * @return true if insert operation went without exception and false otherwise
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public boolean insertProfile(Connection connection,Profile profile) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        int rowNum ;
        ResultSet keys = null;
        Connection con;
        PreparedStatement statement = null;
        try{
            String query = QueriesUtil.getQuery("insertProfile");
            con = connection;
            statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,profile.getUserId());
            statement.setTimestamp(2, Timestamp.valueOf(profile.getUserRegistrationDate()));
            statement.setString(3,profile.getUserFirstName());
            statement.setString(4,profile.getUserSurName());
            statement.setDouble(5,profile.getAccountBalance());
            rowNum = statement.executeUpdate();
            keys = statement.getGeneratedKeys();
            if(keys.next()){
                profile.setProfileId(keys.getInt(1));
            }
        }catch (SQLException e){
          LOGGER.error(e.getMessage());
          throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(keys,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return rowNum>0;
    }

    /**
     * Deletes Profile entity from database table
     * @param connection object with database
     * @param profile entity that should be delete from table
     * @return true if delete operation went without exception and false otherwise
     * @throws SQLException if resources can't be closed
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     */
    @Override
    public boolean deleteProfile(Connection connection,Profile profile) throws SQLException, MySQLEXContainer.MySQLDBExecutionException {
        int rowNum ;
        Connection con;
        PreparedStatement statement = null;
        try{
            String query = QueriesUtil.getQuery("deleteProfile");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setInt(1,profile.getProfileId());
            rowNum = statement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return rowNum>0;
    }

    /**
     * Updates profile balance adding money to account
     * @param connection object with database
     * @param userId userId property by which Profile will be found
     * @param accountBalance money to add
     * @return true if update operation went without exception and false otherwise
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public boolean updateProfileAddBalance(Connection connection, int userId, double accountBalance) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        int rowNum ;
        Connection con;
        PreparedStatement statement = null;
        try{
            String query = QueriesUtil.getQuery("updateProfileAddBalance");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setDouble(1,accountBalance);
            statement.setInt(2,userId);
            rowNum = statement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return rowNum>0;
    }
    /**
     * Updates profile balance withdrawing money from account
     * @param connection object with database
     * @param userId userId property by which Profile will be found
     * @param accountBalance money to withdraw
     * @return true if update operation went without exception and false otherwise
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public boolean updateProfileSWithdrawBalance(Connection connection, int userId, double accountBalance) throws MySQLEXContainer.MySQLDBExecutionException, SQLException, MySQLEXContainer.MySQLDBAccountAmountLessThenNul {
        int rowNum ;
        Connection con;
        PreparedStatement statement = null;
        try{
            String query = QueriesUtil.getQuery("updateProfileSWithdrawBalance");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setDouble(1,accountBalance);
            statement.setInt(2,userId);
            rowNum = statement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
            if(e.getErrorCode()==3819){
                throw new MySQLEXContainer.MySQLDBAccountAmountLessThenNul("Account doesnt have money for order",e);
            }
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return rowNum>0;
    }

    /**
     * Finds Profile by profile id in database table
     * @param connection object with database
     * @param profileId property by which Profile will be found
     * @return Profile if it exists in table and otherwise null
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public Profile findProfile(Connection connection,int profileId) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        ResultSet resultSet = null;
        Connection con;
        PreparedStatement statement = null;
        Profile profile = null;
        try{
            String query = QueriesUtil.getQuery("findProfile");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setInt(1,profileId);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                profile = new Profile();
                profile.setUserId(resultSet.getInt(Fields.PROFILE_USER_ID));
                profile.setProfileId(resultSet.getInt(Fields.PROFILE_PROFILE_ID));
                profile.setUserRegistrationDate(resultSet.getTimestamp(Fields.PROFILE_USER_REGISTRATION_DATE).toLocalDateTime());
                profile.setUserFirstName(resultSet.getString(Fields.PROFILE_USER_FIRST_NAME));
                profile.setUserSurName(resultSet.getString(Fields.PROFILE_USER_SUR_NAME));
                profile.setAccountBalance(resultSet.getDouble(Fields.PROFILE_ACCOUNT_BALANCE));
            }
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return profile;
    }

    /**
     * Returns All profiles as a list
     * @param connection object with database
     * @return list of all profiles
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public List<Profile> findAllProfiles(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        ResultSet resultSet = null;
        Connection con;
        PreparedStatement statement = null;
        Profile profile;
        List<Profile> profiles = new ArrayList<>();
        try{
            String query = QueriesUtil.getQuery("findAllProfiles");
            con = connection;
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                profile = new Profile();
                profile.setUserId(resultSet.getInt(Fields.PROFILE_USER_ID));
                profile.setProfileId(resultSet.getInt(Fields.PROFILE_PROFILE_ID));
                profile.setUserRegistrationDate(resultSet.getTimestamp(Fields.PROFILE_USER_REGISTRATION_DATE).toLocalDateTime());
                profile.setUserFirstName(resultSet.getString(Fields.PROFILE_USER_FIRST_NAME));
                profile.setUserSurName(resultSet.getString(Fields.PROFILE_USER_SUR_NAME));
                profile.setAccountBalance(resultSet.getDouble(Fields.PROFILE_ACCOUNT_BALANCE));
                profiles.add(profile);
            }
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return profiles;
    }

}
