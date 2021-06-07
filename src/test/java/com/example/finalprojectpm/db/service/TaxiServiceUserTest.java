package com.example.finalprojectpm.db.service;

import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import hthurow.tomcatjndi.TomcatJNDI;
import org.junit.jupiter.api.*;

import java.io.File;
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
    private static TomcatJNDI tomcatJNDI;
    @BeforeAll
    static void initJNDI(){
        tomcatJNDI = new TomcatJNDI();
        tomcatJNDI.processContextXml(new File("src/main/webapp/META-INF/context.xml"));
        tomcatJNDI.start();
    }
    @BeforeEach
    void initTaxiService() {
        userDao = mock(UserDao.class);
        taxiServiceUser = new TaxiServiceUser(userDao);
    }
    @AfterAll
    static void dataSourceClearEnv(){
        tomcatJNDI.tearDown();
    }
    @Test
    void insertUser() throws SQLException, MySQLEXContainer.MySQLDBExecutionException, MySQLEXContainer.MySQLDBLargeDataException, MySQLEXContainer.MySQLDBNotUniqueException, ApplicationEXContainer.ApplicationCanNotChangeException, ApplicationEXContainer.ApplicationCanChangeException {
        User user = new User();
        when(userDao.insertUser(any(Connection.class),any(User.class))).thenReturn(true);
        assertTrue(taxiServiceUser.insertUser(user));


    }
    @Test
    void insertUserNotUniqueException() throws SQLException, MySQLEXContainer.MySQLDBExecutionException, MySQLEXContainer.MySQLDBLargeDataException, MySQLEXContainer.MySQLDBNotUniqueException, ApplicationEXContainer.ApplicationCanNotChangeException, ApplicationEXContainer.ApplicationCanChangeException {
        User user = new User();
        when(userDao.insertUser(any(Connection.class),any(User.class))).thenThrow(new SQLException("Login or Email is already in database","23000",1062));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()->taxiServiceUser.insertUser(user));
        assertEquals("Login or Email is already in database",thrown.getMessage());
    }
    @Test
    void insertUserExecutionException() throws SQLException, MySQLEXContainer.MySQLDBExecutionException, MySQLEXContainer.MySQLDBLargeDataException, MySQLEXContainer.MySQLDBNotUniqueException {
        User user = new User();
        when(userDao.insertUser(any(Connection.class),any(User.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()->taxiServiceUser.insertUser(user));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void deleteUser() throws MySQLEXContainer.MySQLDBExecutionException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        String login ="someLogin";
        when(userDao.deleteUser(any(Connection.class),anyString())).thenReturn(true);
        assertTrue(taxiServiceUser.deleteUser(login));
    }
    @Test
    void deleteUserException() throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        String login ="someLogin";
        when(userDao.deleteUser(any(Connection.class),anyString())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()->taxiServiceUser.deleteUser(login));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void validateUser() throws MySQLEXContainer.MySQLDBExecutionException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        String login = "Some login";
        String password = "Some password";
        when(userDao.validateUser(any(Connection.class),anyString(),anyString())).thenReturn(true);
        assertTrue(taxiServiceUser.validateUser(login,password));
    }
    @Test
    void validateUserException() throws MySQLEXContainer.MySQLDBExecutionException, SQLException{
        String login = "Some login";
        String password = "Some password";
        when(userDao.validateUser(any(Connection.class),anyString(),anyString())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()->taxiServiceUser.validateUser(login,password));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void findUser() throws MySQLEXContainer.MySQLDBExecutionException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        String login = "some Login";
        when(userDao.findUser(any(Connection.class),anyString())).thenReturn(new User());
        assertNotNull(taxiServiceUser.findUser(login));
    }
    @Test
    void findUserException() throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        String login = "some Login";
        when(userDao.findUser(any(Connection.class),anyString())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()->taxiServiceUser.findUser(login));
        assertEquals("can't close some resources",thrown.getMessage());
    }

    @Test
    void updateUser() throws MySQLEXContainer.MySQLDBExecutionException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        String login = "some Login";
        String newPassword = "new Password";
        when(userDao.updateUser(any(Connection.class),anyString(),anyString())).thenReturn(true);
        assertTrue(taxiServiceUser.updateUser(login,newPassword));
    }
    @Test
    void updateUserException() throws MySQLEXContainer.MySQLDBExecutionException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        String login = "some Login";
        String newPassword = "new Password";
        when(userDao.updateUser(any(Connection.class),anyString(),anyString())).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()->taxiServiceUser.updateUser(login,newPassword));
        assertEquals("can't close some resources",thrown.getMessage());
    }
    @Test
    void findAllUser() throws MySQLEXContainer.MySQLDBExecutionException, SQLException, ApplicationEXContainer.ApplicationCanNotChangeException {
        when(userDao.findAllUser(any(Connection.class))).thenReturn(new ArrayList<>());
        assertNotNull(taxiServiceUser.findAllUser());
    }
    @Test
    void findAllUserException() throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        when(userDao.findAllUser(any(Connection.class))).thenThrow(new SQLException("can't close some resources"));
        Throwable thrown = assertThrows(ApplicationEXContainer.ApplicationCanNotChangeException.class,()->taxiServiceUser.findAllUser());
        assertEquals("can't close some resources",thrown.getMessage());
    }
}