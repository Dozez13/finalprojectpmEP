package com.example.finalprojectpm.db.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class CarCategoryTest {
    private CarCategory carCategory;
    @BeforeEach
    void init(){
        carCategory = new CarCategory();
    }
    @Test
    void getCarCategory() {
        carCategory.setCarCategoryName("Some");
        assertNotNull(carCategory.getCarCategoryName());
    }

    @Test
    void setCarCategory() {
        carCategory.setCarCategoryName("Some");
        assertEquals("Some",carCategory.getCarCategoryName());
    }

    @Test
    void getCostPerOneKilometer() {
        carCategory.setCostPerOneKilometer(15.0);
        assertEquals(15.0,carCategory.getCostPerOneKilometer());
    }

    @Test
    void setCostPerOneKilometer() {
        carCategory.setCostPerOneKilometer(15.0);
        assertEquals(15.0,carCategory.getCostPerOneKilometer());
    }

    @Test
    void getDiscount() {
        carCategory.setDiscount(15.0);
        assertEquals(15.0,carCategory.getDiscount());
    }

    @Test
    void setDiscount() {
        carCategory.setDiscount(15.0);
        assertEquals(15.0,carCategory.getDiscount());
    }

    @Test
    void getCarCategoryImage() {
        carCategory.setCarCategoryImage(new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB));
        assertNotNull(carCategory.getCarCategoryImage());
    }

    @Test
    void setCarCategoryImage() {
        carCategory.setCarCategoryImage(new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB));
        assertEquals(200,carCategory.getCarCategoryImage().getHeight());
    }

    @Test
    void testEquals() {
        CarCategory carCategory = new CarCategory();
        carCategory.setCarCategoryName("some");
        CarCategory carCategory1 = new CarCategory();
        carCategory1.setCarCategoryName("some");
        assertEquals(carCategory1, carCategory);
    }

    @Test
    void testHashCode() {
        CarCategory carCategory = new CarCategory();
        carCategory.setCarCategoryName("some");
        CarCategory carCategory1 = new CarCategory();
        carCategory1.setCarCategoryName("some");
        assertEquals(carCategory1.hashCode(), carCategory.hashCode());
    }

    @Test
    void testToString() {
        CarCategory carCategory1 = new CarCategory();
        carCategory1.setCarCategoryName("some");
        carCategory1.setDiscount(15);
        carCategory1.setCostPerOneKilometer(25);
        carCategory1.setCarCategoryImage(new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB));
        assertNotEquals("",carCategory1.toString());
    }
}