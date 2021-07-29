package com.example.finalprojectpm.db.service;

import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.DBException;

import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.junit.jupiter.api.*;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaxiServiceUserTest {
    private UserDao userDao;
    private TaxiServiceUser taxiServiceUser;

    @BeforeAll
    static void initTestMode(){
        MySQLDAOFactory.setTestOn();
    }
    @BeforeEach
    void initTaxiService() {
        userDao = mock(UserDao.class);
        taxiServiceUser = new TaxiServiceUser(userDao);
        Connection connection = mock(Connection.class);
        MySQLDAOFactory.setTestConnection(connection);
    }
    @AfterAll
    public static void setTestModeOff(){
        MySQLDAOFactory.setTestOff();
    }
    @Test
    void insertUser() throws SQLException, DBException, ApplicationEXContainer.ApplicationCantRecoverException {
        User user = new User();
        when(userDao.insertUser(any(Connection.class),any(User.class))).thenReturn(true);
        assertTrue(taxiServiceUser.insertUser(user));


    }
    @Test
    void insertUserNotUniqueException() throws SQLException, DBException, ApplicationEXContainer.ApplicationCantRecoverException {
        User user = new User();
        when(userDao.insertUser(any(Connection.class),any(User.class))).thenThrow(new SQLException("Login or Email is already in database","23000",1062));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()->taxiServiceUser.insertUser(user));
        assertEquals("Login or Email is already in database",thrown.getMessage());
    }
    @Test
    void insertUserExecutionException() throws SQLException, DBException {
        User user = new User();
        when(userDao.insertUser(any(Connection.class),any(User.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()->taxiServiceUser.insertUser(user));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void deleteUser() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        String login ="someLogin";
        when(userDao.deleteUser(any(Connection.class),anyString())).thenReturn(true);
        assertTrue(taxiServiceUser.deleteUser(login));
    }
    @Test
    void deleteUserException() throws DBException, SQLException {
        String login ="someLogin";
        when(userDao.deleteUser(any(Connection.class),anyString())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()->taxiServiceUser.deleteUser(login));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void validateUser() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        String login = "Some login";
        String password = "Some password";
        when(userDao.validateUser(any(Connection.class),anyString(),anyString())).thenReturn(true);
        assertTrue(taxiServiceUser.validateUser(login,password));
    }
    @Test
    void validateUserException() throws DBException, SQLException{
        String login = "Some login";
        String password = "Some password";
        when(userDao.validateUser(any(Connection.class),anyString(),anyString())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()->taxiServiceUser.validateUser(login,password));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void findUser() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        String login = "some Login";
        when(userDao.findUser(any(Connection.class),anyString())).thenReturn(new User());
        assertNotNull(taxiServiceUser.findUser(login));
    }
    @Test
    void findUserException() throws DBException, SQLException {
        String login = "some Login";
        when(userDao.findUser(any(Connection.class),anyString())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()->taxiServiceUser.findUser(login));
        assertEquals("can't close some resources",thrown.getMessage());
    }

    @Test
    void updateUser() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        String login = "some Login";
        String newPassword = "new Password";
        when(userDao.updateUser(any(Connection.class),anyString(),anyString())).thenReturn(true);
        assertTrue(taxiServiceUser.updateUser(login,newPassword));
    }
    @Test
    void updateUserException() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        String login = "some Login";
        String newPassword = "new Password";
        when(userDao.updateUser(any(Connection.class),anyString(),anyString())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()->taxiServiceUser.updateUser(login,newPassword));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void findAllUser() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        when(userDao.findAllUser(any(Connection.class))).thenReturn(new ArrayList<>());
        assertNotNull(taxiServiceUser.findAllUser());
    }
    @Test
    void findAllUserException() throws DBException, SQLException {
        when(userDao.findAllUser(any(Connection.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCantRecoverException.class,()->taxiServiceUser.findAllUser());
        assertEquals("can't close some resources",thrown.getMessage());
    }
}