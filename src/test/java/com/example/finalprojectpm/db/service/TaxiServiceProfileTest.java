package com.example.finalprojectpm.db.service;


import com.example.finalprojectpm.db.ProfileDao;
import com.example.finalprojectpm.db.entity.Profile;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.DBException;

import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaxiServiceProfileTest {
    private ProfileDao profileDao;
    private TaxiServiceProfile taxiServiceProfile;

    @BeforeAll
    static void initTestMode(){
        MySQLDAOFactory.setTestOn();
    }
    @BeforeEach
    void initTaxiService() {
        profileDao = mock(ProfileDao.class);
        taxiServiceProfile = new TaxiServiceProfile(profileDao);
        Connection connection = mock(Connection.class);
        MySQLDAOFactory.setTestConnection(connection);
    }
    @AfterAll
    public static void setTestModeOff(){
        MySQLDAOFactory.setTestOff();
    }
    @Test
    void insertProfile() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        Profile profile = new Profile();
        when(profileDao.insertProfile(any(Connection.class),any(Profile.class))).thenReturn(true);
        assertTrue(taxiServiceProfile.insertProfile(profile));
    }

    @Test
    void deleteProfile() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        Profile profile = new Profile();
        when(profileDao.deleteProfile(any(Connection.class),any(Profile.class))).thenReturn(true);
        assertTrue(taxiServiceProfile.deleteProfile(profile));
    }

    @Test
    void updateProfileAddBalance() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
       int userId = 5;
       double newAmount = 15.0;
        when(profileDao.updateProfileAddBalance(any(Connection.class),anyInt(),anyDouble())).thenReturn(true);
        assertTrue(taxiServiceProfile.updateProfileAddBalance(userId,newAmount));
    }
    @Test
    void updateProfileSWithdrawBalance() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        int userId = 5;
        double newAmount = 15.0;
        when(profileDao.updateProfileSWithdrawBalance(any(Connection.class),anyInt(),anyDouble())).thenReturn(true);
        assertTrue(taxiServiceProfile.updateProfileSWithdrawBalance(userId,newAmount));
    }
    @Test
    void findProfile() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        int profileId = 5;
        when(profileDao.findProfile(any(Connection.class),anyInt())).thenReturn(new Profile());
        assertNotNull(taxiServiceProfile.findProfile(profileId));
    }

    @Test
    void findAllProfiles() throws DBException, SQLException, ApplicationEXContainer.ApplicationCantRecoverException {
        when(profileDao.findAllProfiles(any(Connection.class))).thenReturn(new ArrayList<>());
        assertNotNull(taxiServiceProfile.findAllProfiles());
    }
}