package com.example.checkmypay;

import com.google.type.Date;

import java.io.Serializable;

// input_wage, input_startDate, input_endDate, input_overtime, input_credits,
//            input_fromHour, input_toHour, input_providentFund, input_advancedStudyFund;

public class User implements Serializable {
    private String email, password;
    private float hourlyWage, overtime, credits;
    private Date startDate, endDate;

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
