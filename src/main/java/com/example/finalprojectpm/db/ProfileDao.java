package com.example.finalprojectpm.db;

import com.example.finalprojectpm.db.entity.Profile;
import com.example.finalprojectpm.db.exception.DBException;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ProfileDao {
    boolean insertProfile(Connection connection, Profile profile) throws DBException, SQLException;
    boolean deleteProfile(Connection connection,Profile profile) throws DBException,  SQLException;
    boolean updateProfileAddBalance(Connection connection, int userId, double accountBalance) throws DBException, SQLException;
    boolean updateProfileSWithdrawBalance(Connection connection, int userId, double accountBalance) throws DBException, SQLException;
    Profile findProfile(Connection connection, int profileId)throws DBException, SQLException;
    List<Profile> findAllProfiles(Connection connection) throws DBException, SQLException;


}
