package com.example.finalprojectpm.db.service;

import com.example.finalprojectpm.db.ProfileDao;
import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.entity.Profile;
import com.example.finalprojectpm.db.entity.User;

import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import hthurow.tomcatjndi.TomcatJNDI;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaxiServiceRegistrationTest {
    private ProfileDao profileDao;
    private UserDao userDao;
    private TaxiServiceRegistration taxiServiceRegistration;
    private static TomcatJNDI tomcatJNDI;
    @BeforeAll
    static void initJNDI(){
        tomcatJNDI = new TomcatJNDI();
        tomcatJNDI.processContextXml(new File("src/main/webapp/META-INF/context.xml"));
        tomcatJNDI.start();
    }
    @BeforeEach
    void initTaxiService() {
        profileDao = mock(ProfileDao.class);
        userDao = mock(UserDao.class);
        taxiServiceRegistration = new TaxiServiceRegistration(userDao,profileDao);
    }
    @AfterAll
    static void dataSourceClearEnv(){
        tomcatJNDI.tearDown();
    }
    @Test
    void doRegistration() throws SQLException, MySQLEXContainer.MySQLDBExecutionException, MySQLEXContainer.MySQLDBLargeDataException, MySQLEXContainer.MySQLDBNotUniqueException {
        String firstName = "firstName";
        String surName = "surName";
        String login = "Login" ;
        String email = "email";
        String psw = "psw";
        when(userDao.insertUser(any(Connection.class),any(User.class))).thenReturn(true);
        when(profileDao.insertProfile(any(Connection.class),any(Profile.class))).thenReturn(true);
        assertDoesNotThrow(() -> taxiServiceRegistration.doRegistration(firstName,surName,login,email,psw));
    }
}