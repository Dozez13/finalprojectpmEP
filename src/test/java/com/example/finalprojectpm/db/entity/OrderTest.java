package com.example.finalprojectpm.db.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private Order order;
    @BeforeEach
    void init(){
        order = new Order();
    }
    @Test
    void getOrderId() {
        order.setOrderId(1);
        assertEquals(1,order.getOrderId());
    }

    @Test
    void setOrderId() {
        order.setOrderId(1);
        assertEquals(1,order.getOrderId());
    }

    @Test
    void getUserId() {
        order.setUserId(1);
        assertEquals(1,order.getUserId());
    }

    @Test
    void setUserId() {
        order.setUserId(1);
        assertEquals(1,order.getUserId());
    }

    @Test
    void getCarId() {
        order.setCarId(1);
        assertEquals(1,order.getCarId());
    }

    @Test
    void setCarId() {
        order.setCarId(1);
        assertEquals(1,order.getCarId());
    }

    @Test
    void getUserAddress() {
        order.setUserAddress("some");
        assertEquals("some",order.getUserAddress());
    }

    @Test
    void setUserAddress() {
        order.setUserAddress("some");
        assertEquals("some",order.getUserAddress());
    }

    @Test
    void getUserDestination() {
        order.setUserDestination("some");
        assertEquals("some",order.getUserDestination());
    }

    @Test
    void setUserDestination() {
        order.setUserDestination("some");
        assertEquals("some",order.getUserDestination());
    }

    @Test
    void getOrderCost() {
        order.setOrderCost(15);
        assertEquals(15,order.getOrderCost());
    }

    @Test
    void setOrderCost() {
        order.setOrderCost(15);
        assertEquals(15,order.getOrderCost());
    }

    @Test
    void getOrderDate() {
        order.setOrderDate(LocalDateTime.of(2015,6,15,23,36,55));
        assertEquals("2015-06-15T23:36:55",order.getOrderDate().toString());
    }

    @Test
    void setOrderDate() {
        order.setOrderDate(LocalDateTime.of(2015,6,15,23,36,55));
        assertEquals("2015-06-15T23:36:55",order.getOrderDate().toString());
    }

    @Test
    void testEquals() {
        Order order = new Order();
        order.setOrderId(5);
        Order secondOrder = new Order();
        secondOrder.setOrderId(5);
        assertEquals(order,secondOrder);
    }

    @Test
    void testHashCode() {
        Order order = new Order();
        order.setOrderId(5);
        Order secondOrder = new Order();
        secondOrder.setOrderId(5);
        assertEquals(order.hashCode(),secondOrder.hashCode());
    }

    @Test
    void testToString() {
        order.setOrderId(1);
        order.setOrderDate(LocalDateTime.of(2015,6,15,23,36,55));
        order.setOrderCost(255);
        order.setUserId(1);
        order.setCarId(2);
        order.setUserAddress("some");
        order.setUserDestination("destSome");
        assertEquals("Order{orderId=1, userId=1, carId=2, userAddress='some', userDestination='destSome', orderCost=255.0, orderDate=2015-06-15T23:36:55}",order.toString());
    }
}