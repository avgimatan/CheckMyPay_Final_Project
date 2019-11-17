package com.example.checkmypay;

import java.io.Serializable;
import java.util.Calendar;

class Shift implements Serializable, Finals {

    private String day, month, beginHour, endHour, beginMinute, endMinute;
    private String totalHours, shiftProfit;
    private boolean isHoliday;
    private String hourlyWage, shabbatFromHour, shabbatToHour, shabbatFromMin, shabbatToMin;

    public Shift() {
    }

    // Start shift
    public Shift(String hourlyWage, String day, String month, String beginHour, String beginMinute) {
        this.hourlyWage = hourlyWage;
        this.day = day;
        this.month = month;
        this.beginHour = beginHour;
        this.beginMinute = beginMinute;
    }

    public Shift(String hourlyWage, String day, String month, String beginHour, String endHour, String beginMinute, String endMinute,User user) {
        this.hourlyWage = hourlyWage;
        this.day = day;
        this.month = month;
        this.beginHour = beginHour;
        this.endHour = endHour;
        this.beginMinute = beginMinute;
        this.endMinute = endMinute;
        setTotalHours();
        setShiftProfit();
        this.isHoliday = false;
        this.shabbatFromHour = String.valueOf(user.getShabbatFromHour());
        this.shabbatToMin = String.valueOf(user.getShabbatToMin());
        this.shabbatFromMin = String.valueOf(user.getShabbatFromMin());
        this.shabbatToHour = String.valueOf(user.getShabbatToHour());
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

    public String getHourlyWage() {
        return hourlyWage;
    }

    public boolean isHoliday() {
        return isHoliday;
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

    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }

    public void setHourlyWage(String hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public void setTotalHours() {

        String diffHour = String.valueOf(Integer.parseInt(this.endHour) - Integer.parseInt(this.beginHour));
        String diffMinute = String.valueOf(Integer.parseInt(this.endMinute) - Integer.parseInt(this.beginMinute));

        this.totalHours = diffHour + ":" + diffMinute;
    }

    public void setShiftProfit() {

        float minuteToDecimal = Float.parseFloat(this.totalHours.split(":")[1]) / 60;
        float hourToDecimal = Float.parseFloat(this.totalHours.split(":")[0]);
        float totalDecimal = hourToDecimal + minuteToDecimal;
        float hourlyWageDecimal = Float.parseFloat(this.hourlyWage);

        Calendar c = Calendar.getInstance();
        int currentDay = c.get(Calendar.DAY_OF_WEEK);
        float shabbatFromHourDecimal = Float.parseFloat(this.shabbatFromHour);
        float shabbatFromMinuteDecimal = Float.parseFloat(this.shabbatFromMin);
        float beginHourDecimal = Float.parseFloat(this.beginHour);
        float beginMinuteDecimal = Float.parseFloat(this.beginMinute);
        float diffBeginShabbatHours = (shabbatFromHourDecimal + shabbatFromMinuteDecimal) - (beginHourDecimal + beginMinuteDecimal);
        //float numOfExtraHours;
        // check shabbat hours
        if (currentDay == 6 ) { // Friday

            if (diffBeginShabbatHours <= totalDecimal) {

                if (totalDecimal < 8)
                    this.shiftProfit = String.valueOf(diffBeginShabbatHours * hourlyWageDecimal
                            + (totalDecimal - diffBeginShabbatHours) * SECOND_EXTRA_HOURS * hourlyWageDecimal);
                else if (totalDecimal > 8 && totalDecimal <= 10)
                    this.shiftProfit = String.valueOf(diffBeginShabbatHours * hourlyWageDecimal
                            + (8 - diffBeginShabbatHours) * hourlyWageDecimal * SECOND_EXTRA_HOURS
                            + (totalDecimal - 8) * hourlyWageDecimal * FIRST_SHABAT_EXTRA_HOURS);
                else if (totalDecimal > 10)
                    this.shiftProfit = String.valueOf(diffBeginShabbatHours * hourlyWageDecimal
                            + (8 - diffBeginShabbatHours) * hourlyWageDecimal * SECOND_EXTRA_HOURS
                            + 2 * hourlyWageDecimal * FIRST_SHABAT_EXTRA_HOURS
                            + (totalDecimal - 10) * hourlyWageDecimal * SECOND_SHABAT_EXTRA_HOURS);
            } else if (beginHourDecimal >= shabbatFromHourDecimal ) {

            }

        }

        // night shift condition
        if ( (hourToDecimal < 22 && (22 - hourToDecimal) > 2) || hourToDecimal > 22 ) {
            //numOfExtraHours = totalDecimal -7;
            if(isHoliday) {
                if (totalDecimal < 7)
                    this.shiftProfit = String.valueOf(hourlyWageDecimal * totalDecimal * SECOND_EXTRA_HOURS);
                else if (totalDecimal > 7 && totalDecimal <= 9)
                    this.shiftProfit = String.valueOf(hourlyWageDecimal * 7 * SECOND_EXTRA_HOURS
                            + (totalDecimal - 7) * FIRST_SHABAT_EXTRA_HOURS * hourlyWageDecimal);
                else if (totalDecimal > 9)
                    this.shiftProfit = String.valueOf(hourlyWageDecimal * 7 * SECOND_EXTRA_HOURS
                            + hourlyWageDecimal * 2 * FIRST_SHABAT_EXTRA_HOURS
                            + hourlyWageDecimal * (totalDecimal - 9) * SECOND_SHABAT_EXTRA_HOURS);
            }else {
                if (totalDecimal < 7)
                    this.shiftProfit = String.valueOf(hourlyWageDecimal * totalDecimal);
                else if (totalDecimal > 7 && totalDecimal <= 9)
                    this.shiftProfit = String.valueOf(hourlyWageDecimal * 7
                            + (totalDecimal - 7) * FIRST_EXTRA_HOURS * hourlyWageDecimal);
                else if (totalDecimal > 9)
                    this.shiftProfit = String.valueOf(hourlyWageDecimal * 7
                            + hourlyWageDecimal * 2 * FIRST_EXTRA_HOURS
                            + hourlyWageDecimal * (totalDecimal - 9) * SECOND_EXTRA_HOURS);
            }


        } else { // day shift

            if (isHoliday) {

                if (totalDecimal < 8)
                    this.shiftProfit = String.valueOf(hourlyWageDecimal * totalDecimal * SECOND_EXTRA_HOURS);
                else if (totalDecimal > 8 && totalDecimal <= 10)
                    this.shiftProfit = String.valueOf(hourlyWageDecimal * 8 * SECOND_EXTRA_HOURS
                            + (totalDecimal - 8) * FIRST_SHABAT_EXTRA_HOURS * hourlyWageDecimal);
                else if (totalDecimal > 10)
                    this.shiftProfit = String.valueOf(hourlyWageDecimal * 8 * SECOND_EXTRA_HOURS
                            + hourlyWageDecimal * 2 * FIRST_SHABAT_EXTRA_HOURS
                            + hourlyWageDecimal * (totalDecimal - 10) * SECOND_SHABAT_EXTRA_HOURS);

            } else { // calc regular days with extra hours

                if (totalDecimal < 8)
                    this.shiftProfit = String.valueOf(hourlyWageDecimal * totalDecimal);
                else if (totalDecimal > 8 && totalDecimal <= 10)
                    this.shiftProfit = String.valueOf(hourlyWageDecimal * 8
                            + (totalDecimal - 8) * FIRST_EXTRA_HOURS * hourlyWageDecimal);
                else if (totalDecimal > 10)
                    this.shiftProfit = String.valueOf(hourlyWageDecimal * 8
                            + hourlyWageDecimal * 2 * FIRST_EXTRA_HOURS
                            + hourlyWageDecimal * (totalDecimal - 10) * SECOND_EXTRA_HOURS);
            }


        }

    }


    public void calcHolidayProfit(float dayNightHours, float totalDecimal, float hourlyWageDecimal) {

        if (totalDecimal < dayNightHours)
            this.shiftProfit = String.valueOf(hourlyWageDecimal * totalDecimal * SECOND_EXTRA_HOURS);
        else if (totalDecimal > dayNightHours && totalDecimal <= dayNightHours + 2)
            this.shiftProfit = String.valueOf(hourlyWageDecimal * dayNightHours * SECOND_EXTRA_HOURS
                    + (totalDecimal - dayNightHours) * FIRST_SHABAT_EXTRA_HOURS * hourlyWageDecimal);
        else if (totalDecimal > dayNightHours + 2)
            this.shiftProfit = String.valueOf(hourlyWageDecimal * dayNightHours * SECOND_EXTRA_HOURS
                    + hourlyWageDecimal * 2 * FIRST_SHABAT_EXTRA_HOURS
                    + hourlyWageDecimal * (totalDecimal - dayNightHours + 2) * SECOND_SHABAT_EXTRA_HOURS);


    }

}
