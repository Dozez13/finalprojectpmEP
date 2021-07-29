package com.example.finalprojectpm.db.service;

import com.example.finalprojectpm.db.ProfileDao;
import com.example.finalprojectpm.db.UserDao;
import com.example.finalprojectpm.db.entity.Profile;
import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.exception.DBException;

import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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

    @BeforeAll
    static void initTestMode(){
        MySQLDAOFactory.setTestOn();
    }
    @BeforeEach
    void initTaxiService() {
        profileDao = mock(ProfileDao.class);
        userDao = mock(UserDao.class);
        taxiServiceRegistration = new TaxiServiceRegistration(userDao,profileDao);
        Connection connection = mock(Connection.class);
        MySQLDAOFactory.setTestConnection(connection);
    }
    @AfterAll
    public static void setTestModeOff(){
        MySQLDAOFactory.setTestOff();
    }
    @Test
    void doRegistration() throws SQLException, DBException {
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