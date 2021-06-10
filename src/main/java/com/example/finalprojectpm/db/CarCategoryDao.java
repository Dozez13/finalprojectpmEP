package com.example.finalprojectpm.db;

import com.example.finalprojectpm.db.entity.CarCategory;
import com.example.finalprojectpm.db.exception.DBException;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CarCategoryDao {
    boolean insertCarCategory(Connection connection, CarCategory carCategory)throws DBException,SQLException;
    boolean deleteCarCategory(Connection connection, String carCategory) throws DBException, SQLException;
    CarCategory findCarCategory(Connection connection,String carCategory) throws DBException, SQLException;
    boolean updateCarCategory(Connection connection, String carCategory, double newPrice) throws DBException,SQLException;
    List<CarCategory> findAllCarC(Connection connection) throws DBException, SQLException;
    List<CarCategory> findExistingCarC(Connection connection) throws DBException, SQLException;
}
