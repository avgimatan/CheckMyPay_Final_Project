package com.example.checkmypay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RateActivity extends AppCompatActivity {

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
                            input_startDate.setText(String.valueOf(user.getEndDate()));
                        }
                        if(!document.get("endDate").equals("") && !document.get("endDate").equals(null) && document.get("endDate").equals(user.getEndDate())) {
                            input_startDate.setText(String.valueOf(user.getEndDate()));
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "This user is not in DB!", Toast.LENGTH_SHORT).show();
                        isUserExist = false;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "get failed with " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
