package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.lang.Object;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PaycheckActivity extends AppCompatActivity {

    private User user;
    private String key = "";
    private Paycheck paycheck;
    boolean isCurrentPaycheck;
    private Spinner year_spinner, month_spinner;
    private LinearLayout linearLayout;
    private GridLayout gridLayout1, gridLayout2;
    private TextView txt_title, txt_year, txt_month;
    //private String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private List<String> years, months;
    private String[] texts = {"Base Wage", "Travel Fee", "National Insurance", "Income Tax", "Health Insurance", "Gross Wage", "Net Wage"};
    private TextView textView1, textView2, txt_temp;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paycheck);
        user = (User) getIntent().getSerializableExtra("user");

        linearLayout = findViewById(R.id.paycheck_main_linearLayout);
        //txt_temp = findViewById(R.id.paycheck_temp);

        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH);
        int currentYear = c.get(Calendar.YEAR);

        // txt_title
        /*txt_title = new TextView(getApplicationContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
        txt_title.setLayoutParams(params);
        txt_title.setText("My Paycheck");
        txt_title.setGravity(Gravity.CENTER);
        txt_title.setTextColor(getResources().getColor(R.color.colorPrimary));
        //txt_title.setTextSize(40); //txt_title.setTextSize(R.dimen.app_icon_size);
        txt_title.setTextAppearance(this, android.R.style.TextAppearance_Large);
        //txt_title.setTextSize( R.dimen.app_icon_size);
        txt_title.setTypeface(Typeface.create("casual", Typeface.NORMAL));
        linearLayout.addView(txt_title);*/

        // gridLayout1
        gridLayout1 = new GridLayout(getApplicationContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
        gridLayout1.setLayoutParams(params);
        gridLayout1.setRowCount(2);
        gridLayout1.setColumnCount(2);

        txt_year = new TextView(getApplicationContext());
        params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
        txt_year.setLayoutParams(params);
        txt_year.setText("Year: ");
        txt_year.setTypeface(Typeface.create("casual", Typeface.NORMAL));
        txt_year.setTextColor(getResources().getColor(R.color.colorPrimary));
        //txt_title.setTextSize(R.dimen.app_icon_size);
        //txt_year.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Medium);
        // missing: android:fontFamily="casual"
        gridLayout1.addView(txt_year);


        // year_spinner
        year_spinner = new Spinner(getApplicationContext());
        ViewGroup.MarginLayoutParams pmarginParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
        pmarginParams.setMargins(50, 0, 100, 0);
        year_spinner.setLayoutParams(params);
        //year_spinner.setBackgroundResource(17301510); //  17301510 = btn_dropdown
        year_spinner.setLayoutMode(1); // 1 = dropdown
        years = user.getYearsPaychecks();
        ArrayAdapter yearsAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, years);
        year_spinner.setAdapter(yearsAdapter);
        // set default value to current current year
        year_spinner.setSelection(yearsAdapter.getCount() - 1);
        gridLayout1.addView(year_spinner);

        // txt_month
        txt_month = new TextView(getApplicationContext());
        params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
        txt_month.setLayoutParams(params);
        txt_month.setTypeface(Typeface.create("casual", Typeface.NORMAL));
        txt_month.setText("Month: ");
        txt_month.setTextColor(getResources().getColor(R.color.colorPrimary));
        //txt_title.setTextSize(R.dimen.app_icon_size);
        // missing: android:fontFamily="casual"
        gridLayout1.addView(txt_month);

        // month_spinner
        month_spinner = new Spinner(getApplicationContext());
        pmarginParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
        pmarginParams.setMargins(50, 0, 100, 0);
        month_spinner.setLayoutParams(params);
        //month_spinner.setBackgroundResource(17301510); //  17301510 = btn_dropdown
        month_spinner.setLayoutMode(1); // 1 = dropdown
        months = user.getMonthsPaychecks();
        ArrayAdapter monthsAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, months);
        month_spinner.setAdapter(monthsAdapter);
        // set default value to current month
        month_spinner.setSelection(monthsAdapter.getCount() - 1);
        gridLayout1.addView(month_spinner);

        linearLayout.addView(gridLayout1);

        // gridLayout2
        gridLayout2 = new GridLayout(getApplicationContext());
        params = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.FILL_PARENT);
        gridLayout2.setLayoutParams(params);
        gridLayout2.setBackgroundColor(Color.GRAY);
        gridLayout2.setRowCount(2);
        gridLayout2.setColumnCount(5);

        if (years.get(0).equals("empty") || months.get(0).equals("empty")) {
            isCurrentPaycheck = true;
        }
        else {
            key = month_spinner.getSelectedItem().toString() + "#" + year_spinner.getSelectedItem().toString();
            isCurrentPaycheck = false;
        }

        ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);

        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < texts.length; i++) {

            textView1 = new TextView(getApplicationContext());
            textView1.setLayoutParams(params1);
            textView1.setTypeface(Typeface.create("casual", Typeface.NORMAL));
            textView1.setText(texts[i]);
            textView1.setTextColor(getResources().getColor(R.color.colorPrimary));

            textView2 = new TextView(getApplicationContext());
            textView2.setLayoutParams(params2);
            textView2.setGravity(Gravity.CENTER);
            textView2.setTypeface(Typeface.create("casual", Typeface.NORMAL));
            textView2.setTextColor(getResources().getColor(R.color.colorPrimary));

            gridLayout2.addView(textView1);


            if( isCurrentPaycheck) {
                paycheck = new Paycheck((currentMonth+1) + "#" + currentYear, user);
                user.setCurrentPaycheck(new Paycheck());
            }
            else
                paycheck = user.getPaychecks().get(key);

            switch (i) {
                case 0: {
                    textView2.setText(String.valueOf(paycheck.getBaseWage()));
                    //textView2.setText(String.valueOf(user.getPaychecks().get(key).getBaseWage()));
                    textView2.setTextColor(Color.GREEN);
                    break;
                }
                case 1: {
                    textView2.setText(String.valueOf(paycheck.getTravelFee()));
                    textView2.setTextColor(Color.GREEN);
                    break;
                }
                case 2: {
                    textView2.setText(String.valueOf(paycheck.getNationalInsurance()));
                    textView2.setTextColor(Color.RED);
                    break;
                }
                case 3: {
                    textView2.setText(String.valueOf(paycheck.getIncomeTax()));
                    textView2.setTextColor(Color.RED);
                    break;
                }
                case 4: {
                    textView2.setText(String.valueOf(paycheck.getHealthInsurance()));
                    textView2.setTextColor(Color.RED);
                    break;
                }
                case 5: {
                    textView2.setText(String.valueOf(paycheck.getGrossWage()));
                    textView2.setTextColor(Color.BLUE);
                    break;
                }
                case 6: {
                    textView2.setText(String.valueOf(paycheck.getNewWage()));
                    textView2.setTextColor(Color.BLUE);
                    break;
                }
                default: {
                    break;
                }
            }

            gridLayout2.addView(textView2);

        }

        linearLayout.addView(gridLayout2);
    }
}