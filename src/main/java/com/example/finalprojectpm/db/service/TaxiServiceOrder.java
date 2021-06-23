package com.example.finalprojectpm.db.service;


import com.example.finalprojectpm.db.AutoRollback;
import com.example.finalprojectpm.db.OrderDao;
import com.example.finalprojectpm.db.entity.Order;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.exception.DBException;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
/**
 * Service layer for Order entity
 */
public class TaxiServiceOrder {
    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceOrder.class);
    private final OrderDao orderDao;

    /**
     * Sets dao
     * @param orderDao object which will be used
     */
    public TaxiServiceOrder(OrderDao orderDao){
        this.orderDao = orderDao;
    }

    /**
     * Inserts order into table
     * @param order entity that should be inserted
     * @return true if insert operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
    public boolean insertOrder(Order order) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = orderDao.insertOrder(connection,order);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }

        return result;
    }


    /**
     * Inserts orders into table
     * @param orders that should be inserted
     * @return true if all insert operations went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
    public boolean insertOrders(Order... orders) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result= orderDao.insertOrders(connection,orders);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }


    /**
     * Deletes order from table
     * @param order that should be deleted
     * @return true if delete operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
    public boolean deleteOrder(Order order) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result= orderDao.deleteOrder(connection,order);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }


    /**
     * Finds order in table
     * @param order that should be found
     * @return Order if it exists in table and otherwise null
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
    public Order findOrder(Order order) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        Order foundOrder;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            foundOrder = orderDao.findOrder(connection,order);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }

        return foundOrder;
    }


    /**
     * Updates Order by setting new car id
     * @param order that should be updated
     * @param newCarId property that will be changed
     * @return true if update operation went without exception and false otherwise
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
    public boolean updateOrder(Order order, int newCarId) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        boolean result;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            result = orderDao.updateOrder(connection,order,newCarId);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return result;
    }


    /**
     * Returns list of all orders
     * @return List of all orders
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
    public List<Order> findAllOrders() throws ApplicationEXContainer.ApplicationCanNotChangeException {
        List<Order> orders;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            orders =orderDao.findAllOrders(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return orders;
    }
    public List<Order> findOrdersByUser(int userId,int startRow, int rowsPerPage)throws ApplicationEXContainer.ApplicationCanNotChangeException{
        List<Order> orders;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            orders =orderDao.findOrdersByUser(connection,userId,startRow,rowsPerPage);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return orders;
    }

    /**
     * Returns orders' number
     * @return orders' number
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
    public int orderCount() throws ApplicationEXContainer.ApplicationCanNotChangeException {
        int count;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            count = orderDao.orderCount(connection);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }

        return count;
    }

public int orderCountByUser(int userId)throws ApplicationEXContainer.ApplicationCanNotChangeException{
    int count;
    try(Connection connection = MySQLDAOFactory.getConnection();
        AutoRollback autoRollback = new AutoRollback(connection)){
        count = orderDao.orderCountByUser(connection,userId);
        autoRollback.commit();
    } catch (SQLException | NamingException | DBException throwables) {
        LOGGER.error(throwables.getMessage());
        throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
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
     * @throws ApplicationEXContainer.ApplicationCanNotChangeException if some exception arises in dao methods
     */
    public List<Order> findFilSortOrders(Map<String, String> filters, String sortedColumn, boolean descending, int startRow, int rowsPerPage) throws ApplicationEXContainer.ApplicationCanNotChangeException {
        List<Order> orders;
        try(Connection connection = MySQLDAOFactory.getConnection();
            AutoRollback autoRollback = new AutoRollback(connection)){
            orders = orderDao.findFilSortOrders(connection,filters,sortedColumn,descending,startRow,rowsPerPage);
            autoRollback.commit();
        } catch (SQLException | NamingException | DBException throwables) {
            LOGGER.error(throwables.getMessage());
            throw new ApplicationEXContainer.ApplicationCanNotChangeException(throwables.getMessage(),throwables);
        }
        return orders;
    }
}
