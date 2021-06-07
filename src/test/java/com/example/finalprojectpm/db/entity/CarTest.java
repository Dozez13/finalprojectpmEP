package com.example.finalprojectpm.db.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    private Car car;
    @BeforeEach
    void init(){
        car = new Car();
    }
    @Test
    void getCarId() {
        car.setCarId(1);
        assertEquals(1,car.getCarId());
    }

    @Test
    void setCarId() {
        car.setCarId(1);
        assertEquals(1,car.getCarId());
    }

    @Test
    void getCarCategory() {
        car.setCarCategory("some");
        assertEquals("some",car.getCarCategory());
    }

    @Test
    void setCarCategory() {
        car.setCarCategory("some");
        assertEquals("some",car.getCarCategory());
    }

    @Test
    void getNumOfPas() {
        car.setNumOfPas(5);
        assertEquals(5,car.getNumOfPas());
    }

    @Test
    void setNumOfPas() {
        car.setNumOfPas(5);
        assertEquals(5,car.getNumOfPas());
    }

    @Test
    void getCarState() {
        car.setCarState("ready");
        assertEquals("ready",car.getCarState());
    }

    @Test
    void setCarState() {
        car.setCarState("ready");
        assertEquals("ready",car.getCarState());
    }

    @Test
    void getCarName() {
        car.setCarName("BMW");
        assertEquals("BMW",car.getCarName());
    }

    @Test
    void setCarName() {
        car.setCarName("BMW");
        assertEquals("BMW",car.getCarName());
    }

    @Test
    void getCarImage() {
        car.setCarImage(new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB));
        assertNotNull(car.getCarImage());
    }

    @Test
    void setCarImage() {
        car.setCarImage(new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB));
        assertEquals(200,car.getCarImage().getHeight());
    }

    @Test
    void testEquals() {
        car.setCarId(1);
        Car newCar = new Car();
        newCar.setCarId(1);
        assertEquals(car,newCar);
    }

    @Test
    void testHashCode() {
        car.setCarId(1);
        Car newCar = new Car();
        newCar.setCarId(1);
        assertEquals(car.hashCode(),newCar.hashCode());
    }

    @Test
    void testToString() {
        Car myCar = new Car();
        myCar.setCarId(1);
        myCar.setCarName("some");
        myCar.setCarImage(new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB));
        myCar.setCarCategory("Business");
        myCar.setCarState("ready");
        myCar.setNumOfPas(5);
        String expected = "Car{carId=1, carCategory='Business', numOfPas=5, carState='ready', carName='some', carImage=200}";
        assertEquals(expected,myCar.toString());
    }
}