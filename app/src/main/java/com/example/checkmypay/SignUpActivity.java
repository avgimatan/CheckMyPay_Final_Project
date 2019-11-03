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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersCollection = db.collection("Users");


    private EditText email, password;
    private Button btn_signup;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.text_signup_email);
        password = findViewById(R.id.text_signup_pass);
        btn_signup = findViewById(R.id.btn_signup_signup);
        btn_signup.setOnClickListener(this);
    }

    public void initUser(EditText email, EditText password) {
        this.user = new User(email.getText().toString(), password.getText().toString());
    }

    public void writeUserToDB() {
        // Create a new user with a email and password
        Map<String, Object> user = new HashMap<>();
        user.put("email", this.user.getEmail());
        user.put("password", this.user.getPassword());
        user.put("hourlyWage", "");
        user.put("startDate", "");
        user.put("endDate", "");
        user.put("fromHour", "");
        user.put("fromMinute", "");
        user.put("toHour", "");
        user.put("toMinute", "");
        user.put("providentFund", "");
        user.put("advancedStudyFund", "");
        user.put("credits", "");

        usersCollection.document(this.user.getEmail()).set(user);

        // Add a new document with a generated ID
        /*db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        goToMenuActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "Signup failed!", Toast.LENGTH_SHORT).show();
                    }
                });*/

    }

    public boolean emailValidation() {
        String emailAddress = email.getText().toString().trim();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            email.setError("please enter valid email address");
            email.requestFocus();
            return false;
        }
        if (email.getText().toString().equals("")) {
            email.setError("please enter email address");
            email.requestFocus();
            return false;
        }
        return true;
    }

    public void goToMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        // if the email is invalid
        if(!emailValidation()) {
            Toast.makeText(this, "Invalid email!", Toast.LENGTH_SHORT).show();
            return;
        }

        initUser(email, password);
        writeUserToDB();
        goToMenuActivity();
    }
}
