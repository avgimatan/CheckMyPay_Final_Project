package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class PaycheckActivity extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private String key = "";
    private Paycheck paycheck;
    boolean isCurrentPaycheck;
    private Spinner year_spinner, month_spinner;
    private LinearLayout linearLayout, buttonLinearLauout;
    private GridLayout gridLayout1, gridLayout2;
    private TextView txt_title, txt_year, txt_month;
    //private String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private List<String> years, months;
    private String[] texts = {"Base Wage", "Travel Fee", "National Insurance", "Income Tax", "Health Insurance", "Gross Wage", "Net Wage"};
    private TextView textView1, textView2, txt_temp;
    private Button btn_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paycheck);
        user = (User) getIntent().getSerializableExtra("user");

        linearLayout = findViewById(R.id.paycheck_main_linearLayout);

        initChooseGrid();

        setIsCurrentPaycheck();

        setPaycheck();

        initPaycheckGrid();

        initButton();

        // TODO: onClick to spinners. if he choose else paycheck ?
    }

    public void initChooseGrid() {

        // gridLayout1
        gridLayout1 = new GridLayout(getApplicationContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
        gridLayout1.setLayoutParams(params);
        gridLayout1.setPadding(0, 50, 0, 30);
        gridLayout1.setRowCount(2);
        gridLayout1.setColumnCount(2);

        initYearAndMonthLabelAndSpinner();

        linearLayout.addView(gridLayout1);

    }

    public void initYearAndMonthLabelAndSpinner() {

        txt_year = new TextView(getApplicationContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txt_year.setLayoutParams(params);
        txt_year.setPadding(190, 0, 150, 0);
        txt_year.setTypeface(Typeface.create("casual", Typeface.NORMAL), Typeface.BOLD);
        txt_year.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.label_size));
        txt_year.setTextColor(getResources().getColor(R.color.colorPrimary));
        txt_year.setText("Year: ");
        gridLayout1.addView(txt_year);

        // year_spinner
        year_spinner = new Spinner(getApplicationContext());
        year_spinner.setLayoutMode(1); // 1 = dropdown
        years = user.getYearsPaychecksAaaa();
        ArrayAdapter yearsAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, years);
        year_spinner.setAdapter(yearsAdapter);
        // set default value to current current year
        year_spinner.setSelection(yearsAdapter.getCount() - 1);

        /*year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //year_spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });*/

        gridLayout1.addView(year_spinner);

        // txt_month
        txt_month = new TextView(getApplicationContext());
        params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txt_month.setLayoutParams(params);
        txt_month.setPadding(190, 0, 150, 0);
        txt_month.setTypeface(Typeface.create("casual", Typeface.NORMAL), Typeface.BOLD);
        txt_month.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.label_size));
        txt_month.setTextColor(getResources().getColor(R.color.colorPrimary));
        txt_month.setText("Month: ");
        gridLayout1.addView(txt_month);

        // month_spinner
        month_spinner = new Spinner(getApplicationContext());
        month_spinner.setLayoutMode(1); // 1 = dropdown
        months = user.getMonthsPaychecksAaaa();
        ArrayAdapter monthsAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, months);
        month_spinner.setAdapter(monthsAdapter);
        // set default value to current month
        month_spinner.setSelection(monthsAdapter.getCount() - 1);
        gridLayout1.addView(month_spinner);
    }

    public void initPaycheckGrid() {

        // gridLayout2
        gridLayout2 = new GridLayout(getApplicationContext());
        ViewGroup.LayoutParams params = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        gridLayout2.setLayoutParams(params);
        gridLayout2.setPadding(0, 50, 0, 30);
        gridLayout2.setRowCount(7);
        gridLayout2.setColumnCount(2);

        initPaycheckGridLabels();

    }

    public void initPaycheckGridLabels() {

        ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);

        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);


        for (int i = 0; i < texts.length; i++) {

            initLeftColumnLabelGrid(i, params1);

            initRightColumnLabelGrid(i, params2);

            gridLayout2.addView(textView2);

        }

        linearLayout.addView(gridLayout2);

    }

    public void initLeftColumnLabelGrid(int index, ViewGroup.LayoutParams params) {

        textView1 = new TextView(getApplicationContext());
        textView1.setLayoutParams(params);
        textView1.setText(texts[index]);
        textView1.setPadding(40, 50, 0, 0);
        textView1.setTypeface(Typeface.create("casual", Typeface.NORMAL), Typeface.BOLD);
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.label_size));
        if(index == 6)
            textView1.setTextColor(Color.DKGRAY);
        else
            textView1.setTextColor(getResources().getColor(R.color.colorPrimary));

        gridLayout2.addView(textView1);
    }

    public void initRightColumnLabelGrid(int index, ViewGroup.LayoutParams params) {

        textView2 = new TextView(getApplicationContext());
        textView2.setLayoutParams(params);
        textView2.setPadding(0, 50, 250, 0);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.label_size));
        textView2.setGravity(Gravity.CENTER);
        textView2.setTypeface(Typeface.create("casual", Typeface.NORMAL), Typeface.BOLD);
        textView2.setTextColor(getResources().getColor(R.color.colorPrimary));

        switch (index) {
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
                textView2.setTextColor(Color.DKGRAY);
                break;
            }
            default: {
                break;
            }
        }
    }

    public void setIsCurrentPaycheck() {

        if (years.get(0).equals("empty") || months.get(0).equals("empty")) {
            isCurrentPaycheck = true;
        } else {
            key = month_spinner.getSelectedItem().toString() + "#" + year_spinner.getSelectedItem().toString();
            isCurrentPaycheck = false;
        }

    }

    public void setPaycheck() {

        //paycheck = new Paycheck(month_spinner.getSelectedItem().toString() + "#" + year_spinner.getSelectedItem().toString());

        setIsCurrentPaycheck();

        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH);
        int currentYear = c.get(Calendar.YEAR);

        if( isCurrentPaycheck) {
            paycheck = new Paycheck((currentMonth+1) + "#" + currentYear, user);
            user.setCurrentPaycheck(new Paycheck());
        }
        else
            paycheck = user.getPaychecks().get(key);
    }

    public void initButton() {

        // buttonLinearLauout
        buttonLinearLauout = new LinearLayout(getApplicationContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        buttonLinearLauout.setLayoutParams(params);


        //btn_show
        btn_show = new Button(getApplicationContext());
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.BOTTOM;
        btn_show.setLayoutParams(p);
        btn_show.setBackgroundResource(R.color.colorPrimary);
        btn_show.setTextColor(Color.WHITE);
        btn_show.setTypeface(Typeface.create("casual", Typeface.NORMAL));
        btn_show.setText("Show");
        btn_show.setOnClickListener(this);
        buttonLinearLauout.addView(btn_show);

        linearLayout.addView(buttonLinearLauout);
    }

    @Override
    public void onClick(View v) {

        setPaycheck();
        //paycheck = new Paycheck(month_spinner.getSelectedItem().toString() + "#" + year_spinner.getSelectedItem().toString());
        initPaycheckGrid();
    }
}