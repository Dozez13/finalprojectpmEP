package com.example.finalprojectpm.db;

import com.example.finalprojectpm.db.entity.CarCategory;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CarCategoryDao {
    boolean insertCarCategory(Connection connection, CarCategory carCategory) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    boolean deleteCarCategory(Connection connection, String carCategory) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    CarCategory findCarCategory(Connection connection,String carCategory) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    boolean updateCarCategory(Connection connection, String carCategory, double newPrice) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    List<CarCategory> findAllCarC(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    List<CarCategory> findExistingCarC(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
}
