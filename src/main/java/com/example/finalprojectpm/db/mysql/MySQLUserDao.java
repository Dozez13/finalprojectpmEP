package com.example.finalprojectpm.db.mysql;


import com.example.finalprojectpm.db.Fields;
import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import com.example.finalprojectpm.db.util.ConnectionUtil;
import com.example.finalprojectpm.db.util.PasswordUtil;
import com.example.finalprojectpm.db.util.QueriesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Data access object for User related entities
 */
public class MySQLUserDao implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(MySQLUserDao.class);


    /**
     * Inserts User into database table
     * @param connection object with database
     * @param user entity that should be inserted
     * @return true if insert operation went without exception and false otherwise
     * @throws MySQLEXContainer.MySQLDBNotUniqueException if SQLException with error code 1062 at execution query arises
     * @throws MySQLEXContainer.MySQLDBLargeDataException if SQLException with error code 1406 at execution query arises
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException with other error codes at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public boolean insertUser(Connection connection, User user) throws MySQLEXContainer.MySQLDBNotUniqueException, MySQLEXContainer.MySQLDBLargeDataException, MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Insert User Is started");
        int rowNum;
        ResultSet keys = null;
        Connection con;
        PreparedStatement statement = null;
        try {
            String query = QueriesUtil.getQuery("insertUser");
            con = connection;
            statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, PasswordUtil.generateStrongPasswordHash(user.getPassword()));
            statement.setString(3, user.getUserType());
            statement.setString(4, user.getUserEmail());
            rowNum = statement.executeUpdate();
            keys = statement.getGeneratedKeys();
            if (keys.next()) {
                user.setUserId(keys.getInt(1));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            if (e.getErrorCode() == 1062) {
                throw new MySQLEXContainer.MySQLDBNotUniqueException(String.format("%s %s", user.getLogin(), user.getUserEmail()), e.getCause());
            } else if (e.getErrorCode() == 1406) {
                throw new MySQLEXContainer.MySQLDBLargeDataException("Data Is too long", e);
            }
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(), e);
        } finally {
            ConnectionUtil.closeResources(keys, statement, null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return rowNum > 0;
    }

    /**
     * Deletes User into database table
     * @param connection object with database
     * @param login property by which entity should be deleted
     * @return true if delete operation went without exception and false otherwise
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public boolean deleteUser(Connection connection,String login) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Delete User Is started");
        int rowNum;
        Connection con;
        PreparedStatement statement = null;
        try {
            String query = QueriesUtil.getQuery("deleteUser");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setString(1, login);
            rowNum = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(null ,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return rowNum > 0;
    }

    /**
     * Finds User entity by login in database table
     * @param connection object with database
     * @param login property by which entity should be found
     * @return User object if it exists in table and otherwise null
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public User findUser(Connection connection,String login) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Find User Is started");
        User foundUser =null;
        ResultSet resultSet = null;
        Connection con;
        PreparedStatement statement = null;
        try{
            String query = QueriesUtil.getQuery("findUser");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setString(1,login);
            resultSet =statement.executeQuery();
            if(resultSet.next()){
                foundUser = new User();
                foundUser.setLogin(resultSet.getString(Fields.USER_LOGIN));
                foundUser.setUserId(resultSet.getInt(Fields.USER_USER_ID));
                foundUser.setPassword(resultSet.getString(Fields.USER_USER_PASSWORD));
                foundUser.setUserType(resultSet.getString(Fields.USER_USER_TYPE));
                foundUser.setUserEmail(resultSet.getString(Fields.USER_USER_EMAIL));
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);

        }finally {
            ConnectionUtil.closeResources(resultSet ,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return foundUser;
    }

    /**
     * Validates user from database table
     * @param connection object with database
     * @param login property by which entity should be found
     * @param password property by which entity should be validated
     * @return true if validate operation user with these login and password exists
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public boolean validateUser(Connection connection,String login,String password) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Validate User Is started");
        ResultSet resultSet = null;
        Connection con;
        PreparedStatement statement = null;
        try{
            String query = QueriesUtil.getQuery("validateUser");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setString(1,login);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                String hashedPassword = resultSet.getString(Fields.USER_USER_PASSWORD);
                return PasswordUtil.validatePassword(password,hashedPassword);
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(resultSet ,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return false;
    }

    /**
     * Updates found user by login setting new password to it
     * @param connection object with database
     * @param login property by which entity should be found
     * @param newPassword property that should be updated
     * @return true if update operation user with these login and password exists
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public boolean updateUser(Connection connection,String login,String newPassword) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Update user is started");
        int rowNum;
        Connection con;
        PreparedStatement statement = null;
        try {
            String query = QueriesUtil.getQuery("updateUser");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setString(1, PasswordUtil.generateStrongPasswordHash(newPassword));
            statement.setString(2,login);
            rowNum = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(null ,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return rowNum>0;
    }

    /**
     * Returns All users
     * @param connection object with database
     * @return list of all users
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public List<User> findAllUser(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Find All User is started");
        ResultSet resultSet = null;
        Connection con;
        PreparedStatement statement = null;
        List<User> users = new ArrayList<>();
        try {
            String query = QueriesUtil.getQuery("findAllUser");
            con = connection;
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                User user = new User();
                user.setLogin(resultSet.getString(Fields.USER_LOGIN));
                user.setUserId(resultSet.getInt(Fields.USER_USER_ID));
                user.setUserType(resultSet.getString(Fields.USER_USER_TYPE));
                user.setPassword(resultSet.getString(Fields.USER_USER_PASSWORD));
                user.setUserEmail(resultSet.getString(Fields.USER_USER_EMAIL));
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(resultSet ,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return users;
    }


}
