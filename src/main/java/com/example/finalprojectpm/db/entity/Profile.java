package com.example.finalprojectpm.db.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Profile implements Serializable {
    private static final long serialVersionUID = -1473879605284728654L;
    private int userId;
    private int profileId;
    private String userFirstName;
    private String userSurName;
    private LocalDateTime userRegistrationDate;
    private double accountBalance;

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public LocalDateTime getUserRegistrationDate() {
        return userRegistrationDate;
    }

    public void setUserRegistrationDate(LocalDateTime userRegistrationDate) {
        this.userRegistrationDate = userRegistrationDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }


    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserSurName() {
        return userSurName;
    }

    public void setUserSurName(String userSurName) {
        this.userSurName = userSurName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return getProfileId() == profile.getProfileId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProfileId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Profile{");
        sb.append("userId=").append(userId);
        sb.append(", profileId=").append(profileId);
        sb.append(", userFirstName='").append(userFirstName).append('\'');
        sb.append(", userSurName='").append(userSurName).append('\'');
        sb.append(", userRegistrationDate=").append(userRegistrationDate);
        sb.append(", accountBalance=").append(accountBalance);
        sb.append('}');
        return sb.toString();
    }
}
