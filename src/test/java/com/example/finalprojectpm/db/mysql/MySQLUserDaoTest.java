package com.example.finalprojectpm.db.mysql;

import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import com.example.finalprojectpm.db.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MySQLUserDaoTest {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private UserDao userDao;
    private ResultSet resultSet;
    @BeforeEach
    void init() throws SQLException {
        userDao = new MySQLUserDao();
        preparedStatement = mock(PreparedStatement.class);
        connection = mock(Connection.class);
        resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    void deleteUser() throws SQLException, MySQLEXContainer.MySQLDBExecutionException {
        String login = "Login";
        when(connection.prepareStatement(anyString()).executeUpdate()).thenReturn(1);
        boolean result = userDao.deleteUser(connection,login);
        assertTrue(result);
    }

    @Test
    void findUser() throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        String login = "Login";
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("Login")).thenReturn("log");
        when(resultSet.getInt("userId")).thenReturn(1);
        when(resultSet.getString("userPassword")).thenReturn("password");
        when(resultSet.getString("userType")).thenReturn("client");
        when(resultSet.getString("userEmail")).thenReturn("email");
        User user = userDao.findUser(connection,login);
        assertNotNull(user);
    }

    @Test
    void validateUser() throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        String login = "Login";
        String password = "password";
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("userPassword")).thenReturn(PasswordUtil.generateStrongPasswordHash("password"));
        boolean result = userDao.validateUser(connection,login,password);
        assertTrue(result);
    }

    @Test
    void updateUser() throws SQLException, MySQLEXContainer.MySQLDBExecutionException {
        String login = "Login";
        String newPassword = "newPassword";
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean result = userDao.updateUser(connection,login,newPassword);
        assertTrue(result);
    }

    @Test
    void findAllUser() throws SQLException, MySQLEXContainer.MySQLDBExecutionException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("Login")).thenReturn("log");
        when(resultSet.getInt("userId")).thenReturn(1);
        when(resultSet.getString("userPassword")).thenReturn("password");
        when(resultSet.getString("userType")).thenReturn("client");
        when(resultSet.getString("userEmail")).thenReturn("email");
        List<User> users = userDao.findAllUser(connection);
        assertNotNull(users);

    }

}