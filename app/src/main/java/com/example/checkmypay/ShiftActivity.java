package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class ShiftActivity extends AppCompatActivity implements Finals, View.OnClickListener{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Shift> shifts;
    private User user;
    private CheckBox isHoliday;
    private HashMap<String,Button> buttons;
    private ArrayList<Button> gridButtons;
    private EditText editDate, editFromTime, editEndTime;
    private TextView textView, textShiftProfit;
    private LinearLayout mainLayout;
    private TableLayout tableLayout;
    private int editShiftIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);

        user = (User) getIntent().getSerializableExtra("user");

        mainLayout = findViewById(R.id.main_shift_layout);
        initShiftTable();
        mainLayout.addView(tableLayout);
        initButton();

    }

    public void initButton() {

        // create and init buttons
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

    public void initShiftTable() {

        // init parameters
        shifts = user.getShifts();
        gridButtons = new ArrayList<>();
        tableLayout = new TableLayout(this);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
        TableRow tableRowViews = new TableRow(this);
        tableRowViews.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));

        // add headlines view to grid
        String[] headlines = {"Date ","Begin Hour ", "End Hour ","Holiday ", "Shift Profit ", "Edit"};
        for (String str : headlines) {
            textView = new TextView(this);
            textView.setText(str);
            tableRowViews.addView(textView);
        }
        tableLayout.addView(tableRowViews);

        // add shifts to table
        if (shifts.size() != 0) {

            for (Shift shift : shifts) {

                TableRow tableRowEdit = new TableRow(this);
                tableRowEdit.setId(editShiftIndex);
                tableRowEdit.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));

                editDate = new EditText(this);
                editDate.setText(shift.getDay() + "/" + shift.getMonth());
                tableRowEdit.addView(editDate); // Date (editable)

                editFromTime = new EditText(this);
                editFromTime.setText(shift.getBeginHour() + ":" + shift.getBeginMinute());
                tableRowEdit.addView(editFromTime); // from time (editable)

                editEndTime = new EditText(this);
                editEndTime.setText(shift.getEndHour() + ":" + shift.getEndMinute());
                tableRowEdit.addView(editEndTime); // end time (editable)

                isHoliday = new CheckBox(this);
                tableRowEdit.addView(isHoliday); // checkbox

                textShiftProfit = new TextView(this);
                textShiftProfit.setText(String.valueOf(shift.getShiftProfit()));
                tableRowEdit.addView(textShiftProfit); // total profit

                Button bu = new Button(this,null,android.R.attr.buttonBarButtonStyle);
                bu.setId(editShiftIndex);
                editShiftIndex++;
/*                ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(2, 2, 2, 2);
                bu.setLayoutParams(params);*/
                bu.setAllCaps(false);
                bu.setBackgroundResource(R.color.colorPrimary);
                bu.setText("Edit");
                bu.setTextColor(getResources().getColor(R.color.white));
                gridButtons.add(bu);
                bu.setOnClickListener(this);
                tableRowEdit.addView(bu);
                tableLayout.addView(tableRowEdit);
            }
        }
    }


    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        switch (button.getText().toString()) {

            case "Add Shift":

                // init table row for each shift
                TableRow tableRowEdit = new TableRow(this);
                tableRowEdit.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));

                // set data to views
                editDate = new EditText(this);
                editDate.setText("01/01");
                tableRowEdit.addView(editDate); // Date (editable)

                editFromTime = new EditText(this);
                editFromTime.setText("00:00");
                tableRowEdit.addView(editFromTime); // from time (editable)

                editEndTime = new EditText(this);
                editEndTime.setText("24:00");
                tableRowEdit.addView(editEndTime);

                isHoliday = new CheckBox(this);
                tableRowEdit.addView(isHoliday); // checkbox

                textShiftProfit = new TextView(this);
                textShiftProfit.setText("0.0");
                tableRowEdit.addView(textShiftProfit); // total profit

                Button bu = new Button(this,null,android.R.attr.buttonBarButtonStyle);
                bu.setId(editShiftIndex);
                editShiftIndex++;
/*                ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(2, 2, 2, 2);
                bu.setLayoutParams(params);*/
                bu.setAllCaps(false);
                bu.setBackgroundResource(R.color.colorPrimary);
                bu.setText("Edit");
                bu.setTextColor(getResources().getColor(R.color.white));
                gridButtons.add(bu);
                bu.setOnClickListener(this);
                tableRowEdit.addView(bu);
                tableLayout.addView(tableRowEdit);

                // create and add shift with the new details
                Shift shift = new Shift(
                        String.valueOf(user.getHourlyWage()),
                        editDate.getText().toString().split("/")[0],
                        editDate.getText().toString().split("/")[1],
                        editFromTime.getText().toString().split(":")[0],
                        editEndTime.getText().toString().split(":")[0],
                        editFromTime.getText().toString().split(":")[1],
                        editEndTime.getText().toString().split(":")[1],
                        user);

                shifts.add(shift);
                user.setShifts(shifts);
                Toast.makeText(this,"shift size " + shifts.size() , Toast.LENGTH_SHORT).show();

                break;

            case "Edit":

                for (Button b : gridButtons) {
                    if (b.getId() == button.getId()) {

                        int shiftIndex = b.getId();
                        Shift editedShift = shifts.get(shiftIndex);

                        TableRow editTableRow = (TableRow) tableLayout.getChildAt(shiftIndex + 1);

                        EditText editShiftDate = (EditText) editTableRow.getChildAt(0);
                        editedShift.setDay(editShiftDate.getText().toString().split("/")[0]);
                        editedShift.setMonth(editShiftDate.getText().toString().split("/")[1]);

                        EditText editFromHour = (EditText) editTableRow.getChildAt(1);
                        editedShift.setBeginHour(editFromHour.getText().toString().split(":")[0]);
                        editedShift.setBeginMinute(editFromHour.getText().toString().split(":")[1]);

                        EditText editToHour = (EditText) editTableRow.getChildAt(2);
                        editedShift.setEndHour(editToHour.getText().toString().split(":")[0]);
                        editedShift.setEndMinute(editToHour.getText().toString().split(":")[1]);

                        CheckBox editIsHoliday = (CheckBox) editTableRow.getChildAt(3);
                        if (editIsHoliday.isChecked()) {
                            editedShift.setHoliday(true);
                        }

                        editedShift.setTotalHours();
                        editedShift.setShiftProfit();

                        shifts.set(shiftIndex, editedShift);

                        Toast.makeText(this,"Edit detail's Successfully" , Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case "Update Details":

                db.collection("Users")
                        .document(user.getId())
                        .set(user)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getApplicationContext(), "Updated detail's Successfully", Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(
                                e -> Toast.makeText(getApplicationContext(), " An error has occurred", Toast.LENGTH_SHORT).show());

                break;
        }



    }
}
