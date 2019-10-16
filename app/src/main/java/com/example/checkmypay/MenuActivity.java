package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


    }

    // check this function
    public void goToActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }// check

    public void goToSetting() {
        Intent intent = new Intent(this, SettingActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    public void goToRate() {
        Intent intent = new Intent(this, RateActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    public void goToPaycheck() {
        Intent intent = new Intent(this, PaycheckActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    public void goToSalary() {
        Intent intent = new Intent(this, SalaryActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
