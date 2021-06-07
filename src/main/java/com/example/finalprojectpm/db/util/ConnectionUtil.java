package com.example.finalprojectpm.db.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtil {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionUtil.class);
    private ConnectionUtil(){}
    public static void oneMethodToCloseThemAll(ResultSet resultSet, Statement statement, Connection connection) throws SQLException {
        if (resultSet != null&&!resultSet.isClosed()) {
            resultSet.close();
            LOGGER.info("Result set Is closed");
        }
        if (statement != null&&!statement.isClosed()) {
            statement.close();
            LOGGER.info("Statement set Is closed");
        }
        if (connection != null&&!connection.isClosed()) {
            connection.close();
            LOGGER.info("Connection set Is closed");
        }
    }
}
