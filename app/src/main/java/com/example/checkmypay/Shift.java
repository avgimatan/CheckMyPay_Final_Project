package com.example.checkmypay;

class Shift {

    private int day, month, beginHour, endHour, beginMinute, endMinute;
    private float totalHours, shiftProfit;

    // not full constructor
    public Shift(int day, int month, int beginHour, int endHour,
                 int beginMinute, int endMinute, float totalHours) {
        this.day = day;
        this.month = month;
        this.beginHour = beginHour;
        this.endHour = endHour;
        this.beginMinute = beginMinute;
        this.endMinute = endMinute;
        this.totalHours = totalHours;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setBeginHour(int beginHour) {
        this.beginHour = beginHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public void setBeginMinute(int beginMinute) {
        this.beginMinute = beginMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public void setTotalHours(float totalHours) {
        this.totalHours = totalHours;
    }

    // calc the profit fore each shift using calender
    public void setShiftProfit(float shiftProfit) {
        this.shiftProfit = shiftProfit;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getBeginHour() {
        return beginHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getBeginMinute() {
        return beginMinute;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public float getTotalHours() {
        return totalHours;
    }

    public float getShiftProfit() {
        return shiftProfit;
    }
}
