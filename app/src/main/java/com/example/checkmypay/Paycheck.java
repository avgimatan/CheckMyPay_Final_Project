package com.example.checkmypay;

import java.io.Serializable;

/**
 * key: "month#year"
 */

class Paycheck implements Serializable, Finals {

    private String key;
    private float baseWage = 0, travelFee = 0, grossWage = 0, nationalInsurance = 0, incomeTax = 0 ,healthInsurance = 0, newWage = 0;

    public Paycheck() {
    }

    // key: "month#year"
    public Paycheck(String key) {
        this.key = key;
    }

    // key: "month#year"
    public Paycheck(String key, User user) {
        this.key = key;

        this.baseWage = 0;
        if(user.getShifts() != null) {
            for(Shift shift : user.getShifts())
                this.baseWage += Float.parseFloat(shift.getShiftProfit());
        }

        this.travelFee = user.getTravelFee();
        this.grossWage = this.travelFee + this.baseWage;

        setNationalInsurance();
        setIncomeTax();
        //this.healthInsurance = 0;
        setHealthInsurance();
        this.newWage = 0;
    }

    // key: "month#year"
    public void setKey(String key) {
        this.key = key;
    }

    public void setBaseWage(float baseWage) {
        this.baseWage = baseWage;
    }

    public void setTravelFee(float travelFee) {
        this.travelFee = travelFee;
    }

    public void setGrossWage(float grossWage) {
        this.grossWage = grossWage;
    }

    private void setNationalInsurance() {
        if(this.grossWage <= NATIONAL_INSURANCE_1)                                  // Level 1
            this.nationalInsurance = this.grossWage * NATIONAL_INSURANCE_1_PRECENT;
        else if(this.grossWage <= NATIONAL_INSURANCE_2)                             // Level 2
            this.nationalInsurance = this.grossWage * NATIONAL_INSURANCE_2_PRECENT;
        else                                                                        // Level 3
            this.nationalInsurance = this.grossWage * NATIONAL_INSURANCE_3_PRECENT;
    }

    private void setIncomeTax() {
        if(this.grossWage <= INCOME_TAX_LEVEL_1)                                    // Level 1
            this.incomeTax = this.grossWage * INCOME_TAX_LEVEL_1_PRECENT;
        else if(this.grossWage <= INCOME_TAX_LEVEL_2)                               // Level 2
            this.incomeTax = this.grossWage * INCOME_TAX_LEVEL_2_PRECENT;
        else if(this.grossWage <= INCOME_TAX_LEVEL_3)                               // Level 3
            this.incomeTax = this.grossWage * INCOME_TAX_LEVEL_3_PRECENT;
        else if(this.grossWage <= INCOME_TAX_LEVEL_4)                               // Level 4
            this.incomeTax = this.grossWage * INCOME_TAX_LEVEL_4_PRECENT;
        else if(this.grossWage <= INCOME_TAX_LEVEL_5)                               // Level 5
            this.incomeTax = this.grossWage * INCOME_TAX_LEVEL_5_PRECENT;
        else if(this.grossWage <= INCOME_TAX_LEVEL_6)                               // Level 6
            this.incomeTax = this.grossWage * INCOME_TAX_LEVEL_6_PRECENT;
        else                                                                        // Level 7
            this.incomeTax = this.grossWage * INCOME_TAX_LEVEL_7_PRECENT;
    }

    private void setHealthInsurance() {

        this.healthInsurance = healthInsurance;
    }

    public void setNewWage(float newWage) {
        this.newWage = newWage;
    }

    public String getKey() {
        return key;
    }

    public String getYear() {
        return this.key.split("#")[1];
    }

    public String getMonth() {
        return this.key.split("#")[0];
    }

    public float getBaseWage() {

        return baseWage;
    }

    public float getTravelFee() {
        return travelFee;
    }

    public float getGrossWage() {
        return grossWage;
    }

    public float getNationalInsurance() {
        return nationalInsurance;
    }

    public float getIncomeTax() {
        return incomeTax;
    }

    public float getHealthInsurance() {
        return healthInsurance;
    }

    public float getNewWage() {
        return newWage;
    }
}
