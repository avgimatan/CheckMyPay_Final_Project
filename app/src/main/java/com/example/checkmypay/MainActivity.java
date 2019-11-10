package com.example.checkmypay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private HashMap<String, Button> buttons;
    private LinearLayout linearLayout;

    //UI Elements
    private Button sign_out_btn;

    //Firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            finish();
        } else
            getUserFromDB();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get user from other activities
        sign_out_btn = findViewById(R.id.sign_out_btn);
        sign_out_btn.setOnClickListener(this);

        //create Linear and Buttons
        LinearLayout mainLayout = findViewById(R.id.main_layout);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        buttons = new HashMap<>();
        createButtons();
        mainLayout.addView(linearLayout);

    }

    // get current user
    private void getUserFromDB() {
        db.collection("Users")
                .document(mAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class); //check how to get user from document
                        Toast.makeText(MainActivity.this, user.getId(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void createButtons() {

        buttons.put("My Paycheck", new Button(this));
        buttons.put("My Salary", new Button(this));
        buttons.put("My Rate", new Button(this));
        buttons.put("My Shifts", new Button(this));

        buttons.get("My Paycheck").setText("My Paycheck");
        buttons.get("My Salary").setText("My Salary");
        buttons.get("My Rate").setText("My Rate");
        buttons.get("My Shifts").setText("My Shifts");

        for (Button button : buttons.values()) {
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            button.setLayoutParams(params);
            button.setAllCaps(false);
            button.setOnClickListener(this);
            button.setBackgroundResource(R.color.colorPrimary);
            button.setTextColor(getResources().getColor(R.color.white));
            // TODO: set font for each button
            linearLayout.addView(button);
        }

    }

    public void goToActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void logOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        try {
            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(" ")
                    .setMessage("Do you want to sign out?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        try {
                            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                            mGoogleSignInClient.signOut();
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                            finish();
                        } catch (Exception e) {
                            Toast.makeText(this, "Can not sign out", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNeutralButton("No", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        } catch (Exception e) {
            Toast.makeText(this, "Can not display dialog", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view instanceof Button) {
            Button clickedButton = (Button) view;
            if (clickedButton == buttons.get("My Paycheck")) {
                goToActivity(PaycheckActivity.class);
            }
            if (clickedButton == buttons.get("My Salary")) {
                goToActivity(SalaryActivity.class);
            }
            if (clickedButton == buttons.get("My Rate")) {
                Intent intent = new Intent(getApplicationContext(), RateActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                //goToActivity(RateActivity.class);
            }
            if (clickedButton == buttons.get("My Shifts")) {
                goToActivity(ShiftActivity.class);
            }
            if (clickedButton.getId() == R.id.sign_out_btn) {
                logOut();
            }
        }
    }
}
