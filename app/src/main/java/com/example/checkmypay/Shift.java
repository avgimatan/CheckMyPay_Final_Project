package com.example.checkmypay;

class Shift {

    private String day, month, beginHour, endHour, beginMinute, endMinute;
    private String totalHours, shiftProfit;

    public Shift() {
    }

    public Shift(String day, String month, String beginHour, String endHour, String beginMinute, String endMinute, String totalHours, String shiftProfit) {
        this.day = day;
        this.month = month;
        this.beginHour = beginHour;
        this.endHour = endHour;
        this.beginMinute = beginMinute;
        this.endMinute = endMinute;
        this.totalHours = totalHours;
        this.shiftProfit = shiftProfit;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getBeginHour() {
        return beginHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public String getBeginMinute() {
        return beginMinute;
    }

    public String getEndMinute() {
        return endMinute;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public String getShiftProfit() {
        return shiftProfit;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setBeginHour(String beginHour) {
        this.beginHour = beginHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public void setBeginMinute(String beginMinute) {
        this.beginMinute = beginMinute;
    }

    public void setEndMinute(String endMinute) {
        this.endMinute = endMinute;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }

    public void setShiftProfit(String shiftProfit) {
        this.shiftProfit = shiftProfit;
    }
}
