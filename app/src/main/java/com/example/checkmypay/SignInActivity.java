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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private EditText email, password;
    private TextView btn_signUp;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        btn_signUp = findViewById(R.id.button_sign_up);
        btn_login = findViewById(R.id.btn_login);
        btn_signUp.setOnClickListener(view -> goToActivity(SignUpActivity.class));
        btn_login.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        signIn();
    }

    private void signIn() {

        if (!emailValidation()) {
            Toast.makeText(this, "Invalid email!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Welcome " + email.getText().toString(), Toast.LENGTH_SHORT).show();
                        goToActivity(MainActivity.class);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid user", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void goToActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
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
