package com.example.checkmypay;

import java.io.Serializable;
import java.util.Calendar;


class Shift implements Serializable, Finals {

    private String day, month, beginHour, endHour, beginMinute, endMinute;
    private String totalHours, shiftProfit;
    private boolean isHoliday;
    private float hourlyWage, shabbatFromHour, shabbatToHour, shabbatFromMin, shabbatToMin;

    public Shift() {
    }

    // Start shift
    public Shift(float hourlyWage, String day, String month, String beginHour, String beginMinute) {
        this.hourlyWage = hourlyWage;
        this.day = day;
        this.month = month;
        this.beginHour = beginHour;
        this.beginMinute = beginMinute;
    }

    public Shift(float hourlyWage, String day, String month, String beginHour, String endHour, String beginMinute, String endMinute,User user) {
        this.hourlyWage = hourlyWage;
        this.day = day;
        this.month = month;
        this.beginHour = beginHour;
        this.endHour = endHour;
        this.beginMinute = beginMinute;
        this.endMinute = endMinute;
        this.isHoliday = false;
        this.shabbatFromHour = user.getShabbatFromHour();
        this.shabbatToMin = user.getShabbatToMin();
        this.shabbatFromMin = user.getShabbatFromMin();
        this.shabbatToHour = user.getShabbatToHour();
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

    public float getHourlyWage() {
        return hourlyWage;
    }

    public float getShabbatFromHour() {
        return shabbatFromHour;
    }

    public float getShabbatToHour() {
        return shabbatToHour;
    }

    public float getShabbatFromMin() {
        return shabbatFromMin;
    }

    public float getShabbatToMin() {
        return shabbatToMin;
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

    public void setHourlyWage(float hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public void setTotalHours() {

        int endHourDecimal = Integer.parseInt(this.endHour);
        int beginHourDecimal = Integer.parseInt(this.beginHour);
        if (endHourDecimal < beginHourDecimal) {
            endHourDecimal+=24;
        }
        String diffHour = String.valueOf(endHourDecimal - beginHourDecimal);
        String diffMinute = String.valueOf(Integer.parseInt(this.endMinute) - Integer.parseInt(this.beginMinute));

        this.totalHours = diffHour + ":" + diffMinute;
    }

    public void setShiftProfit() {

        float hourToDecimal = Float.parseFloat(this.totalHours.split(":")[0]);
        float minuteToDecimal = Float.parseFloat(this.totalHours.split(":")[1]) / 60;
        float totalDecimal = hourToDecimal + minuteToDecimal;

        float shabbatFromTime = this.shabbatFromHour + (this.shabbatFromMin/60);
        //float shabbatToTime = this.shabbatToHour + (this.shabbatToMin/60);

        float beginHourDecimal = Float.parseFloat(this.beginHour);
        float beginMinuteDecimal = Float.parseFloat(this.beginMinute);
        float beginTimeDecimal = beginHourDecimal + (beginMinuteDecimal/60);

        float endHourDecimal = Float.parseFloat(this.endHour);
        //float endMinuteDecimal = Float.parseFloat(this.endMinute);
        if (endHourDecimal < beginHourDecimal) {
            endHourDecimal+=24;
        }
        //float endTimeDecimal = endHourDecimal + endMinuteDecimal;



        Calendar shiftCalendar = Calendar.getInstance();
        //Calendar shabbatToHourCalendar = Calendar.getInstance();
        //Calendar endHourCalendar = Calendar.getInstance();
        // set the day of the shift
        shiftCalendar.set(Calendar.YEAR, Integer.parseInt(this.month), Integer.parseInt(this.day));
        // set the day of the shift with shabbat finish hour
        //shabbatToHourCalendar.set(Calendar.YEAR, Integer.parseInt(this.month), Integer.parseInt(this.day),(int)shabbatToHourDecimal,(int)shabbatToMinuteDecimal);
        int shabbatDay = shiftCalendar.get(Calendar.DAY_OF_WEEK);

        float diffBeginShabbatHours = shabbatFromTime - beginTimeDecimal;
        float diffEndShabbatHours = shabbatFromTime - beginTimeDecimal;

        // night shift condition
        if ( (beginHourDecimal < 22 && (22 - endHourDecimal) >= 2) || beginHourDecimal >= 22 ) {

            if (isHoliday) // calc Holiday
               calcHolidayProfit(NIGHT_HOURS, totalDecimal, this.hourlyWage);
            else if (shabbatDay == 5) // calc Friday
                calcBeginShabbatProfit(NIGHT_HOURS, totalDecimal, this.hourlyWage, diffBeginShabbatHours
                        , beginHourDecimal, this.shabbatFromHour, endHourDecimal);
            else if (shabbatDay == 6) // calc Saturday
                calcEndShabbatProfit(NIGHT_HOURS, totalDecimal, this.hourlyWage, diffEndShabbatHours,
                        beginHourDecimal, this.shabbatToHour, endHourDecimal);
            else // calc regular hours
                calcRegularProfit(NIGHT_HOURS, totalDecimal, this.hourlyWage);

        } else { // day shift

            if (isHoliday) // calc Holiday
                calcHolidayProfit(DAY_HOURS, totalDecimal, this.hourlyWage);
            else if (shabbatDay == 5) // calc Friday
                calcBeginShabbatProfit(DAY_HOURS, totalDecimal, this.hourlyWage, diffBeginShabbatHours
                        , beginHourDecimal, this.shabbatFromHour, endHourDecimal);
            else if (shabbatDay == 6) // calc Saturday
                calcEndShabbatProfit(DAY_HOURS, totalDecimal, this.hourlyWage, diffEndShabbatHours
                        , beginHourDecimal, this.shabbatToHour, endHourDecimal);
            else // calc regular days with extra hours
                calcRegularProfit(DAY_HOURS, totalDecimal, this.hourlyWage);
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

        if (diff < totalDecimal && diff >= 0) {
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

        if (diff < totalDecimal && diff >= 0) {
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
