package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ShiftActivity extends AppCompatActivity {

    private ArrayList<Shift> shifts;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);

        user = (User) getIntent().getSerializableExtra("user");

        LinearLayout mainLayout = findViewById(R.id.main_shift_layout);
        GridLayout gridLayout = findViewById(R.id.shift_gridlayout);
        gridLayout.setColumnCount(4);

        TextView textView = new TextView(getApplicationContext());
        String[] texts = {"Date","Begin Hour", "End Hour", "Total Hours"};

        for (int i = 0; i < texts.length; i++) {
            textView.setText(texts[i]);
            gridLayout.addView(textView);
        }

        shifts = user.getShifts();

        for (Shift shift : shifts) {

        }

        for (int i = 0; i < shifts.size(); i++) {

        }


    }
}
