package com.example.finalprojectpm.db.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;
    @BeforeEach
    void init(){
        user = new User();
    }
    @Test
    void getUserId() {
        user.setUserId(1);
        assertEquals(1,user.getUserId());
    }

    @Test
    void setUserId() {
        user.setUserId(1);
        assertEquals(1,user.getUserId());
    }

    @Test
    void getLogin() {
        user.setLogin("login");
        assertEquals("login",user.getLogin());
    }

    @Test
    void setLogin() {
        user.setLogin("login");
        assertEquals("login",user.getLogin());
    }

    @Test
    void getPassword() {
        user.setPassword("password");
        assertEquals("password",user.getPassword());
    }

    @Test
    void setPassword() {
        user.setPassword("password");
        assertEquals("password",user.getPassword());
    }

    @Test
    void getUserType() {
        user.setUserType("client");
        assertEquals("client",user.getUserType());
    }

    @Test
    void setUserType() {
        user.setUserType("client");
        assertEquals("client",user.getUserType());
    }

    @Test
    void getUserEmail() {
        user.setUserEmail("email");
        assertEquals("email",user.getUserEmail());
    }

    @Test
    void setUserEmail() {
        user.setUserEmail("email");
        assertEquals("email",user.getUserEmail());
    }

    @Test
    void testEquals() {
        User user1 = new User();
        user1.setUserId(1);
        User user2 = new User();
        user2.setUserId(1);
        assertEquals(user1,user2);
    }

    @Test
    void testHashCode() {
        User user1 = new User();
        user1.setUserId(1);
        User user2 = new User();
        user2.setUserId(1);
        assertEquals(user1.hashCode(),user2.hashCode());
    }

    @Test
    void testToString() {
        user.setUserId(1);
        user.setLogin("login");
        user.setPassword("password");
        user.setUserEmail("email");
        user.setUserType("client");
        assertEquals("User{userId=1, login='login', password='password', userType='client', userEmail='email'}",user.toString());
    }
}