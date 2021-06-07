package com.example.finalprojectpm.db;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class AutoRollbackTest {

    @Test
    void commit()  {
        boolean exceptionOccur = false;
        try(Connection connection = mock(Connection.class);
            AutoRollback rollback = new AutoRollback(connection)){
            rollback.commit();
        } catch (SQLException throwables) {
            exceptionOccur = true;
        }
        assertFalse(exceptionOccur);
    }


}