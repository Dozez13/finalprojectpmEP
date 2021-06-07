package com.example.finalprojectpm.db.mysql;


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

public class MySQLUserDao implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(MySQLUserDao.class);
    private static final String USER_PASSWORD = "userPassword";

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
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution", e);
        } finally {
            ConnectionUtil.oneMethodToCloseThemAll(keys, statement, null);
            LOGGER.debug("Close all resources");
        }
        return rowNum > 0;
    }

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
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.oneMethodToCloseThemAll(null ,statement,null);
            LOGGER.debug("Close all resources");
        }
        return rowNum > 0;
    }

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
                foundUser.setLogin(resultSet.getString("login"));
                foundUser.setUserId(resultSet.getInt("userId"));
                foundUser.setPassword(resultSet.getString(USER_PASSWORD));
                foundUser.setUserType(resultSet.getString("userType"));
                foundUser.setUserEmail(resultSet.getString("userEmail"));
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);

        }finally {
            ConnectionUtil.oneMethodToCloseThemAll(resultSet ,statement,null);
            LOGGER.debug("Close all resources");
        }
        return foundUser;
    }
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
                String hashedPassword = resultSet.getString(USER_PASSWORD);
                return PasswordUtil.validatePassword(password,hashedPassword);
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.oneMethodToCloseThemAll(resultSet ,statement,null);
            LOGGER.debug("Close all resources");
        }
        return false;
    }
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
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.oneMethodToCloseThemAll(null ,statement,null);
            LOGGER.debug("Close all resources");
        }
        return rowNum>0;
    }

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
                user.setLogin(resultSet.getString("login"));
                user.setUserId(resultSet.getInt("userId"));
                user.setUserType(resultSet.getString("userType"));
                user.setPassword(resultSet.getString(USER_PASSWORD));
                user.setUserEmail(resultSet.getString("userEmail"));
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.oneMethodToCloseThemAll(resultSet ,statement,null);
            LOGGER.debug("Close all resources");
        }
        return users;
    }


}
