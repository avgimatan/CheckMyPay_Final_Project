package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class RateActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private User user;

    private EditText input_wage, input_startDate, input_endDate, input_credits, input_fromHour, input_travelFee,
            input_fromMinute, input_toHour, input_toMinute, input_providentFund, input_advancedStudyFund;

    private Button btn_saveDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        user = (User) getIntent().getSerializableExtra("user");

        input_wage = findViewById(R.id.rate_input_hourly_wage);
        input_startDate = findViewById(R.id.rate_input_start_date);
        input_endDate = findViewById(R.id.rate_input_end_date);
        input_fromHour = findViewById(R.id.rate_input_from_hour);
        input_fromMinute = findViewById(R.id.rate_input_from_minute);
        input_toHour = findViewById(R.id.rate_input_to_hour);
        input_toMinute = findViewById(R.id.rate_input_to_minute);
        input_providentFund = findViewById(R.id.rate_input_provident_funds);
        input_advancedStudyFund = findViewById(R.id.rate_input_advanced_study_fund);
        input_credits = findViewById(R.id.rate_input_credits);
        input_travelFee = findViewById(R.id.rate_input_travel_fee);
        btn_saveDetails = findViewById(R.id.rate_btn_save_details);

        btn_saveDetails.setOnClickListener(this);

        getDetails();

    }

    public void getDetails() {

        input_wage.setText(String.valueOf(user.getHourlyWage()));
        input_travelFee.setText(String.valueOf(user.getTravelFee()));
        input_startDate.setText(String.valueOf(user.getStartDate()));
        input_endDate.setText(String.valueOf(user.getEndDate()));
        input_fromHour.setText(String.valueOf(user.getShabbatFromHour()));
        input_fromMinute.setText(String.valueOf(user.getShabbatFromMin()));
        input_toHour.setText(String.valueOf(user.getShabbatToHour()));
        input_toMinute.setText(String.valueOf(user.getShabbatToMin()));
        input_providentFund.setText(String.valueOf(user.getProvidentFund()));
        input_advancedStudyFund.setText(String.valueOf(user.getAdvancedStudyFund()));
        input_credits.setText(String.valueOf(user.getCredits()));
    }


    public void writeDetailsToDB() {

        // TODO: it is only for test!

        /*ArrayList<Shift> shifts = new ArrayList<>();
        Map<String, Paycheck> paychecks = new HashMap<>();
        shifts = new ArrayList<>();
        shifts.add(new Shift(user.getHourlyWage(), "01", "10", "07", "15", "00", "00",user));
        shifts.add(new Shift(user.getHourlyWage(), "02", "10", "15", "23", "00", "00",user));
        shifts.add(new Shift(user.getHourlyWage(), "03", "10", "15", "23", "00", "00",user));
        user.setShifts(shifts);
        paychecks.put("10#2019", new Paycheck("10#2019", user));
        user.setPaychecks(paychecks);

        shifts = new ArrayList<>();
        shifts.add(new Shift(user.getHourlyWage(), "01", "11", "07", "15", "00", "00",user));
        shifts.add(new Shift(user.getHourlyWage(), "02", "11", "07", "15", "00", "00",user));*/
        /*shifts.add(new Shift(user.getHourlyWage(), "1", "7", "7", "15", "10", "10",user));
        shifts.add(new Shift(user.getHourlyWage(), "2", "7", "15", "23", "0", "0",user));
        shifts.add(new Shift(user.getHourlyWage(), "3", "7", "7", "15", "10", "10",user));*/
        //user.setShifts(shifts);
        //paychecks.put("7#2019", new Paycheck("7#2019", user));
        //user.setPaychecks(paychecks);



        db.collection("Users")
                .document(user.getId())
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Save detailes Successfully", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), " An error has occurred", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onClick(View v) {
        if (Integer.parseInt(input_startDate.getText().toString()) < 1 || Integer.parseInt(input_startDate.getText().toString()) > 31) {
            Toast.makeText(RateActivity.this, "Start Date have to be between 1 -31!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.parseInt(input_endDate.getText().toString()) < 1 || Integer.parseInt(input_endDate.getText().toString()) > 31) {
            Toast.makeText(RateActivity.this, "End Date have to be between 1 -31!", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( (Integer.parseInt(input_fromHour.getText().toString()) < 0 || Integer.parseInt(input_fromHour.getText().toString()) > 23)
                || (Integer.parseInt(input_toHour.getText().toString()) < 0 || Integer.parseInt(input_toHour.getText().toString()) > 23)
                || (Integer.parseInt(input_fromMinute.getText().toString()) < 0 || Integer.parseInt(input_fromMinute.getText().toString()) > 59)
                || (Integer.parseInt(input_toMinute.getText().toString()) < 0 || Integer.parseInt(input_toMinute.getText().toString()) > 59) ) {
            Toast.makeText(RateActivity.this, "Wrong shabbat hours! please insert a valid input", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = user.getId();
        String email = user.getEmail();
        String password = user.getPassword();
        ArrayList<Shift> shifts = user.getShifts();
        Map<String, Paycheck> paychecks = user.getPaychecks();

        user = new User(
                id,
                email,
                password,
                Float.parseFloat(input_wage.getText().toString()),
                Float.parseFloat(input_travelFee.getText().toString()),
                Float.parseFloat(input_providentFund.getText().toString()),
                Float.parseFloat(input_advancedStudyFund.getText().toString()),
                Float.parseFloat(input_credits.getText().toString()),
                Integer.parseInt(input_startDate.getText().toString()),
                Integer.parseInt(input_endDate.getText().toString()),
                Integer.parseInt(input_fromHour.getText().toString()),
                Integer.parseInt(input_toHour.getText().toString()),
                Integer.parseInt(input_fromMinute.getText().toString()),
                Integer.parseInt(input_toMinute.getText().toString()),
                shifts,
                paychecks);

        writeDetailsToDB();

        goToMenuActivity();
    }

    public void goToMenuActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
