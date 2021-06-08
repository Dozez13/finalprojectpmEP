package com.example.finalprojectpm.db.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {
    private Profile profile;
    @BeforeEach
    void init(){
        profile = new Profile();
    }
    @Test
    void getUserRegistrationDate() {
        profile.setUserRegistrationDate(LocalDateTime.of(2015,6,15,23,55,34));
        assertEquals("2015-06-15T23:55:34",profile.getUserRegistrationDate().toString());
    }

    @Test
    void setUserRegistrationDate() {
        profile.setUserRegistrationDate(LocalDateTime.of(2015,6,15,23,55,34));
        assertEquals("2015-06-15T23:55:34",profile.getUserRegistrationDate().toString());
    }

    @Test
    void getUserId() {
        profile.setUserId(1);
        assertEquals(1,profile.getUserId());
    }

    @Test
    void setUserId() {
        profile.setUserId(1);
        assertEquals(1,profile.getUserId());
    }

    @Test
    void getProfileId() {
        profile.setProfileId(1);
        assertEquals(1,profile.getProfileId());
    }

    @Test
    void setProfileId() {
        profile.setProfileId(1);
        assertEquals(1,profile.getProfileId());
    }

    @Test
    void getUserFirstName() {
        profile.setUserFirstName("name");
        assertEquals("name",profile.getUserFirstName());
    }

    @Test
    void setUserFirstName() {
        profile.setUserFirstName("name");
        assertEquals("name",profile.getUserFirstName());
    }

    @Test
    void getUserSurName() {
        profile.setUserSurName("name");
        assertEquals("name",profile.getUserSurName());
    }

    @Test
    void setUserSurName() {
        profile.setUserSurName("name");
        assertEquals("name",profile.getUserSurName());
    }

    @Test
    void testEquals() {
        Profile profile1 = new Profile();
        profile1.setProfileId(1);
        profile.setProfileId(1);
        assertEquals(profile1,profile);
    }

    @Test
    void testHashCode() {
        Profile profile1 = new Profile();
        profile1.setProfileId(1);
        profile.setProfileId(1);
        assertEquals(profile1.hashCode(),profile.hashCode());
    }

    @Test
    void testToString() {
        profile.setProfileId(1);
        profile.setUserId(1);
        profile.setUserSurName("surname");
        profile.setUserFirstName("firstname");
        profile.setUserRegistrationDate(LocalDateTime.of(2015,6,15,23,55,34));
        assertEquals("Profile{userId=1, profileId=1, userFirstName='firstname', userSurName='surname', userRegistrationDate=2015-06-15T23:55:34}",profile.toString());
    }
}