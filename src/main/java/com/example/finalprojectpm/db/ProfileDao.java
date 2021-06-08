package com.example.finalprojectpm.db;

import com.example.finalprojectpm.db.entity.Profile;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ProfileDao {
    boolean insertProfile(Connection connection, Profile profile) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    boolean deleteProfile(Connection connection,Profile profile) throws SQLException, MySQLEXContainer.MySQLDBExecutionException;
    boolean updateProfile(Connection connection,int userId,String userFirstName) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    Profile findProfile(Connection connection,int profileId) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    List<Profile> findAllProfiles(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;


}
