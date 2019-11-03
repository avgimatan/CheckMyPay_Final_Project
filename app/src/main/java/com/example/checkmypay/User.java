package com.example.checkmypay;

import java.io.Serializable;

// user json:
/**
 *      hourlyWage
 *      startDate
 *      endDate
 *      fromHour
 *      fromMinute
 *      toHour
 *      toMinute
 *      providentFund
 *      advancedStudyFund
 *      credits
 */

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

    public void setHourlyWage(float hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public void setProvidentFund(float providentFund) {
        this.providentFund = providentFund;
    }

    public void setAdvancedStudyFund(float advancedStudyFund) {
        this.advancedStudyFund = advancedStudyFund;
    }

    public void setCredits(float credits) {
        this.credits = credits;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public void setShabbatFromHour(int shabbatFromHour) {
        this.shabbatFromHour = shabbatFromHour;
    }

    public void setShabbatToHour(int shabbatToHour) {
        this.shabbatToHour = shabbatToHour;
    }

    public void setShabbatFromMin(int shabbatFromMin) {
        this.shabbatFromMin = shabbatFromMin;
    }

    public void setShabbatToMin(int shabbatToMin) {
        this.shabbatToMin = shabbatToMin;
    }

    public float getHourlyWage() {
        return hourlyWage;
    }

    public float getProvidentFund() {
        return providentFund;
    }

    public float getAdvancedStudyFund() {
        return advancedStudyFund;
    }

    public float getCredits() {
        return credits;
    }

    public int getStartDate() {
        return startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public int getShabbatFromHour() {
        return shabbatFromHour;
    }

    public int getShabbatToHour() {
        return shabbatToHour;
    }

    public int getShabbatFromMin() {
        return shabbatFromMin;
    }

    public int getShabbatToMin() {
        return shabbatToMin;
    }
}
