package com.example.checkmypay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
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
        Log.e("Rate", "onCreate: " + user.getId());

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

        input_wage.setText(user.getHourlyWage() + "");
        input_travelFee.setText(user.getTravelFee() + "");
        input_startDate.setText(user.getStartDate() + "");
        input_endDate.setText(user.getEndDate() + "");
        input_fromHour.setText(user.getShabbatFromHour() + "");
        input_fromMinute.setText(user.getShabbatFromMin() + "");
        input_toHour.setText(user.getShabbatToHour() + "");
        input_toMinute.setText(user.getShabbatToMin() + "");
        input_providentFund.setText(user.getProvidentFund() + "");
        input_advancedStudyFund.setText(user.getAdvancedStudyFund() + "");
        input_credits.setText(user.getCredits() + "");
    }


    public void writeDetailsToDB() {
        CollectionReference users = db.collection("Users");

        Map<String, Object> details = new HashMap<>();
        details.put("email", this.user.getEmail());
        details.put("password", this.user.getPassword());
        details.put("hourlyWage", input_wage.getText().toString());
        details.put("travelFee", input_travelFee.getText().toString());
        details.put("startDate", input_startDate.getText().toString());
        details.put("endDate", input_endDate.getText().toString());
        details.put("fromHour", input_fromHour.getText().toString());
        details.put("fromMinute", input_fromMinute.getText().toString());
        details.put("toHour", input_toHour.getText().toString());
        details.put("toMinute", input_toMinute.getText().toString());
        details.put("providentFund", input_providentFund.getText().toString());
        details.put("advancedStudyFund", input_advancedStudyFund.getText().toString());
        details.put("credits", input_credits.getText().toString());
        details.put("shifts", this.user.getShifts());
        details.put("paychecks", this.user.getPaychecks());

        users.document(user.getEmail()).set(details);
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
        writeDetailsToDB();

        String email = user.getEmail();
        String password = user.getPassword();
        ArrayList<Shift> shifts = user.getShifts();
        Map<String, Paycheck> paychecks = user.getPaychecks();

        user = new User(email,
                password,
                Float.parseFloat(input_travelFee.getText().toString()),
                Float.parseFloat(input_wage.getText().toString()),
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

        goToMenuActivity();
    }

    public void goToMenuActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
