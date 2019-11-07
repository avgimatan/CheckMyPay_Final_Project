package com.example.checkmypay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

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

        initFirebase();

        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        btn_signUp = findViewById(R.id.button_sign_up);
        btn_login = findViewById(R.id.btn_login);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUp();
            }
        });

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
                readUserFromDB();
                goToMenuActivity();     // if all right, connect and go to MenuActivity
            }
            else {
                return;     // return if invalid password
            }
        }
        else {
            return;     // return if the user is not exist
        }

    }

    private void readUserFromDB() {
        DocumentReference userDocument = db.collection("Users").document(user.getEmail());
        userDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        user.setHourlyWage(Float.parseFloat(document.get("hourlyWage").toString()));
                        user.setTravelFee(Float.parseFloat(document.get("travelFee").toString()));
                        user.setStartDate(Integer.parseInt(document.get("startDate").toString()));
                        user.setEndDate(Integer.parseInt(document.get("endDate").toString()));
                        user.setShabbatFromHour(Integer.parseInt(document.get("fromHour").toString()));
                        user.setShabbatFromMin(Integer.parseInt(document.get("fromMinute").toString()));
                        user.setShabbatToHour(Integer.parseInt(document.get("toHour").toString()));
                        user.setShabbatToMin(Integer.parseInt(document.get("toMinute").toString()));
                        user.setProvidentFund(Float.parseFloat(document.get("providentFund").toString()));
                        user.setAdvancedStudyFund(Float.parseFloat(document.get("advancedStudyFund").toString()));
                        user.setCredits(Float.parseFloat(document.get("credits").toString()));
                        user.setShifts((ArrayList<Shift>) document.get("shifts"));
                        user.setPaychecks((HashMap<String, Paycheck>) document.get("paychecks"));

                        // read all attributes of user in DB
                    }
                } else {
                    Toast.makeText(MainActivity.this, "failed with " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // check this function
    /*public void checkIfUserInDB() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getInstance().getReference();

        ref.child("users").child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // use "username" already exists
                    // Let the user know he needs to pick another username.
                } else {
                    // User does not exist. NOW call createUserWithEmailAndPassword
                    mAuth.createUserWithPassword();
                    // Your previous code here.

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }*/

    public void initFirebase() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    public void checkIfUserInDB() {

        // user document == user input from txt area
        DocumentReference userDocument = db.collection("Users").document(email.getText().toString());
        userDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        isUserExist = true;
                        return;
                    } else {
                        Toast.makeText(MainActivity.this, "This user is not in DB!", Toast.LENGTH_SHORT).show();
                        isUserExist = false;
                        return;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "get failed with " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void checkIfValidPassword() {

        DocumentReference userDocument = db.collection("Users").document(user.getEmail());
        userDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if(document.get("password").equals(user.getPassword())) {
                            isValidPassword = true;
                            return;
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Invalid password!", Toast.LENGTH_SHORT).show();
                            isValidPassword = false;
                            return;
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

        /*db.collection("Users").whereEqualTo("password", this.user.getPassword())
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
                });*/
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return false;
    }

}
