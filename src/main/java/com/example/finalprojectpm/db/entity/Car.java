package com.example.finalprojectpm.db.entity;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Objects;

public class Car implements Serializable {
    private static final long serialVersionUID = -1473879605284728654L;
    private int carId;
    private String carCategory;
    private int numOfPas;
    private String carState;
    private String carName;
    private transient BufferedImage carImage;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(String carCategory) {
        this.carCategory = carCategory;
    }

    public int getNumOfPas() {
        return numOfPas;
    }

    public void setNumOfPas(int numOfPas) {
        this.numOfPas = numOfPas;
    }

    public String getCarState() {
        return carState;
    }

    public void setCarState(String carState) {
        this.carState = carState;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public BufferedImage getCarImage() {
        return carImage;
    }

    public void setCarImage(BufferedImage carImage) {
        this.carImage = carImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return getCarId() == car.getCarId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCarId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car{");
        sb.append("carId=").append(carId);
        sb.append(", carCategory='").append(carCategory).append('\'');
        sb.append(", numOfPas=").append(numOfPas);
        sb.append(", carState='").append(carState).append('\'');
        sb.append(", carName='").append(carName).append('\'');
        sb.append(", carImage=").append(carImage.getHeight());
        sb.append('}');
        return sb.toString();
    }
}
