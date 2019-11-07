package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class PaycheckActivity extends AppCompatActivity {

    private User user;
    private String key;
    private Spinner year_spinner, month_spinner;
    private GridLayout gridLayout;
    private TextView txt_baseWage, txt_travelFee, txt_grossWage, txt_nationalInsurance,
                     txt_incomeTax, txt_healthInsurance, txt_netWage,
                     output_baseWage, output_travelFee, output_grossWage, output_nationalInsurance,
                     output_incomeTax, output_healthInsurance, output_netWage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paycheck);
        user = (User) getIntent().getSerializableExtra("user");

        year_spinner = findViewById(R.id.paycheck_year_spinner);
        month_spinner = findViewById(R.id.paycheck_month_spinner);

        String[] years = user.getYearsPaychecks();
        String[] months = user.getMonthsPaychecks();

        ArrayAdapter<String> yearsAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, years);
        year_spinner.setAdapter(yearsAdapter);

        ArrayAdapter<String> monthsAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, months);
        month_spinner.setAdapter(monthsAdapter);

        gridLayout = findViewById(R.id.paycheck_grid);

        String[] texts = {"Base Wage", "Travel Fee", "Gross Wage", "National Insurance", "Income Tax", "Health Insurance", "Net Wage"};
        TextView textView1 = new TextView(getApplicationContext()),
                 textView2 = new TextView(getApplicationContext());

        key = month_spinner.getSelectedItem().toString() + "#" + year_spinner.getSelectedItem().toString();

        for(int i=0; i<texts.length; i++) {
            textView1.setText(texts[i]);
            gridLayout.addView(textView1);
            switch(i) {
                case 0: {
                    textView2.setText(String.valueOf(user.getPaychecks().get(key).getBaseWage()));
                    textView2.setTextColor(Color.GREEN);
                }
                case 1: {
                    textView2.setText(String.valueOf(user.getPaychecks().get(key).getTravelFee()));
                    textView2.setTextColor(Color.GREEN);
                }
                case 2: {
                    textView2.setText(String.valueOf(user.getPaychecks().get(key).getGrossWage()));
                    textView2.setTextColor(Color.GREEN);
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

            gridLayout.addView(textView2);

        }

    }
}
