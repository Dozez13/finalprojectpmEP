package com.example.finalprojectpm.db.mysql;


import com.example.finalprojectpm.db.Fields;
import com.example.finalprojectpm.db.OrderDao;
import com.example.finalprojectpm.db.entity.Order;
import com.example.finalprojectpm.db.exception.MySQLEXContainer;
import com.example.finalprojectpm.db.util.ConnectionUtil;
import com.example.finalprojectpm.db.util.QueriesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Data access object for Order related entities
 */
public class MySQLOrderDao implements OrderDao {
    private static final Logger LOGGER = LogManager.getLogger(MySQLOrderDao.class);

    /**
     * Inserts Order entity into database table
     * @param connection object with database
     * @param order entity to be inserted
     * @return true if insert operation went without exception and false otherwise
     * @throws MySQLEXContainer.MySQLDBLargeDataException if SQLException with error code 1406 at execution query arises
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException with other error codes at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public boolean insertOrder(Connection connection, Order order) throws MySQLEXContainer.MySQLDBLargeDataException, MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Insert Order Is Started");
        int rowNum ;
        ResultSet keys = null;
        Connection con;
        PreparedStatement statement = null;
        try {
            String query = QueriesUtil.getQuery("insertOrder");
            con = connection;
            statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,order.getUserId());
            statement.setInt(2,order.getCarId());
            statement.setString(3, order.getUserAddress());
            statement.setString(4, order.getUserDestination());
            statement.setDouble(5,order.getOrderCost());
            statement.setTimestamp(6, Timestamp.valueOf(order.getOrderDate()));
            rowNum = statement.executeUpdate();
            keys = statement.getGeneratedKeys();
            if(keys.next()){
                order.setOrderId(keys.getInt(1));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            if(e.getErrorCode()==1406){
                throw new MySQLEXContainer.MySQLDBLargeDataException("Data Is too long",e.getCause());
            }
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(keys,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return rowNum>0;
    }

    /**
     * Inserts Orders entity into database table
     * @param connection object with database
     * @param orders Array or Only one entity
     * @return true if all insert operations went without exception and false otherwise
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public boolean insertOrders(Connection connection,Order... orders) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Insert Orders Is started");
        int[] rowNum;
        Connection con;
        PreparedStatement statement = null;
        try{
            String query = QueriesUtil.getQuery("insertOrders");
            con = connection;
            statement = con.prepareStatement(query);
            for (Order order : orders) {
                statement.setInt(1, order.getUserId());
                statement.setInt(2, order.getCarId());
                statement.setString(3, order.getUserAddress());
                statement.setString(4, order.getUserDestination());
                statement.setDouble(5, order.getOrderCost());
                statement.setTimestamp(6, Timestamp.valueOf(order.getOrderDate()));
                statement.addBatch();
            }
            rowNum = statement.executeBatch();
        }catch (SQLException e){
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return rowNum!=null;
    }

    /**
     * Deletes Order entity from database table
     * @param connection object with database
     * @param order entity to be deleted
     * @return true if insert operation went without exception and false otherwise
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public boolean deleteOrder(Connection connection,Order order) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Delete Order Is started");
        int rowNum;
        Connection con;
        PreparedStatement statement = null;
        try {
            String query = QueriesUtil.getQuery("deleteOrder");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setInt(1,order.getOrderId());
            rowNum = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return rowNum > 0;
    }

    /**
     * Returns Order entity from database table
     * @param connection object with database
     * @param order entity to be found
     * @return Order's object if It is found and null otherwise
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public Order findOrder(Connection connection,Order order) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Find Order Is started");
        Order foundOrder = null;
        ResultSet resultSet = null;
        Connection con;
        PreparedStatement statement = null;
        try {
            String query = QueriesUtil.getQuery("findOrder");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setInt(1,order.getOrderId());
            resultSet =statement.executeQuery();
            if(resultSet.next()){
                foundOrder = new Order();
                foundOrder.setOrderId(resultSet.getInt(Fields.ORDER_ORDER_ID));
                foundOrder.setUserId(resultSet.getInt(Fields.ORDER_USER_ID));
                foundOrder.setCarId(resultSet.getInt(Fields.ORDER_CAR_ID));
                foundOrder.setUserAddress(resultSet.getString(Fields.ORDER_USER_ADDRESS));
                foundOrder.setUserDestination(resultSet.getString(Fields.ORDER_DEST_ADDRESS));
                foundOrder.setOrderCost(resultSet.getDouble(Fields.ORDER_ORDER_COST));
                foundOrder.setOrderDate(resultSet.getTimestamp(Fields.ORDER_ORDER_DATE).toLocalDateTime());
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return foundOrder;
    }

    /**
     * Updates Order entity from database table by setting new car id
     * @param connection object with database
     * @param order entity to be updated
     * @param newCarId property which should be updated
     * @return true if update operation went without exception and false otherwise
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public boolean updateOrder(Connection connection,Order order, int newCarId) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Update Order Is started");
        int rowNum ;
        Connection con;
        PreparedStatement statement = null;
        try {
            String query = QueriesUtil.getQuery("updateOrder");
            con = connection;
            statement = con.prepareStatement(query);
            statement.setInt(1, newCarId);
            statement.setInt(2,order.getOrderId());
            rowNum = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return rowNum>0;
    }

    /**
     * Returns all orders from database table
     * @param connection object with database
     * @return List of all orders
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public List<Order> findAllOrders(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Find All Orders Is started");
        ResultSet resultSet = null;
        Connection con;
        PreparedStatement statement = null;
        List<Order> orders = new ArrayList<>();
        try {
            String query = QueriesUtil.getQuery("findAllOrders");
            con = connection;
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                Order order = new Order();
                order.setCarId(resultSet.getInt(Fields.ORDER_CAR_ID));
                order.setOrderId(resultSet.getInt(Fields.ORDER_ORDER_ID));
                order.setUserId(resultSet.getInt(Fields.ORDER_USER_ID));
                order.setUserAddress(resultSet.getString(Fields.ORDER_USER_ADDRESS));
                order.setUserDestination(resultSet.getString(Fields.ORDER_DEST_ADDRESS));
                order.setOrderCost(resultSet.getDouble(Fields.ORDER_ORDER_COST));
                order.setOrderDate(resultSet.getTimestamp(Fields.ORDER_ORDER_DATE).toLocalDateTime());
                orders.add(order);
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return orders;
    }

    /**
     * Returns order's number
     * @param connection object with database
     * @return number of orders
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public int orderCount(Connection connection) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Order Count Is started");
        ResultSet resultSet = null;
        Connection con;
        PreparedStatement statement = null;
        int count=0;
        try {
            String query = QueriesUtil.getQuery("orderCount");
            con = connection;
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                count = resultSet.getInt(1);
            }

        }catch (SQLException e){
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return count;
    }

    /**
     * This method returns List of orders that went through filtering and sorting.
     * For pagination it uses start row and rowsPerPage value
     * @param connection object with database
     * @param filters Map object that contains columns names as key and by which value it should be filtered as value
     * @param sortedColumn sorting column name
     * @param descending desc true or false
     * @param startRow start row as limit
     * @param rowsPerPage number of rows as offset
     * @return List of processed orders
     * @throws MySQLEXContainer.MySQLDBExecutionException if SQLException at execution query arises
     * @throws SQLException if resources can't be closed
     */
    @Override
    public List<Order> findFilSortOrders(Connection connection, Map<String, String> filters, String sortedColumn, boolean descending, int startRow, int rowsPerPage) throws MySQLEXContainer.MySQLDBExecutionException, SQLException {
        LOGGER.debug("Find, Filter, Sort Orders Is started");
        StringBuilder query = new StringBuilder("SELECT*FROM (SELECT*FROM user_order ");
        ResultSet resultSet = null;
        Connection con;
        PreparedStatement statement = null;
        LinkedHashMap<String, String> filterToQuery;
        List<String> values = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        if (filters != null) {
            query.append("WHERE ");
            filterToQuery = new LinkedHashMap<>(filters);
            values = new ArrayList<>(filterToQuery.values());
            keys = new ArrayList<>(filterToQuery.keySet());
            keys.forEach(key ->
            {
                if (key.equals(Fields.ORDER_ORDER_DATE)) {
                    query.append("date_format(").append(key).append(",'%Y-%m-%d %H:%i') = ? AND ");
                } else {
                    query.append(key).append(" = ? AND ");
                }
            });
            query.setLength(query.length() - 4);
        }
        query.append(" LIMIT ?, ?) a").append(" ORDER BY ").append(sortedColumn).append(" ").append(descending ? "DESC" : "ASC;");
        try {
            con = connection;
            statement = con.prepareStatement(query.toString());
            for (int i = 0; i < values.size(); i++) {
                switch (keys.get(i)) {
                    case Fields.ORDER_ORDER_ID:
                    case Fields.ORDER_USER_ID:
                    case Fields.ORDER_CAR_ID: {
                        statement.setInt(i + 1, Integer.parseInt(values.get(i)));
                        break;
                    }
                    case Fields.ORDER_ORDER_DATE:
                    case Fields.ORDER_USER_ADDRESS:
                    case Fields.ORDER_DEST_ADDRESS: {
                        statement.setString(i + 1, values.get(i));
                        break;
                    }
                    case Fields.ORDER_ORDER_COST: {
                        statement.setDouble(i + 1, Double.parseDouble(values.get(i)));
                        break;
                    }
                }
            }
            statement.setInt(values.size() + 1, startRow);
            statement.setInt(values.size() + 2, rowsPerPage);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setCarId(resultSet.getInt(Fields.ORDER_CAR_ID));
                order.setOrderId(resultSet.getInt(Fields.ORDER_ORDER_ID));
                order.setUserId(resultSet.getInt(Fields.ORDER_USER_ID));
                order.setUserAddress(resultSet.getString(Fields.ORDER_USER_ADDRESS));
                order.setUserDestination(resultSet.getString(Fields.ORDER_DEST_ADDRESS));
                order.setOrderCost(resultSet.getDouble(Fields.ORDER_ORDER_COST));
                order.setOrderDate(resultSet.getTimestamp(Fields.ORDER_ORDER_DATE).toLocalDateTime());
                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException(e.getMessage(),e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug(Fields.LOG_CLOSE_RESOURCES);
        }
        return orders;
    }


}
