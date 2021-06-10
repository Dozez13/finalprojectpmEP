package com.example.finalprojectpm.db;

import com.example.finalprojectpm.db.entity.Car;
import com.example.finalprojectpm.db.exception.DBException;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CarDao {
    boolean insertCar(Connection connection, Car car) throws DBException, SQLException;
    boolean deleteCar(Connection connection,int carId) throws DBException, SQLException;
    Car findCar(Connection connection,int carId) throws DBException,SQLException;
    Car findCar(Connection connection,int numOfPas,String carCategory)throws DBException, SQLException;
    boolean updateCar(Connection connection,int carId,String newCarCategory) throws DBException, SQLException;
    List<Car> findAllCars(Connection connection) throws DBException, SQLException;
    List<Integer> findNumberCarByCat(Connection connection) throws DBException, SQLException;
}
