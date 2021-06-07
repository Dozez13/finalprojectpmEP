package com.example.finalprojectpm.db;

import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    boolean insertUser(Connection connection, User user) throws MySQLEXContainer.MySQLDBNotUniqueException, MySQLEXContainer.MySQLDBLargeDataException, MySQLEXContainer.MySQLDBExecutionException, SQLException;
    boolean deleteUser(Connection connection,String login) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    boolean validateUser(Connection connection,String login,String password) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    User findUser(Connection connection,String login) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    boolean updateUser(Connection connection,String login,String newPassword) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    List<User> findAllUser(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
}
