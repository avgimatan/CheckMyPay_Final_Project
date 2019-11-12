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
import android.widget.Toast;

import com.google.protobuf.Parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ShiftActivity extends AppCompatActivity implements Finals, View.OnClickListener{

    private ArrayList<Shift> shifts;
    private User user;
    //private User userWithShifts;
    private CheckBox isHoliday;
    private HashMap<String,Button> buttons;
    private ArrayList<Button> gridButtons;
    private EditText editDate, editFromTime, editEndTime;
    private TextView textView, textTotalHours, textShiftProfit;
    private GridLayout gridLayout;
    private LinearLayout mainLayout;
    private int btnCounter = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);

        user = (User) getIntent().getSerializableExtra("user");

        mainLayout = findViewById(R.id.main_shift_layout);
        initShiftGrid();
        mainLayout.addView(gridLayout);
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
        gridButtons = new ArrayList<>();

        //**********************
/*        userWithShifts = new User("shosho@mail.com","123456");
        ArrayList <Shift> allShifts = new ArrayList<>();
        for (int i = 0;i<4;i++) {
            String num = String.valueOf(i);
            Shift sh = new Shift(num,"11",i + "0","24",i +"1","55","10","290");
            allShifts.add(sh);
        }
        userWithShifts.setShifts(allShifts);*/

        //****************
        isHoliday = new CheckBox(this);

        gridLayout = new GridLayout(this);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);
        gridLayout.setLayoutParams(params);
        gridLayout.setColumnCount(GRID_SHIFT_COLUMN);
        gridLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
/*        if (shifts.size() == 0)
            gridLayout.setRowCount(1);
        else
            gridLayout.setRowCount(shifts.size() + 1);*/
        String[] headlines = {"Date ","Begin Hour ", "End Hour ", "Total Time ","Shift Profit ", "Holiday", "Edit"};

        // add headlines view to grid
        for (int i = 0; i < headlines.length; i++) {
            textView = new TextView(this);
            textView.setText(headlines[i]);
            gridLayout.addView(textView);

        }

        // add shifts to grid
        if (shifts.size() != 0) {
            //gridLayout.setRowCount(shifts.size() + 1);
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
                isHoliday = new CheckBox(this);
                gridLayout.addView(textShiftProfit); // total profit
                gridLayout.addView(isHoliday); // checkbox
                Button bu = new Button(this);
                bu.setId(btnCounter++);
                //bu.setText("Edit");
                gridButtons.add(bu);

                gridLayout.addView(bu);
            }
        }
    }


    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        switch (button.getText().toString()) {
            case "Add Shift":

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

                // calc total hour (try other approach)
                String beginHour = editFromTime.getText().toString().split(":")[0];
                String endHour = editEndTime.getText().toString().split(":")[0];
                String hourDiff = String.valueOf(Integer.parseInt(endHour) - Integer.parseInt(beginHour));
                String beginMinute = editFromTime.getText().toString().split(":")[1];
                String endMinute = editEndTime.getText().toString().split(":")[1];
                String minuteDiff = String.valueOf(Integer.parseInt(endMinute) - Integer.parseInt(beginMinute));
                textTotalHours.setText(hourDiff + ":" + minuteDiff); //

                gridLayout.addView(textTotalHours); // total hours
                textShiftProfit = new TextView(this);
                textShiftProfit.setText("288.9");
                gridLayout.addView(textShiftProfit); // total profit
                isHoliday = new CheckBox(this);
                gridLayout.addView(isHoliday); // checkbox
                Button bu = new Button(this);
                //bu.setText("Edit");
                gridButtons.add(bu);
                gridLayout.addView(bu);

                Shift shift = new Shift(user, editDate.getText().toString().split("/")[0],
                        editDate.getText().toString().split("/")[1],
                        editFromTime.getText().toString().split(":")[0],
                        editEndTime.getText().toString().split(":")[0],
                        editFromTime.getText().toString().split(":")[1],
                        editEndTime.getText().toString().split(":")[1]);
                shifts.add(shift);
                //gridLayout.setRowCount(shifts.size() + 1);
                Toast.makeText(this,"shift size " + shifts.size() , Toast.LENGTH_SHORT).show();

                break;
            case "Update Details":

/*                ArrayList<Shift> updatedShifts = new ArrayList<>();
                for (int i = 0; i < gridLayout.getRowCount(); i++) {
                    for (int j = 0; j < gridLayout.getColumnCount(); j++) {


                    }
                }*/

                break;
        }



    }
}
