package com.example.finalprojectpm.db;

import com.example.finalprojectpm.db.entity.Car;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CarDao {
    boolean insertCar(Connection connection, Car car) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    boolean deleteCar(Connection connection,int carId) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    Car findCar(Connection connection,int carId) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    Car findCar(Connection connection,int numOfPas,String carCategory) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    boolean updateCar(Connection connection,int carId,String newCarCategory) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    List<Car> findAllCars(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
    List<Integer> findNumberCarByCat(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException;
}
