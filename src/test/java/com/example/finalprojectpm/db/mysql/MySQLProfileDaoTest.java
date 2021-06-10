package com.example.finalprojectpm.db.mysql;


import com.example.finalprojectpm.db.ProfileDao;
import com.example.finalprojectpm.db.entity.Profile;
import com.example.finalprojectpm.db.exception.DBException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MySQLProfileDaoTest {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ProfileDao profileDao;
    private ResultSet resultSet;
    @BeforeEach
    void init() throws SQLException {

        profileDao = new MySQLProfileDao();
        preparedStatement = mock(PreparedStatement.class);
        connection = mock(Connection.class);
        resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    void deleteProfile() throws SQLException, DBException {
        Profile profile = new Profile();
        profile.setProfileId(1);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean result = profileDao.deleteProfile(connection,profile);
        assertTrue(result);
    }

    @Test
    void updateProfileAddBalance() throws SQLException, DBException {
        int userId = 5;
        double newAmount = 15.0;
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean result = profileDao.updateProfileAddBalance(connection,userId,newAmount);
        assertTrue(result);
    }
    @Test
    void updateProfileSWithdrawBalance() throws SQLException, DBException {
        int userId = 5;
        double newAmount = 15.0;
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean result = profileDao.updateProfileSWithdrawBalance(connection,userId,newAmount);
        assertTrue(result);
    }

    @Test
    void findProfile() throws SQLException, DBException {
        int profileId = 5;
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("profileId")).thenReturn(1);
        when(resultSet.getInt("userId")).thenReturn(1);
        when(resultSet.getTimestamp("userRegistrationDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getString("userFirstName")).thenReturn("firstname");
        when(resultSet.getString("userSurName")).thenReturn("surname");
        when(resultSet.getDouble("accountBalance")).thenReturn(15.0);
        Profile profile = profileDao.findProfile(connection,profileId);
        assertNotNull(profile);
    }

    @Test
    void findAllProfiles() throws DBException, SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("profileId")).thenReturn(1);
        when(resultSet.getInt("userId")).thenReturn(1);
        when(resultSet.getTimestamp("userRegistrationDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getString("userFirstName")).thenReturn("firstname");
        when(resultSet.getString("userSurName")).thenReturn("surname");
        when(resultSet.getDouble("accountBalance")).thenReturn(15.0);
        List<Profile> profile = profileDao.findAllProfiles(connection);
        assertNotNull(profile);
    }


}