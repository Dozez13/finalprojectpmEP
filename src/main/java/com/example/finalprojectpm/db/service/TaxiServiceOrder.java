package com.example.finalprojectpm.db.service;


import com.example.finalprojectpm.db.AutoRollback;
import com.example.finalprojectpm.db.OrderDao;
import com.example.finalprojectpm.db.entity.Order;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TaxiServiceOrder {
    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceOrder.class);
    private final OrderDao orderDao;
    public TaxiServiceOrder(OrderDao orderDao){
        this.orderDao = orderDao;
    }
    public boolean insertOrder(Order order) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = orderDao.insertOrder(connection,order);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBLargeDataException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }

        return result;
    }


    public boolean insertOrders(Order... orders) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result= orderDao.insertOrders(connection,orders);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }


    public boolean deleteOrder(Order order) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result= orderDao.deleteOrder(connection,order);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }


    public Order findOrder(Order order) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        Order foundOrder;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            foundOrder = orderDao.findOrder(connection,order);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }

        return foundOrder;
    }


    public boolean updateOrder(Order order, int newCarId) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = orderDao.updateOrder(connection,order,newCarId);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }


    public List<Order> findAllOrders() throws ApplicationEXContainer.ApplicationCanNotChangeException {
        List<Order> orders;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            orders =orderDao.findAllOrders(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return orders;
    }


    public int orderCount() throws ApplicationEXContainer.ApplicationCanNotChangeException {
        int count;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            count = orderDao.orderCount(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }

        return count;
    }


    public List<Order> findFilSortOrders(Map<String, String> filters, String sortedColumn, boolean descending, int startRow, int rowsPerPage) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        List<Order> orders;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            orders = orderDao.findFilSortOrders(connection,filters,sortedColumn,descending,startRow,rowsPerPage);
            autoRollback.commit();
        } catch (SQLException | NamingException | MySQLEXContainer.MySQLDBExecutionException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return orders;
    }
}
