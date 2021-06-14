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


/**
 *Factory for creating DAOs and obtaining connection using Connection Pool JNDI with MYSQL
 * @author Pavlo Manuilenko
 */
public class MySQLDAOFactory extends DAOFactory {
    private static final Logger LOGGER = LogManager.getLogger(MySQLDAOFactory.class);
    private static DataSource dataSource;

    /**
     * Initializes Connection Pool. Before using this
     * method you must configure the Date Source and the Connections Pool in your
     * WEB_APP_ROOT/META-INF/context.xml file.
     * @throws NamingException If context file Is broken
     */
    public static void dataSourceInit() throws NamingException {
        LOGGER.debug("Starting initialize datasource");
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:comp/env");
        dataSource = (DataSource) envContext.lookup("jdbc/UsersDB");
        LOGGER.debug("Ended initialize datasource");
    }

    /**
     * Returns a DB connection from the Pool Connections and If Pool is not initialized do it
     * @return DB connection from the Pool Connections
     * @throws NamingException If context file Is broken
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Returns MySQLCarDao object
     * @return MYSQL implementation of CarDao
     */
    @Override
    public CarDao getCarDao() {
        return new MySQLCarDao();
    }

    /**
     * Returns MySQLUserDao object
     * @return MYSQL implementation of UserDao
     */
    @Override
    public UserDao getUserDao() {
        return new MySQLUserDao();
    }

    /**
     * Returns MySQLOrderDao object
     * @return MYSQL implementation of OrderDao
     */
    @Override
    public OrderDao getOrderDao() {
        return new MySQLOrderDao();
    }
    /**
     * Returns MySQLCarCategoryDao object
     * @return MYSQL implementation of CarCategoryDao
     */
    @Override
    public CarCategoryDao getCarCategoryDao() {
        return new MySQLCarCategoryDao();
    }
    /**
     * Returns MySQLProfileDao object
     * @return MYSQL implementation of ProfileDao
     */
    @Override
    public ProfileDao getProfileDao() {
        return new MySQLProfileDao();
    }
}
