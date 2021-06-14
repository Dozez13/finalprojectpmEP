package com.example.finalprojectpm.db.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class work with property file to obtain sql query in string representation
 * It contains methods to load property file and get query
 */
public class QueriesUtil {
    private static final String QUERIES_PROPERTIES = "queries.properties";
    private static Properties props;
    private QueriesUtil(){}

    /**
     * This method loads property file and return it
     * @return loaded property file which contains query
     * @throws SQLException if file is can't be loaded
     */
    public static Properties getQueries() throws SQLException {
        //singleton
        if (props == null) {
            InputStream is =
                    QueriesUtil.class.getResourceAsStream("/" + QUERIES_PROPERTIES);

            props = new Properties();
            try {
                props.load(is);
            } catch (IOException e) {
                throw new SQLException("Unable to load property file: " + QUERIES_PROPERTIES + System.lineSeparator() + e.getMessage());
            }
        }
        return props;
    }

    /**
     * This method gets query from property file and returns it
     * @param query key to get specific sql query
     * @return String sql query representation
     * @throws SQLException if property file can't be loaded
     */
    public static String getQuery(String query) throws SQLException {
        return getQueries().getProperty(query);
    }

}