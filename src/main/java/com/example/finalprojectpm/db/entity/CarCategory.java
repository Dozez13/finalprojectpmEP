package com.example.finalprojectpm.db.entity;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Objects;

/**
 * CarCategory entity
 * @author Pavlo Manuilenko
 */
public class CarCategory implements Serializable {
    private static final long serialVersionUID = -4179236910442782235L;
    private String carCategoryName;
    private double costPerOneKilometer;
    private double discount;
    private transient BufferedImage carCategoryImage;

    public String getCarCategoryName() {
        return carCategoryName;
    }

    public void setCarCategoryName(String carCategoryName) {
        this.carCategoryName = carCategoryName;
    }

    public double getCostPerOneKilometer() {
        return costPerOneKilometer;
    }

    public void setCostPerOneKilometer(double costPerOneKilometer) {
        this.costPerOneKilometer = costPerOneKilometer;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public BufferedImage getCarCategoryImage() {
        return carCategoryImage;
    }

    public void setCarCategoryImage(BufferedImage carCategoryImage) {
        this.carCategoryImage = carCategoryImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarCategory that = (CarCategory) o;
        return Objects.equals(getCarCategoryName(), that.getCarCategoryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCarCategoryName());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CarCategory{");
        sb.append("carCategory='").append(carCategoryName).append('\'');
        sb.append(", costPerOneKilometer=").append(costPerOneKilometer);
        sb.append(", discount=").append(discount);
        sb.append(", carCategoryImage=").append(carCategoryImage.getHeight());
        sb.append('}');
        return sb.toString();
    }
}
