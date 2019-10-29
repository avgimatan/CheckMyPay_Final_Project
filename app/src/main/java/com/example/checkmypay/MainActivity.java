package com.example.checkmypay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText email, password;
    private TextView btn_signUp;
    private Button btn_login;
    private User user;
    private boolean isUserExist = false, isValidPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        btn_signUp = findViewById(R.id.button_sign_up);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUp();
            }
        });
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

        // if the email is invalid
        if(!emailValidation()) {
            Toast.makeText(this, "Invalid email!", Toast.LENGTH_SHORT).show();
            return;
        }

        initUser(email, password);

        checkIfUserInDB();

        if(isUserExist) {
            checkIfValidPassword();     // if the user exist, check the password
            if(isValidPassword) {
                goToMenuActivity();     // if all right, connect and go to MenuActivity
            }
            else {
                return;     // return if invalid password
            }
        }
        else {
            return;     // return if the user is not exist
        }

        /*TODO: search this user in the DB
            if (exist)
                check the password
                if (valid password)
                    valid user
                    goToMenuActivity()
                else
                    invalid password
                    insert again
            else
                the user is not exist! should sign up first*/

    }

    public void checkIfUserInDB() {

        db.collection("Users").whereEqualTo("email", this.user.getEmail())
                .limit(1).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //boolean isEmpty = task.getResult().isEmpty();
                            if(task.getResult().isEmpty()) {
                                Toast.makeText(MainActivity.this, "This user is not in DB!", Toast.LENGTH_SHORT).show();
                                isUserExist = false;
                            }
                            else {
                                isUserExist = true;
                            }
                        }
                    }
                });
    }

    public void checkIfValidPassword() {

        db.collection("Users").whereEqualTo("password", this.user.getPassword())
                .limit(1).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //boolean isEmpty = task.getResult().isEmpty();
                            if(task.getResult().isEmpty()) {
                                Toast.makeText(MainActivity.this, "Invalid password!", Toast.LENGTH_SHORT).show();
                                isValidPassword = false;
                            }
                            else {
                                isValidPassword = true;
                            }
                        }
                    }
                });
    }

    public void initUser(EditText email, EditText password) {
        this.user = new User(email.getText().toString(), password.getText().toString());
    }

    public void goToSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
