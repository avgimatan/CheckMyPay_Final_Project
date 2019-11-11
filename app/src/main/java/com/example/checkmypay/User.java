package com.example.checkmypay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// user json:

/**
 * email
 * password
 * hourlyWage
 * startDate
 * endDate
 * fromHour
 * fromMinute
 * toHour
 * toMinute
 * providentFund
 * advancedStudyFund
 * credits
 * <p>
 * ArrayList <Shift> shifts
 * Map <String(Date month and year), Paycheck> paychecks
 */

// TODO: Remeber every new paycheck (new month) initialize the shifts !!

public class User implements Serializable {
    private String id;
    private String email, password;
    private float hourlyWage, providentFund, advancedStudyFund, credits, travelFee;
    private int startDate, endDate, shabbatFromHour, shabbatToHour, shabbatFromMin, shabbatToMin;
    private ArrayList<Shift> shifts;
    private Map<String, Paycheck> paychecks;
    private Paycheck currentPaycheck;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        this.currentPaycheck = new Paycheck(month + "#" + year);
        this.hourlyWage = 0;
        this.providentFund = 0;
        this.advancedStudyFund = 0;
        this.credits = 0;
        this.travelFee = 0;
        this.startDate = 0;
        this.endDate = 0;
        this.shabbatFromHour = 0;
        this.shabbatToHour = 0;
        this.shabbatFromMin = 0;
        this.shabbatToMin = 0;
        this.shifts = new ArrayList<>();
        this.paychecks = new HashMap<String, Paycheck>();

    }


    public User(String id, String email, String password, float hourlyWage, float travelFee, float providentFund, float advancedStudyFund,
                float credits, int startDate, int endDate, int shabbatFromHour, int shabbatToHour, int shabbatFromMin,
                int shabbatToMin, ArrayList<Shift> shifts, Map<String, Paycheck> paychecks) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.hourlyWage = hourlyWage;
        this.travelFee = travelFee;
        this.providentFund = providentFund;
        this.advancedStudyFund = advancedStudyFund;
        this.credits = credits;
        this.startDate = startDate;
        this.endDate = endDate;
        this.shabbatFromHour = shabbatFromHour;
        this.shabbatToHour = shabbatToHour;
        this.shabbatFromMin = shabbatFromMin;
        this.shabbatToMin = shabbatToMin;
        this.shifts = shifts;
        this.paychecks = paychecks;

        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);


        this.currentPaycheck = new Paycheck(month + "#" + year);
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

    public void setCurrentPaycheck(Paycheck currentPaycheck) {
        this.currentPaycheck = currentPaycheck;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    // TODO: check it
    public ArrayList<Shift> getShifts() {
        //if(this.shifts != null)
        return shifts;
        //else
        //return new ArrayList<Shift>();
    }

    public Paycheck getCurrentPaycheck() {
        return currentPaycheck;
    }

    public Map<String, Paycheck> getPaychecks() {
        return paychecks;
    }

    // TODO: month can not be duplicate
    public List<String> getMonthsPaychecks() {
        List<String> months = new ArrayList<>();       // = new String[this.paychecks.size()];
        List<String> keys;
        if (this.paychecks != null) {
            keys = new ArrayList<>(this.paychecks.keySet());
            for (String s : keys) {
                months.add(s.split("#")[0]);
            }
        } else
            months.add("empty");
        return months;
    }

    public List<String> getYearsPaychecks() {
        List<String> years = new ArrayList<>();
        List<String> keys;
        if (this.paychecks != null) {
            keys = new ArrayList<>(this.paychecks.keySet());
            for (String s : keys) {
                years.add(s.split("#")[1]);
            }
        } else {
            years.add("empty");
        }
        return years;
    }

    public List<String> getMonthPaychecks() {
        List<String> months = new ArrayList<>();
        List<String> keys;
        if (this.paychecks != null) {
            keys = new ArrayList<>(this.paychecks.keySet());
            for (String s : keys) {
                months.add(s.split("#")[0]);
            }
        } else {
            months.add("empty");
        }
        return months;
    }
}