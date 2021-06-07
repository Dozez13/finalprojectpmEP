package com.example.finalprojectpm.db.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class QueriesUtil {
    private static final String QUERIES_PROPERTIES = "queries.properties";
    private static Properties props;
    private QueriesUtil(){}
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

    public static String getQuery(String query) throws SQLException {
        return getQueries().getProperty(query);
    }

}