package com.example.finalprojectpm.db.mysql;

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

public class MySQLProfileDao implements ProfileDao {
    private static final Logger LOGGER = LogManager.getLogger(MySQLProfileDao.class);
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
        }
        return rowNum>0;
    }

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
        }
        return rowNum>0;
    }

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
        }
        return rowNum>0;
    }
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
        }
        return rowNum>0;
    }
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
                profile.setUserId(resultSet.getInt("userId"));
                profile.setProfileId(resultSet.getInt("profileId"));
                profile.setUserRegistrationDate(resultSet.getTimestamp("userRegistrationDate").toLocalDateTime());
                profile.setUserFirstName(resultSet.getString("userFirstName"));
                profile.setUserSurName(resultSet.getString("userSurName"));
                profile.setAccountBalance(resultSet.getDouble("accountBalance"));
            }
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
        }
        return profile;
    }

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
                profile.setUserId(resultSet.getInt("userId"));
                profile.setProfileId(resultSet.getInt("profileId"));
                profile.setUserRegistrationDate(resultSet.getTimestamp("userRegistrationDate").toLocalDateTime());
                profile.setUserFirstName(resultSet.getString("userFirstName"));
                profile.setUserSurName(resultSet.getString("userSurName"));
                profile.setAccountBalance(resultSet.getDouble("accountBalance"));
                profiles.add(profile);
            }
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
        }
        return profiles;
    }

}
