package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private HashMap<String,Button> buttons;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // get user from other activities
        user = (User) getIntent().getSerializableExtra("user");

        //create Linear and Buttons
        LinearLayout mainLayout = findViewById(R.id.main_layout);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        buttons = new HashMap<>();
        createButtons();
        mainLayout.addView(linearLayout);

    }


    private void createButtons() {

        buttons.put("My Paycheck",new Button(this));
        buttons.put("My Salary",new Button(this));
        buttons.put("My Rate",new Button(this));
        //buttons.put("My Setting",new Button(this));

        buttons.get("My Paycheck").setText("My Paycheck");
        buttons.get("My Salary").setText("My Salary");
        buttons.get("My Rate").setText("My Rate");
        //buttons.get("My Setting").setText("My Setting");

        for (Button button : buttons.values())
        {
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,10,10);
            button.setLayoutParams(params);
            button.setAllCaps(false);
            button.setOnClickListener(this);
            button.setBackgroundResource(R.color.colorPrimary);
            button.setTextColor(getResources().getColor(R.color.white));
            // TODO: set font for each button
            linearLayout.addView(button);
        }

    }

    public void goToActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

/*
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
    }*/

    @Override
    public void onClick(View view) {
        if(view instanceof Button) {
            Button clickedButton = (Button) view;
            if (clickedButton == buttons.get("My Paycheck")){
                goToActivity(PaycheckActivity.class);
            } if (clickedButton == buttons.get("My Salary")) {
                goToActivity(SalaryActivity.class);
            } if (clickedButton == buttons.get("My Rate")) {
                goToActivity(RateActivity.class);
            }
        }
    }
}
