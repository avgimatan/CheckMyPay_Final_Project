package com.example.checkmypay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

// user json:
/**
 *      email
 *      password
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
 *
 *      ArrayList <Shift> shifts
 *      Map <String(Date month and year), Paycheck> paychecks
 */

public class User implements Serializable {
    private String email, password;
    private float hourlyWage, providentFund, advancedStudyFund, credits, travelFee;
    private int startDate, endDate, shabbatFromHour, shabbatToHour, shabbatFromMin, shabbatToMin;
    private ArrayList<Shift> shifts;
    private Map<String, Paycheck> paychecks;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Constructor without paychecks and shifts
    public User(String email, String password, float hourlyWage, float providentFund, float advancedStudyFund,
                float credits, int startDate, int endDate, int shabbatFromHour, int shabbatToHour, int shabbatFromMin,
                int shabbatToMin, float travelFee) {
        this.email = email;
        this.password = password;
        this.hourlyWage = hourlyWage;
        this.providentFund = providentFund;
        this.advancedStudyFund = advancedStudyFund;
        this.credits = credits;
        this.startDate = startDate;
        this.endDate = endDate;
        this.shabbatFromHour = shabbatFromHour;
        this.shabbatToHour = shabbatToHour;
        this.shabbatFromMin = shabbatFromMin;
        this.shabbatToMin = shabbatToMin;
        this.travelFee = travelFee;
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

    public void setTravelFee(float travelFee) {
        this.travelFee = travelFee;
    }

    public void setShifts(ArrayList<Shift> shifts) {
        this.shifts = shifts;
    }

    public void setPaychecks(Map<String, Paycheck> paychecks) {
        this.paychecks = paychecks;
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

    public float getTravelFee() {
        return travelFee;
    }

    public ArrayList<Shift> getShifts() {
        return shifts;
    }

    public Map<String, Paycheck> getPaychecks() {
        return paychecks;
    }
}
