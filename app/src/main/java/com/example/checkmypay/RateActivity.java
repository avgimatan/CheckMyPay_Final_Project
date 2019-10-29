package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RateActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private TextView txt_title, txt_hourlyWage, txt_salaryStartDate, txt_salaryEndDate, txt_overtime, txt_creditPoints,
                        txt_fromHour, txt_toHour, txt_providentFund /* kupat gemel */; // need more

    private EditText input_hourlyWage, input_salaryStartDate, input_salaryEndDate, input_overtime, input_creditPoints,
            input_fromHour, input_toHour, input_providentFund;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        linearLayout = findViewById(R.id.rate_linearLayout);

        //linearLayout.add


    }
}
