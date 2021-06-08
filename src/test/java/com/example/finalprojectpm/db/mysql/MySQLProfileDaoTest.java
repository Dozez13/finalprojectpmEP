package com.example.finalprojectpm.db.mysql;


import com.example.finalprojectpm.db.ProfileDao;
import com.example.finalprojectpm.db.entity.Profile;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
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
    void deleteProfile() throws SQLException, MySQLEXContainer.MySQLDBExecutionException {
        Profile profile = new Profile();
        profile.setProfileId(1);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean result = profileDao.deleteProfile(connection,profile);
        assertTrue(result);
    }

    @Test
    void updateProfile() throws SQLException, MySQLEXContainer.MySQLDBExecutionException {
        int userId = 5;
        String newFirstName = "new";
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean result = profileDao.updateProfile(connection,userId,newFirstName);
        assertTrue(result);
    }

    @Test
    void findProfile() throws SQLException, MySQLEXContainer.MySQLDBExecutionException {
        int profileId = 5;
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("profileId")).thenReturn(1);
        when(resultSet.getInt("userId")).thenReturn(1);
        when(resultSet.getTimestamp("userRegistrationDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getString("userFirstName")).thenReturn("firstname");
        when(resultSet.getString("userSurName")).thenReturn("surname");
        Profile profile = profileDao.findProfile(connection,profileId);
        assertNotNull(profile);
    }

    @Test
    void findAllProfiles() throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("profileId")).thenReturn(1);
        when(resultSet.getInt("userId")).thenReturn(1);
        when(resultSet.getTimestamp("userRegistrationDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getString("userFirstName")).thenReturn("firstname");
        when(resultSet.getString("userSurName")).thenReturn("surname");
        List<Profile> profile = profileDao.findAllProfiles(connection);
        assertNotNull(profile);
    }


}