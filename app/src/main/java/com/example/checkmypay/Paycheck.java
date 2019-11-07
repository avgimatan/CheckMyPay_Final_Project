package com.example.checkmypay;

/**
 * key: "month#year"
 */

class Paycheck {

    private String key;
    private float baseWage = 0, travelFee = 0, grossWage = 0, nationalInsurance = 0, incomeTax = 0 ,healthInsurance = 0, newWage = 0;

    // key: "month#year"
    public Paycheck(String key) {
        this.key = key;
    }

    public Paycheck(String key, float baseWage, float travelFee, float grossWage, float nationalInsurance,
                    float incomeTax, float healthInsurance, float newWage) {
        this.key = key;
        this.baseWage = baseWage;
        this.travelFee = travelFee;
        this.grossWage = grossWage;
        this.nationalInsurance = nationalInsurance;
        this.incomeTax = incomeTax;
        this.healthInsurance = healthInsurance;
        this.newWage = newWage;
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

    public void setNationalInsurance(float nationalInsurance) {
        this.nationalInsurance = nationalInsurance;
    }

    public void setIncomeTax(float incomeTax) {
        this.incomeTax = incomeTax;
    }

    public void setHealthInsurance(float healthInsurance) {
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
