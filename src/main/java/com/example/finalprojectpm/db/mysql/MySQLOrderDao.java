package com.example.finalprojectpm.db.mysql;


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

public class MySQLOrderDao implements OrderDao {
    private static final Logger LOGGER = LogManager.getLogger(MySQLOrderDao.class);
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
            if(e.getErrorCode()==1406 ){
                throw new MySQLEXContainer.MySQLDBLargeDataException("Data Is too long",e.getCause());
            }
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(keys,statement,null);
            LOGGER.debug("Close all resources");
        }
        return rowNum>0;
    }

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
            for(int i =0;i< orders.length;i++){
                statement.setInt(1,orders[i].getUserId());
                statement.setInt(2,orders[i].getCarId());
                statement.setString(3,orders[i].getUserAddress());
                statement.setString(4,orders[i].getUserDestination());
                statement.setDouble(5,orders[i].getOrderCost());
                statement.setTimestamp(6, Timestamp.valueOf(orders[i].getOrderDate()));
                statement.addBatch();
            }
            rowNum = statement.executeBatch();
        }catch (SQLException e){
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug("Close all resources");
        }
        return rowNum!=null;
    }

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
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug("Close all resources");
        }
        return rowNum > 0;
    }

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
                foundOrder.setOrderId(resultSet.getInt("orderId"));
                foundOrder.setUserId(resultSet.getInt("userId"));
                foundOrder.setCarId(resultSet.getInt("carId"));
                foundOrder.setUserAddress(resultSet.getString("userAddress"));
                foundOrder.setUserDestination(resultSet.getString("userDestination"));
                foundOrder.setOrderCost(resultSet.getDouble("orderCost"));
                foundOrder.setOrderDate(resultSet.getTimestamp("orderDate").toLocalDateTime());
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug("Close all resources");
        }
        return foundOrder;
    }

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
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(null,statement,null);
            LOGGER.debug("Close all resources");
        }
        return rowNum>0;
    }

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
                order.setCarId(resultSet.getInt("carId"));
                order.setOrderId(resultSet.getInt("orderId"));
                order.setUserId(resultSet.getInt("userId"));
                order.setUserAddress(resultSet.getString("userAddress"));
                order.setUserDestination(resultSet.getString("userDestination"));
                order.setOrderCost(resultSet.getDouble("orderCost"));
                order.setOrderDate(resultSet.getTimestamp("orderDate").toLocalDateTime());
                orders.add(order);
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug("Close all resources");
        }
        return orders;
    }

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
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug("Close all resources");
        }
        return count;
    }

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
                if (key.equals("orderDate")) {
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
                    case "orderId":
                    case "userId":
                    case "carId": {
                        statement.setInt(i + 1, Integer.parseInt(values.get(i)));
                        break;
                    }
                    case "orderDate":
                    case "userAddress":
                    case "userDestination": {
                        statement.setString(i + 1, values.get(i));
                        break;
                    }
                    case "orderCost": {
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
                order.setCarId(resultSet.getInt("carId"));
                order.setOrderId(resultSet.getInt("orderId"));
                order.setUserId(resultSet.getInt("userId"));
                order.setUserAddress(resultSet.getString("userAddress"));
                order.setUserDestination(resultSet.getString("userDestination"));
                order.setOrderCost(resultSet.getDouble("orderCost"));
                order.setOrderDate(resultSet.getTimestamp("orderDate").toLocalDateTime());
                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new MySQLEXContainer.MySQLDBExecutionException("Bad execution",e);
        }finally {
            ConnectionUtil.closeResources(resultSet,statement,null);
            LOGGER.debug("Close all resources");
        }
        return orders;
    }


}
