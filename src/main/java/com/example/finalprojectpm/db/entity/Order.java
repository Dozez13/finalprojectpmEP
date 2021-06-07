package com.example.finalprojectpm.db.entity;

import com.example.finalprojectpm.db.CustomLocalDateTimeJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order implements Serializable {
    private static final long serialVersionUID = -4577879200654802752L;
    private int orderId;
    private int userId;
    private int carId;
    private String userAddress;
    private String userDestination;
    private double orderCost;
    @JsonSerialize(using = CustomLocalDateTimeJsonSerializer.class)
    private LocalDateTime orderDate;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserDestination() {
        return userDestination;
    }

    public void setUserDestination(String userDestination) {
        this.userDestination = userDestination;
    }

    public double getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(double orderCost) {
        this.orderCost = orderCost;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getOrderId() == order.getOrderId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", userId=").append(userId);
        sb.append(", carId=").append(carId);
        sb.append(", userAddress='").append(userAddress).append('\'');
        sb.append(", userDestination='").append(userDestination).append('\'');
        sb.append(", orderCost=").append(orderCost);
        sb.append(", orderDate=").append(orderDate);
        sb.append('}');
        return sb.toString();
    }
}
