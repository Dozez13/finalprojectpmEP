package com.example.finalprojectpm.db;

import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.exception.DBException;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    boolean insertUser(Connection connection, User user) throws DBException, SQLException;
    boolean deleteUser(Connection connection,String login) throws DBException, SQLException;
    boolean validateUser(Connection connection,String login,String password) throws DBException, SQLException;
    User findUser(Connection connection,String login) throws DBException, SQLException;
    boolean updateUser(Connection connection,String login,String newPassword) throws DBException, SQLException;
    List<User> findAllUser(Connection connection) throws DBException, SQLException;
}
