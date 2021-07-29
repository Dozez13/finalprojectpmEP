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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Stream;
/**
 * Service layer for all entity
 */
@Service
public class TaxiServiceMakeOrder {
    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceMakeOrder.class);

    private final CarDao mySQLCarDao;
    private final OrderDao mySQLOrderDao;
    private final CarCategoryDao mySQLCarCategoryDao;
    private final UserDao mySQLUserDao;
    private final ProfileDao mySQLProfileDao;

    /**
     * Set all dao
     * @param mySQLCarDao object which will be used to manipulate Car
     * @param mySQLOrderDao object which will be used to manipulate Order
     * @param mySQLCarCategoryDao object which will be used to manipulate CarCategory
     * @param mySQLUserDao object which will be used to manipulate User
     * @param mySQLProfileDao object which will be used to manipulate Profile
     */
    @Autowired
    public TaxiServiceMakeOrder(CarDao mySQLCarDao, OrderDao mySQLOrderDao, CarCategoryDao mySQLCarCategoryDao, UserDao mySQLUserDao, ProfileDao mySQLProfileDao){
        this.mySQLCarDao = mySQLCarDao;
        this.mySQLOrderDao = mySQLOrderDao;
        this.mySQLCarCategoryDao = mySQLCarCategoryDao;
        this.mySQLUserDao = mySQLUserDao;
        this.mySQLProfileDao = mySQLProfileDao;
    }

    /**
     * @param stingNumbers array that contains number of passenger of each car
     * @param categories array that contains category of each car
     * @param userAddress user location
     * @param userDestination user destination
     * @param login user login
     * @return string message to client
     * @throws ApplicationEXContainer.ApplicationSendOrderMessageException if car not found
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if dao methods throw exception
     */
    public String makeOrder(String[] stingNumbers, String[] categories, String userAddress, String userDestination, String login) throws ApplicationEXContainer.ApplicationSendOrderMessageException, ApplicationEXContainer.ApplicationCantRecoverException {
        String messageTakenTime = null;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            int[] numbers = Stream.of(stingNumbers).mapToInt(Integer::parseInt).toArray();
            Car[] foundCars = new Car[categories.length];
            User foundUser = mySQLUserDao.findUser(connection,login);
            for (int i = 0; i < foundCars.length; i++) {
                foundCars[i] = mySQLCarDao.findCar(connection,numbers[i], categories[i]);
                if (foundCars[i] == null) {
                    LOGGER.info("Car with  {} number of passenger and category {} Is not found",numbers[i],categories[i]);
                    throw new MySQLEXContainer.MySQLDBCarNotFoundException(String.format("%s %d", categories[i], numbers[i]));
                }
                mySQLCarDao.updateCar(connection,foundCars[i].getCarId(), "on Order");
                double distance = DistanceUtil.getDistance(userAddress, userDestination);
                CarCategory foundCarCategory = mySQLCarCategoryDao.findCarCategory(connection,categories[i]);
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
                mySQLProfileDao.updateProfileSWithdrawBalance(connection,foundUser.getUserId(),orderCost);
                mySQLOrderDao.insertOrder(connection,order);
                if (messageTakenTime == null) {
                    messageTakenTime = DistanceUtil.takenTime(distance);
                }
            }
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException|MySQLEXContainer.MySQLDBLargeDataException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables);
        }catch (DBException e){
            LOGGER.error(e.getMessage());
            throw new ApplicationEXContainer.ApplicationSendOrderMessageException(e.getMessage(),e);
        }
        return messageTakenTime;
    }
}

