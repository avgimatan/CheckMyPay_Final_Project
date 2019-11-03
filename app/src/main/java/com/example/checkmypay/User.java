package com.example.checkmypay;

import com.google.type.Date;

import java.io.Serializable;

public class User implements Serializable {
    private String email, password;
    private float hourlyWage, providentFund, advancedStudyFund, credits;
    private int startDate, endDate, shabbatFromHour, shabbatToHour, shabbatFromMin, shabbatToMin;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


}
