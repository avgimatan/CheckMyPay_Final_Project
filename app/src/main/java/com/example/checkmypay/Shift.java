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
        this.isHoliday = false;
        this.shabbatFromHour = String.valueOf(user.getShabbatFromHour());
        this.shabbatToMin = String.valueOf(user.getShabbatToMin());
        this.shabbatFromMin = String.valueOf(user.getShabbatFromMin());
        this.shabbatToHour = String.valueOf(user.getShabbatToHour());
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

        float shabbatToHourDecimal = Float.parseFloat(this.shabbatToHour);
        float shabbatToMinuteDecimal = Float.parseFloat(this.shabbatToMin);
        float endHourDecimal = Float.parseFloat(this.endHour);
        float endMinuteDecimal = Float.parseFloat(this.endMinute);
        float diffBeginShabbatHours = (shabbatFromHourDecimal + shabbatFromMinuteDecimal) - (beginHourDecimal + beginMinuteDecimal);
        float diffEndShabbatHours = (shabbatToHourDecimal + shabbatToMinuteDecimal) - (endHourDecimal + endMinuteDecimal);

        // night shift condition
        if ( (beginHourDecimal < 22 && (22 - beginHourDecimal) >= 2) || beginHourDecimal >= 22 ) {

            if (isHoliday) // calc Holiday
               calcHolidayProfit(NIGHT_HOURS, totalDecimal, hourlyWageDecimal);
            else if (currentDay == 6) // calc Friday
                calcBeginShabbatProfit(NIGHT_HOURS, totalDecimal, hourlyWageDecimal, diffBeginShabbatHours
                        , beginHourDecimal, shabbatFromHourDecimal, endHourDecimal);
            else if (currentDay == 7) // calc Saturday
                calcEndShabbatProfit(NIGHT_HOURS, totalDecimal, hourlyWageDecimal, diffEndShabbatHours,
                        beginHourDecimal, shabbatToHourDecimal, endHourDecimal);
            else // calc regular hours
                calcRegularProfit(NIGHT_HOURS, totalDecimal, hourlyWageDecimal);

        } else { // day shift

            if (isHoliday) // calc Holiday
                calcHolidayProfit(DAY_HOURS, totalDecimal, hourlyWageDecimal);
            else if (currentDay == 6) // calc Friday
                calcBeginShabbatProfit(DAY_HOURS, totalDecimal, hourlyWageDecimal, diffBeginShabbatHours
                        , beginHourDecimal, shabbatFromHourDecimal, endHourDecimal);
            else if (currentDay == 7) // calc Saturday
                calcEndShabbatProfit(DAY_HOURS, totalDecimal, hourlyWageDecimal, diffEndShabbatHours
                        , beginHourDecimal, shabbatToHourDecimal, endHourDecimal);
            else // calc regular days with extra hours
                calcRegularProfit(DAY_HOURS, totalDecimal, hourlyWageDecimal);
        }

    }

    public void calcHolidayProfit(float dayNightHours, float totalDecimal, float hourlyWageDecimal) {

        if (totalDecimal <= dayNightHours)
            this.shiftProfit = String.valueOf(hourlyWageDecimal * totalDecimal * SECOND_EXTRA_HOURS);
        else if (totalDecimal > dayNightHours && totalDecimal <= (dayNightHours + 2))
            this.shiftProfit = String.valueOf(hourlyWageDecimal * dayNightHours * SECOND_EXTRA_HOURS
                    + (totalDecimal - dayNightHours) * hourlyWageDecimal * FIRST_SHABAT_EXTRA_HOURS);
        else if (totalDecimal > (dayNightHours + 2) )
            this.shiftProfit = String.valueOf(hourlyWageDecimal * dayNightHours * SECOND_EXTRA_HOURS
                    + hourlyWageDecimal * 2 * FIRST_SHABAT_EXTRA_HOURS
                    + hourlyWageDecimal * (totalDecimal - (dayNightHours + 2) ) * SECOND_SHABAT_EXTRA_HOURS);


    }

    public void calcRegularProfit(float dayNightHours, float totalDecimal, float hourlyWageDecimal) {
        if (totalDecimal <= dayNightHours)
            this.shiftProfit = String.valueOf(hourlyWageDecimal * totalDecimal);
        else if (totalDecimal > dayNightHours && totalDecimal <= (dayNightHours + 2) )
            this.shiftProfit = String.valueOf(hourlyWageDecimal * dayNightHours
                    + (totalDecimal - dayNightHours) * FIRST_EXTRA_HOURS * hourlyWageDecimal);
        else if (totalDecimal > (dayNightHours + 2) )
            this.shiftProfit = String.valueOf(hourlyWageDecimal * dayNightHours
                    + hourlyWageDecimal * 2 * FIRST_EXTRA_HOURS
                    + hourlyWageDecimal * (totalDecimal - (dayNightHours + 2) ) * SECOND_EXTRA_HOURS);
    }

    public void calcBeginShabbatProfit(float dayNightHours, float totalDecimal, float hourlyWageDecimal, float diff,
                                  float beginHourDecimal, float shabbatFromHourDecimal, float endHourDecimal) {

        if (diff < totalDecimal) {
            if (totalDecimal <= dayNightHours)
                this.shiftProfit = String.valueOf(diff * hourlyWageDecimal
                        + (totalDecimal - diff) * SECOND_EXTRA_HOURS * hourlyWageDecimal);
            else if (totalDecimal > dayNightHours && totalDecimal <= (dayNightHours + 2) )
                this.shiftProfit = String.valueOf(diff * hourlyWageDecimal
                        + (dayNightHours - diff) * hourlyWageDecimal * SECOND_EXTRA_HOURS
                        + (totalDecimal - dayNightHours) * hourlyWageDecimal * FIRST_SHABAT_EXTRA_HOURS);
            else if (totalDecimal > (dayNightHours + 2) )
                this.shiftProfit = String.valueOf(diff * hourlyWageDecimal
                        + (dayNightHours - diff) * hourlyWageDecimal * SECOND_EXTRA_HOURS
                        + 2 * hourlyWageDecimal * FIRST_SHABAT_EXTRA_HOURS
                        + (totalDecimal - (dayNightHours + 2) ) * hourlyWageDecimal * SECOND_SHABAT_EXTRA_HOURS);
        }
        else if (beginHourDecimal >= shabbatFromHourDecimal ) {
            calcHolidayProfit(NIGHT_HOURS, totalDecimal, hourlyWageDecimal);
        }
        else if (endHourDecimal <= shabbatFromHourDecimal) {
            calcRegularProfit(DAY_HOURS, totalDecimal, hourlyWageDecimal);
        }
    }

    public void calcEndShabbatProfit(float dayNightHours, float totalDecimal, float hourlyWageDecimal, float diff,
                                       float beginHourDecimal, float shabbatToHourDecimal, float endHourDecimal) {

        if (diff < totalDecimal) {
            if (totalDecimal <= dayNightHours)
                this.shiftProfit = String.valueOf(diff * hourlyWageDecimal * SECOND_EXTRA_HOURS
                        + (totalDecimal - diff) * hourlyWageDecimal);
            else if (totalDecimal > dayNightHours && totalDecimal <= (dayNightHours + 2) )
                this.shiftProfit = String.valueOf(diff * hourlyWageDecimal * SECOND_EXTRA_HOURS
                        + (dayNightHours - diff) * hourlyWageDecimal
                        + (totalDecimal - dayNightHours) * hourlyWageDecimal * FIRST_EXTRA_HOURS);
            else if (totalDecimal > (dayNightHours + 2) )
                this.shiftProfit = String.valueOf(diff * hourlyWageDecimal * SECOND_EXTRA_HOURS
                        + (dayNightHours - diff) * hourlyWageDecimal
                        + 2 * hourlyWageDecimal * FIRST_EXTRA_HOURS
                        + (totalDecimal - (dayNightHours + 2) ) * hourlyWageDecimal * SECOND_EXTRA_HOURS);

        }
        else if (beginHourDecimal >= shabbatToHourDecimal ) {
            calcRegularProfit(NIGHT_HOURS,totalDecimal,hourlyWageDecimal);
        }
        else if (endHourDecimal <= shabbatToHourDecimal ) {
            calcHolidayProfit(DAY_HOURS, totalDecimal, hourlyWageDecimal);
        }
    }

}
