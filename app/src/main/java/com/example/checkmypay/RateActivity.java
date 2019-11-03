package com.example.checkmypay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RateActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private User user;

    private EditText input_wage, input_startDate, input_endDate, input_credits, input_fromHour,
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
        btn_saveDetails = findViewById(R.id.rate_btn_save_details);

        getDetails();

        btn_saveDetails.setOnClickListener(this);

    }

    public void getDetails() {
        DocumentReference userDocument = db.collection("Users").document(user.getEmail());
        userDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if(!document.get("hourlyWage").equals("") && !document.get("hourlyWage").equals(null) && document.get("hourlyWage").equals(user.getHourlyWage())) {
                            input_wage.setText(String.valueOf(user.getHourlyWage()));
                        }
                        if(!document.get("startDate").equals("") && !document.get("startDate").equals(null) && document.get("startDate").equals(user.getStartDate())) {
                            input_startDate.setText(String.valueOf(user.getStartDate()));
                        }
                        if(!document.get("endDate").equals("") && !document.get("endDate").equals(null) && document.get("endDate").equals(user.getEndDate())) {
                            input_endDate.setText(String.valueOf(user.getEndDate()));
                        }
                        if(!document.get("fromHour").equals("") && !document.get("fromHour").equals(null) && document.get("fromHour").equals(user.getShabbatFromHour())) {
                            input_fromHour.setText(String.valueOf(user.getShabbatFromHour()));
                        }
                        if(!document.get("fromMinute").equals("") && !document.get("fromMinute").equals(null) && document.get("fromMinute").equals(user.getShabbatFromMin())) {
                            input_fromMinute.setText(String.valueOf(user.getShabbatFromMin()));
                        }
                        if(!document.get("toHour").equals("") && !document.get("toHour").equals(null) && document.get("toHour").equals(user.getShabbatToHour())) {
                            input_toHour.setText(String.valueOf(user.getShabbatToHour()));
                        }
                        if(!document.get("toMinute").equals("") && !document.get("toMinute").equals(null) && document.get("toMinute").equals(user.getShabbatToMin())) {
                            input_toMinute.setText(String.valueOf(user.getShabbatToMin()));
                        }
                        if(!document.get("providentFund").equals("") && !document.get("providentFund").equals(null) && document.get("providentFund").equals(user.getProvidentFund())) {
                            input_providentFund.setText(String.valueOf(user.getProvidentFund()));
                        }
                        if(!document.get("advancedStudyFund").equals("") && !document.get("advancedStudyFund").equals(null) && document.get("advancedStudyFund").equals(user.getAdvancedStudyFund())) {
                            input_advancedStudyFund.setText(String.valueOf(user.getAdvancedStudyFund()));
                        }
                        if(!document.get("credits").equals("") && !document.get("credits").equals(null) && document.get("credits").equals(user.getCredits())) {
                            input_credits.setText(String.valueOf(user.getCredits()));
                        }
                    }
                } else {
                    Toast.makeText(RateActivity.this, "failed with " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void writeDetailsToDB() {
        CollectionReference users = db.collection("Users");

        Map<String, Object> details = new HashMap<>();
        details.put("email", this.user.getEmail());
        details.put("password", this.user.getPassword());
        details.put("hourlyWage", input_wage);
        details.put("startDate", input_startDate);
        details.put("endDate", input_endDate);
        details.put("fromHour", input_fromHour);
        details.put("fromMinute", input_fromMinute);
        details.put("toHour", input_toHour);
        details.put("toMinute", input_toMinute);
        details.put("providentFund", input_providentFund);
        details.put("advancedStudyFund", input_advancedStudyFund);
        details.put("credits", input_credits);

        users.document(user.getEmail()).set(details);
    }

    @Override
    public void onClick(View v) {
        writeDetailsToDB();
        goToMenuActivity();
    }

    public void goToMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
