package com.example.finalprojectpm.db.service;


import com.example.finalprojectpm.db.*;
import com.example.finalprojectpm.db.entity.Car;
import com.example.finalprojectpm.db.entity.CarCategory;
import com.example.finalprojectpm.db.entity.Order;
import com.example.finalprojectpm.db.entity.User;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.DBException;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import com.example.finalprojectpm.db.util.DistanceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class TaxiServiceMakeOrder {
    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceMakeOrder.class);
    private final CarDao carDao;
    private final OrderDao orderDao;
    private final CarCategoryDao categoryDao;
    private final UserDao userDao;
    private final ProfileDao profileDao;
    public TaxiServiceMakeOrder(CarDao carDao, OrderDao orderDao, CarCategoryDao categoryDao, UserDao userDao,ProfileDao profileDao){
        this.carDao = carDao;
        this.orderDao = orderDao;
        this.categoryDao = categoryDao;
        this.userDao = userDao;
        this.profileDao =profileDao;
    }

    public String makeOrder(String[] stingNumbers, String[] categories, String userAddress, String userDestination, String login) throws ApplicationEXContainer.ApplicationCanChangeException, ApplicationEXContainer.ApplicationCanNotChangeException {
        String messageTakenTime = null;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            int[] numbers = Stream.of(stingNumbers).mapToInt(Integer::parseInt).toArray();
            Car[] foundCars = new Car[categories.length];
            User foundUser = userDao.findUser(connection,login);
            for (int i = 0; i < foundCars.length; i++) {
                foundCars[i] = carDao.findCar(connection,numbers[i], categories[i]);
                if (foundCars[i] == null) {
                    LOGGER.info("Car with  {} number of passenger and category {} Is not found",numbers[i],categories[i]);
                    throw new MySQLEXContainer.MySQLDBCarNotFoundException(String.format("%s %d", categories[i], numbers[i]));
                }
                carDao.updateCar(connection,foundCars[i].getCarId(), "on Order");
                double distance = DistanceUtil.getDistance(userAddress, userDestination);
                CarCategory foundCarCategory = categoryDao.findCarCategory(connection,categories[i]);
                double discount = foundCarCategory.getDiscount();
                double costPerKilo = foundCarCategory.getCostPerOneKilometer();
                int scale = (int) Math.pow(10, 1);
                double orderCost = (double) Math.round(((distance * costPerKilo) - ((distance * costPerKilo) * discount)) * scale) / scale;
                Order order = new Order();
                order.setUserId(foundUser.getUserId());
                order.setCarId(foundCars[i].getCarId());
                order.setOrderDate(LocalDateTime.now());
                order.setUserAddress(userAddress);
                order.setUserDestination(userDestination);
                order.setOrderCost(orderCost);
                profileDao.updateProfileSWithdrawBalance(connection,foundUser.getUserId(),orderCost);
                orderDao.insertOrder(connection,order);
                if (messageTakenTime == null) {
                    messageTakenTime = DistanceUtil.takenTime(distance);
                }
            }
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException|MySQLEXContainer.MySQLDBLargeDataException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables);
        }catch (DBException e){
            LOGGER.error(e.getMessage());
            throw new ApplicationEXContainer.ApplicationCanChangeException(e.getMessage(),e);
        }
        return messageTakenTime;
    }
}

