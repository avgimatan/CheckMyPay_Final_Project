package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText email, password;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.text_signup_email);
        password = findViewById(R.id.text_signup_pass);

        // TODO: Check email validation

        initUser(email, password);

        writeUserToDB();

    }

    public void initUser(EditText email, EditText password) {
        this.user = new User(email.getText().toString(), password.getText().toString());
    }

    public void writeUserToDB() {
        // Create a new user with a email and password
        Map<String, Object> user = new HashMap<>();
        user.put("email", email.getText().toString());
        user.put("password", password.getText().toString());

    }
}
