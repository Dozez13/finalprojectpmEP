package com.example.finalprojectpm.db.mysql;

import com.example.finalprojectpm.db.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySQLDAOFactory extends DAOFactory {
    private static final Logger LOGGER = LogManager.getLogger(MySQLDAOFactory.class);
    private static DataSource dataSource;
    public static void dataSourceInit() throws NamingException {
        LOGGER.debug("Starting initialize datasource");
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:comp/env");
        dataSource = (DataSource) envContext.lookup("jdbc/UsersDB");
        LOGGER.debug("Ended initialize datasource");
    }
    public static Connection getConnection() throws NamingException, SQLException {
        if(dataSource==null){
            dataSourceInit();
        }
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new SQLException(e);
        }
        return connection;
    }
    @Override
    public CarDao getCarDao() {
        return new MySQLCarDao();
    }
    @Override
    public UserDao getUserDao() {
        return new MySQLUserDao();
    }
    @Override
    public OrderDao getOrderDao() {
        return new MySQLOrderDao();
    }
    @Override
    public CarCategoryDao getCarCategoryDao() {
        return new MySQLCarCategoryDao();
    }

    @Override
    public ProfileDao getProfileDao() {
        return new MySQLProfileDao();
    }
}
