package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Set;

public class PaycheckActivity extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private String key = "";
    private Paycheck paycheck;
    boolean isCurrentPaycheck = true;
    private Spinner year_spinner, month_spinner;
    private LinearLayout linearLayout, buttonLinearLauout;
    private GridLayout gridLayout1, gridLayout2;
    private TextView txt_title, txt_year, txt_month;
    private Set<String> years, months;
    private String[] texts = {"Base Wage", "Travel Fee", "National Insurance", "Income Tax", "Health Insurance", "Gross Wage", "Net Wage"};
    private TextView textView1, textView2;
    private Button btn_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paycheck);
        user = (User) getIntent().getSerializableExtra("user");

        linearLayout = findViewById(R.id.paycheck_main_linearLayout);

        initChooseGrid();

        setPaycheck();

        initPaycheckGrid();

        initButton();
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

        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH);
        int currentYear = c.get(Calendar.YEAR);

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
        years = user.getYearsPaychecks();
        if(!years.contains(currentYear))
            years.add(String.valueOf(currentYear));
        ArrayAdapter yearsAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, years.toArray());
        year_spinner.setAdapter(yearsAdapter);
        year_spinner.setSelection(yearsAdapter.getPosition(String.valueOf(currentYear)));

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
        months = user.getMonthsPaychecks();
        if(!months.contains(String.valueOf(currentMonth + 1)))
            months.add(String.valueOf(currentMonth + 1));
        ArrayAdapter monthsAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, months.toArray());
        month_spinner.setAdapter(monthsAdapter);
        // set default value to current month
        month_spinner.setSelection(monthsAdapter.getPosition(String.valueOf(currentMonth + 1)));
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
        if(index == 5)
            textView1.setTextColor(Color.DKGRAY);
        else if(index == 6)
            textView1.setTextColor(Color.BLUE);
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
                textView2.setTextColor(Color.DKGRAY);
                break;
            }
            case 6: {
                SpannableString content = new SpannableString(String.valueOf(paycheck.getNewWage()));
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                textView2.setText(content);
                textView2.setTextColor(Color.BLUE);
                break;
            }
            default: {
                break;
            }
        }
    }

    public void setIsCurrentPaycheck() {

        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH);
        int currentYear = c.get(Calendar.YEAR);

        if(year_spinner.getSelectedItem().toString().equals(String.valueOf(currentYear)) &&
                month_spinner.getSelectedItem().toString().equals(String.valueOf(currentMonth + 1)))
            isCurrentPaycheck = true;
        else
            isCurrentPaycheck = false;

        key = month_spinner.getSelectedItem().toString() + "#" + year_spinner.getSelectedItem().toString();
    }

    public void setPaycheck() {

        setIsCurrentPaycheck();

        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH);
        int currentYear = c.get(Calendar.YEAR);

        if( isCurrentPaycheck) {
            paycheck = new Paycheck((currentMonth+1) + "#" + currentYear, user);
            user.setCurrentPaycheck(paycheck);
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
        linearLayout.removeView(gridLayout2);
        linearLayout.removeView(buttonLinearLauout);
        initPaycheckGrid();
        initButton();
    }
}