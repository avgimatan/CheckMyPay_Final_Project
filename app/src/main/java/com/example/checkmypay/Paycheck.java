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
            for(Shift shift : user.getShifts()) {
                if(shift.getShiftProfit() != null)
                    this.baseWage += Float.parseFloat(shift.getShiftProfit());
            }
        }

        this.travelFee = user.getTravelFee();
        this.grossWage = this.travelFee + this.baseWage;

        setNationalInsurance();
        setIncomeTax();
        setHealthInsurance();
        setNewWage(user.getCredits());
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
        if(this.grossWage <= INCOME_TAX_1)                                    // Level 1
            this.incomeTax = this.grossWage * INCOME_TAX_1_PRECENT;
        else if(this.grossWage <= INCOME_TAX_2)                               // Level 2
            this.incomeTax = this.grossWage * INCOME_TAX_2_PRECENT;
        else if(this.grossWage <= INCOME_TAX_3)                               // Level 3
            this.incomeTax = this.grossWage * INCOME_TAX_3_PRECENT;
        else if(this.grossWage <= INCOME_TAX_4)                               // Level 4
            this.incomeTax = this.grossWage * INCOME_TAX_4_PRECENT;
        else if(this.grossWage <= INCOME_TAX_5)                               // Level 5
            this.incomeTax = this.grossWage * INCOME_TAX_5_PRECENT;
        else if(this.grossWage <= INCOME_TAX_6)                               // Level 6
            this.incomeTax = this.grossWage * INCOME_TAX_6_PRECENT;
        else                                                                  // Level 7
            this.incomeTax = this.grossWage * INCOME_TAX_7_PRECENT;
    }

    private void setHealthInsurance() {
        if(this.grossWage <= HEALTH_INSURANCE_1)                                  // Level 1
            this.healthInsurance = this.grossWage * HEALTH_INSURANCE_1_PRECENT;
        else if(this.grossWage <= HEALTH_INSURANCE_2)
            this.healthInsurance = this.grossWage * HEALTH_INSURANCE_2_PRECENT;
        else
            this.healthInsurance = this.grossWage * HEALTH_INSURANCE_3_PRECENT;

    }

    private void setNewWage(float credit) {
        if( (credit*CREDIT_VALUE) >= this.incomeTax )
            this.newWage = this.grossWage - this.nationalInsurance - this.healthInsurance;
        else
            this.newWage = this.grossWage - this.nationalInsurance - this.healthInsurance - (this.incomeTax - (credit*CREDIT_VALUE));
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
