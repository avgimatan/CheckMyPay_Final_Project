package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ShiftActivity extends AppCompatActivity implements Finals, View.OnClickListener{

    private ArrayList<Shift> shifts;
    private User user;
    private CheckBox isHoliday;
    private Button updateDetailsButton;
    private EditText editDetails;
    private TextView textView;
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
        updateDetailsButton = findViewById(R.id.shift_updatedetails_btn);
        mainLayout.addView(updateDetailsButton);

    }

    public void initShiftGrid() {

        shifts = user.getShifts();
        isHoliday = new CheckBox(getApplicationContext());
        editDetails = new EditText(getApplicationContext());
        //textView = new TextView(getApplicationContext());
        gridLayout = findViewById(R.id.shift_gridlayout);
        gridLayout.setColumnCount(GRID_SHIFT_COLUMN);
        String[] headlines = {"Date","Begin Hour", "End Hour", "Total Time","Shift Profit", "Holiday"};

        // add headlines view to grid
        for (int i = 0; i < headlines.length; i++) {
            textView = new TextView(getApplicationContext());
            textView.setText(headlines[i]);
            gridLayout.addView(textView);

        }

/*        // add shifts to grid
        for (Shift shift : shifts) {
            editDetails.setText(shift.getDay() + "/" + shift.getMonth());
            gridLayout.addView(editDetails); // Date (editable)
            editDetails.setText(shift.getBeginHour() + ":" + shift.getBeginMinute());
            gridLayout.addView(editDetails); // from time (editable)
            editDetails.setText(shift.getEndHour() + ":" + shift.getEndMinute());
            gridLayout.addView(editDetails); // end time (editable)
            textView.setText(String.valueOf(shift.getTotalHours()));
            gridLayout.addView(textView); // total hours
            textView.setText(String.valueOf(shift.getShiftProfit()));
            gridLayout.addView(textView); // total profit
            gridLayout.addView(isHoliday); // checkbox

        }*/
    }


    @Override
    public void onClick(View view) {

/*        ArrayList<Shift> newShifts;
        Shift shift = new Shift();
        BaseAdapter baseAdapter;
        baseAdapter.geti
        shift.setBeginHour(editDetails.getText());*/

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            //View itemText = child.findViewById(R.id.itemtext);

        }



    }
}
