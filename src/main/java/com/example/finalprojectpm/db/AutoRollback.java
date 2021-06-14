package com.example.finalprojectpm.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class contains functionality to rollback database action if statement fails
 * @author Pavlo Manuilenko
 * @see AutoCloseable
 */
public class AutoRollback implements AutoCloseable {
    private Connection conn;
    private boolean committed;

    /**
     * This method commit database action
     * @param conn connection for commit
     */
    public AutoRollback(Connection conn){
        this.conn = conn;
    }
    public void commit() throws SQLException {
        conn.commit();
        committed = true;
    }

    /**
     * This method rollbacks action if flow fails
     * @throws SQLException if rollback is failed
     */
    @Override
    public void close() throws SQLException {
        if(!committed) {
            conn.rollback();
        }
    }
}
