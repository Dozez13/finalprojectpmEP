package com.example.finalprojectpm.db.mysql;

import com.example.finalprojectpm.db.DAOFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MySQLDAOFactoryTest {
    private static DAOFactory daoFactory;
    @BeforeAll
    static void init(){
        daoFactory = DAOFactory.getDAOFactory(1);
    }
    @Test
    void getCarDao() {
        assertNotNull(daoFactory.getCarDao());
    }

    @Test
    void getUserDao() {
        assertNotNull(daoFactory.getUserDao());
    }

    @Test
    void getOrderDao() {
        assertNotNull(daoFactory.getOrderDao());
    }

    @Test
    void getCarCategoryDao() {
        assertNotNull(daoFactory.getCarCategoryDao());
    }
}