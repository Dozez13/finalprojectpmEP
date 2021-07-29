package com.example.finalprojectpm.db.service;


import com.example.finalprojectpm.db.AutoRollback;
import com.example.finalprojectpm.db.OrderDao;
import com.example.finalprojectpm.db.entity.Order;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.DBException;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
/**
 * Service layer for Order entity
 */
@Service
public class TaxiServiceOrder {
    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceOrder.class);

    private final OrderDao mySQLOrderDao;

    /**
     * Sets dao
     * @param mySQLOrderDao object which will be used
     */
    @Autowired
    public TaxiServiceOrder(OrderDao mySQLOrderDao){
        this.mySQLOrderDao = mySQLOrderDao;
    }

    /**
     * Inserts order into table
     * @param order entity that should be inserted
     * @return true if insert operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public boolean insertOrder(Order order) throws ApplicationEXContainer.ApplicationCantRecoverException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = mySQLOrderDao.insertOrder(connection,order);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }

        return result;
    }


    /**
     * Inserts orders into table
     * @param orders that should be inserted
     * @return true if all insert operations went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public boolean insertOrders(Order... orders) throws ApplicationEXContainer.ApplicationCantRecoverException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result= mySQLOrderDao.insertOrders(connection,orders);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }
        return result;
    }


    /**
     * Deletes order from table
     * @param order that should be deleted
     * @return true if delete operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public boolean deleteOrder(Order order) throws ApplicationEXContainer.ApplicationCantRecoverException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result= mySQLOrderDao.deleteOrder(connection,order);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }
        return result;
    }


    /**
     * Finds order in table
     * @param order that should be found
     * @return Order if it exists in table and otherwise null
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public Order findOrder(Order order) throws ApplicationEXContainer.ApplicationCantRecoverException {
        Order foundOrder;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            foundOrder = mySQLOrderDao.findOrder(connection,order);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }

        return foundOrder;
    }


    /**
     * Updates Order by setting new car id
     * @param order that should be updated
     * @param newCarId property that will be changed
     * @return true if update operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public boolean updateOrder(Order order, int newCarId) throws ApplicationEXContainer.ApplicationCantRecoverException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = mySQLOrderDao.updateOrder(connection,order,newCarId);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }
        return result;
    }


    /**
     * Returns list of all orders
     * @return List of all orders
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public List<Order> findAllOrders() throws ApplicationEXContainer.ApplicationCantRecoverException {
        List<Order> orders;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            orders = mySQLOrderDao.findAllOrders(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }
        return orders;
    }

    /**
     * Return specific Orders by user
     * @param userId id for user searched orders
     * @param startRow start row for searching
     * @param rowsPerPage number of rows
     * @return orders by user if some exception arises in dao methods
     * @throws ApplicationEXContainer.ApplicationCantRecoverException
     */
    public List<Order> findOrdersByUser(int userId,int startRow, int rowsPerPage)throws ApplicationEXContainer.ApplicationCantRecoverException {
        List<Order> orders;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            orders = mySQLOrderDao.findOrdersByUser(connection,userId,startRow,rowsPerPage);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }
        return orders;
    }

    /**
     * Returns orders' number
     * @return orders' number
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public int orderCount() throws ApplicationEXContainer.ApplicationCantRecoverException {
        int count;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            count = mySQLOrderDao.orderCount(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }

        return count;
    }

    /**
     * Returns user order's number
     * @param userId id for user searched orders
     * @return number of User orders
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
public int orderCountByUser(int userId)throws ApplicationEXContainer.ApplicationCantRecoverException {
    int count;
    try(Connection connection = MySQLDAOFactory.getConnection();
        AutoRollback autoRollback = new AutoRollback(connection)){
        count = mySQLOrderDao.orderCountByUser(connection,userId);
        autoRollback.commit();
    } catch (SQLException | NamingException | DBException throwables) {
        LOGGER.error(throwables.getMessage());
        throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
    }
    return count;
}
    /**
     * This method returns List of orders that went through filtering and sorting.
     * @param filters Map object that contains columns names as key and by which value it should be filtered as value
     * @param sortedColumn sorting column name
     * @param descending desc true or false
     * @param startRow start row as limit
     * @param rowsPerPage number of rows as offset
     * @return List of processed orders
     * @throws ApplicationEXContainer.ApplicationCantRecoverException if some exception arises in dao methods
     */
    public List<Order> findFilSortOrders(Map<String, String> filters, String sortedColumn, boolean descending, int startRow, int rowsPerPage) throws ApplicationEXContainer.ApplicationCantRecoverException {
        List<Order> orders;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            orders = mySQLOrderDao.findFilSortOrders(connection,filters,sortedColumn,descending,startRow,rowsPerPage);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCantRecoverException(throwables.getMessage(),throwables);
        }
        return orders;
    }
}
