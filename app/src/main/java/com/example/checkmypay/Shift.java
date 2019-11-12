package com.example.checkmypay;

class Shift {

    private String day, month, beginHour, endHour, beginMinute, endMinute;
    private String totalHours, shiftProfit;
    private User user;

    public Shift() {
    }

    public Shift(User user, String day, String month, String beginHour, String endHour, String beginMinute, String endMinute) {
        this.day = day;
        this.month = month;
        this.beginHour = beginHour;
        this.endHour = endHour;
        this.beginMinute = beginMinute;
        this.endMinute = endMinute;
        this.user = user;
        setTotalHours();
        setShiftProfit();
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

    public void setTotalHours() {

        String diffHour = String.valueOf(Integer.parseInt(this.endHour) - Integer.parseInt(this.beginHour));
        String diffMinute = String.valueOf(Integer.parseInt(this.endMinute) - Integer.parseInt(this.beginMinute));

        this.totalHours = diffHour + ":" + diffMinute;
    }

    public void setShiftProfit() {

        String minute = this.totalHours.split(":")[1];
        float minuteToDecimal = Float.parseFloat(minute) / 60;
        String hour = this.totalHours.split(":")[0];
        this.shiftProfit = String.valueOf((Float.parseFloat(hour) + minuteToDecimal) * this.user.getHourlyWage());
    }

}
