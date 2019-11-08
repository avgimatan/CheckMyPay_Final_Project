package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class PaycheckActivity extends AppCompatActivity {

    private User user;
    private String key = "";
    private Spinner year_spinner, month_spinner;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paycheck);
        user = (User) getIntent().getSerializableExtra("user");

        year_spinner = findViewById(R.id.paycheck_year_spinner);
        month_spinner = findViewById(R.id.paycheck_month_spinner);
        gridLayout = findViewById(R.id.paycheck_grid);

        String[] years = user.getYearsPaychecks();
        //String[] months = user.getMonthsPaychecks();
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        ArrayAdapter<String> yearsAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, years);
        year_spinner.setAdapter(yearsAdapter);

        ArrayAdapter<String> monthsAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, months);
        month_spinner.setAdapter(monthsAdapter);

        String[] texts = {"Base Wage", "Travel Fee", "Gross Wage", "National Insurance", "Income Tax", "Health Insurance", "Net Wage"};
        TextView textView1, textView2;

        // TODO: Define default value for the spinners
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int monthIndex = c.get(Calendar.MONTH);
        int yearIndex = c.get(Calendar.YEAR);

        // set default value to current month and current year
        month_spinner.setSelection(monthIndex);
        year_spinner.setSelection(yearsAdapter.getCount());

        if(!years[0].equals("empty"))
            key = (monthIndex + 1) + "#" + year_spinner.getSelectedItem().toString(); // TODO: maybe without  .toString()

        for(int i=0; i < texts.length; i++) {

            textView1 = new TextView(this);
            textView2 = new TextView(this);

            textView1.setText(texts[i]);
            gridLayout.addView(textView1);
            if(!key.equals("")) {
                switch(i) {
                    case 0: {
                        int baseWage = 0;
                        for(Shift sh : user.getShifts()) {
                            baseWage += sh.getShiftProfit();
                        }
                        textView2.setText(String.valueOf(baseWage));
                        //textView2.setText(String.valueOf(user.getPaychecks().get(key).getBaseWage()));
                        textView2.setTextColor(Color.GREEN);
                    }
                    case 1: {
                        textView2.setText(String.valueOf(user.getPaychecks().get(key).getTravelFee()));
                        textView2.setTextColor(Color.GREEN);
                    }
                    case 2: {
                        textView2.setText(String.valueOf(user.getPaychecks().get(key).getGrossWage()));
                        textView2.setTextColor(Color.BLUE);
                    }
                    case 3: {
                        textView2.setText(String.valueOf(user.getPaychecks().get(key).getNationalInsurance()));
                        textView2.setTextColor(Color.RED);
                    }
                    case 4: {
                        textView2.setText(String.valueOf(user.getPaychecks().get(key).getIncomeTax()));
                        textView2.setTextColor(Color.RED);
                    }
                    case 5: {
                        textView2.setText(String.valueOf(user.getPaychecks().get(key).getHealthInsurance()));
                        textView2.setTextColor(Color.RED);
                    }
                    case 6: {
                        textView2.setText(String.valueOf(user.getPaychecks().get(key).getNewWage()));
                        textView2.setTextColor(Color.BLUE);
                    }
                }
            }
            else {
                textView2.setText("No details");
                textView2.setTextColor(Color.GRAY);
            }

            gridLayout.addView(textView2);

        }

    }
}
