package com.example.finalprojectpm.db.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class contains method to work with sql resources as Connection, ResultSet, Statement namely for closing it
 * @author Pavlo Manuilenko
 * @see Connection
 * @see ResultSet
 * @see Statement
 */
public class ConnectionUtil {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionUtil.class);
    private ConnectionUtil(){}

    /**
     * Closes resource as ResultSet,Connection,Statement and throws SQLException if it fails to do that
     * @param resultSet ResultSet that should be closed
     * @param statement Statement that should be closed
     * @param connection Connection that should be closed
     * @see Connection
     * @see ResultSet
     * @see Statement
     * @throws SQLException throws SQLException if fails to close resources
     */
    public static void closeResources(ResultSet resultSet, Statement statement, Connection connection) throws SQLException {
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
