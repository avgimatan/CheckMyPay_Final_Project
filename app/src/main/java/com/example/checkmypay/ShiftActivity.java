package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ShiftActivity extends AppCompatActivity implements Finals, View.OnClickListener{

    private ArrayList<Shift> shifts;
    private User user;
    private CheckBox isHoliday;
    private HashMap<String,Button> buttons;
    private EditText editDate, editFromTime, editEndTime;
    private TextView textView, textTotalHours, textShiftProfit;
    private GridLayout gridLayout;
    private LinearLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);

        user = (User) getIntent().getSerializableExtra("user");

        mainLayout = findViewById(R.id.main_shift_layout);
        initShiftGrid();
        mainLayout.addView(gridLayout); // DEBUG STOP HERE
        initButton();

    }

    public void initButton() {

        buttons = new HashMap<>();
        buttons.put("Add Shift", new Button(this));
        buttons.put("Update Details", new Button(this));

        buttons.get("Add Shift").setText("Add Shift");
        buttons.get("Update Details").setText("Update Details");

        for (Button button : buttons.values()) {
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            button.setLayoutParams(params);
            button.setAllCaps(false);
            button.setBackgroundResource(R.color.colorPrimary);
            button.setTextColor(getResources().getColor(R.color.white));
            button.setOnClickListener(this);
            mainLayout.addView(button);
        }

    }

    public void initShiftGrid() {

        shifts = user.getShifts();
        isHoliday = new CheckBox(getApplicationContext());

        gridLayout = new GridLayout(getApplicationContext());
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);
        gridLayout.setLayoutParams(params);
        gridLayout.setColumnCount(GRID_SHIFT_COLUMN);
        String[] headlines = {"Date ","Begin Hour ", "End Hour ", "Total Time ","Shift Profit ", "Holiday"};

        // add headlines view to grid
        for (int i = 0; i < headlines.length; i++) {
            textView = new TextView(getApplicationContext());
            textView.setText(headlines[i]);
            gridLayout.addView(textView);

        }

        // add shifts to grid
        if (shifts != null) {
            for (Shift shift : shifts) {
                editDate = new EditText(this);
                editDate.setText(shift.getDay() + "/" + shift.getMonth());
                gridLayout.addView(editDate); // Date (editable)
                editFromTime = new EditText(this);
                editFromTime.setText(shift.getBeginHour() + ":" + shift.getBeginMinute());
                gridLayout.addView(editFromTime); // from time (editable)
                editEndTime = new EditText(this);
                editEndTime.setText(shift.getEndHour() + ":" + shift.getEndMinute());
                gridLayout.addView(editEndTime); // end time (editable)
                textTotalHours = new TextView(this);
                textTotalHours.setText(String.valueOf(shift.getTotalHours()));
                gridLayout.addView(textTotalHours); // total hours
                textShiftProfit = new TextView(this);
                textShiftProfit.setText(String.valueOf(shift.getShiftProfit()));
                gridLayout.addView(textShiftProfit); // total profit
                gridLayout.addView(isHoliday); // checkbox
            }
        }
    }


    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        switch (button.getText().toString()) {
            case "Add Shift":
                if(shifts == null) {
                    editDate = new EditText(this);
                    editDate.setText("01/01");
                    gridLayout.addView(editDate); // Date (editable)
                    editFromTime = new EditText(this);
                    editFromTime.setText("00:00");
                    gridLayout.addView(editFromTime); // from time (editable)
                    editEndTime = new EditText(this);
                    editEndTime.setText("24:00");
                    gridLayout.addView(editEndTime); // end time (editable)
                    textTotalHours = new TextView(this);
                    textTotalHours.setText("10.5");
                    gridLayout.addView(textTotalHours); // total hours
                    textShiftProfit = new TextView(this);
                    textShiftProfit.setText("288.9");
                    gridLayout.addView(textShiftProfit); // total profit
                    gridLayout.addView(isHoliday); // checkbox
                }

                Shift shift = new Shift(editDate.getText().toString().split("/")[0],
                        editDate.getText().toString().split("/")[1],
                        editFromTime.getText().toString().split(":")[0],
                        editEndTime.getText().toString().split(":")[0],
                        editFromTime.getText().toString().split(":")[1],
                        editEndTime.getText().toString().split(":")[1],
                        textTotalHours.getText().toString(),
                        textShiftProfit.getText().toString());
                shifts.add(shift);

                break;
            case "Update Details":

                break;
        }



    }
}
