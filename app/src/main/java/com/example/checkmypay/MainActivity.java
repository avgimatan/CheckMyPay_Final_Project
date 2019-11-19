package com.example.checkmypay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener, Finals {

    private static boolean isFirstTime = true;
    private User user;
    private ArrayList<Button> buttons;
    private LinearLayout linearLayout, mainLayout;
    private TextView nameText;

    // Location
    private Location currentLocation = null;
    private LocationManager locationManager;

    //UI Elements
    private Button sign_out_btn;

    //Firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            finish();
        } else {
            getUserFromDB();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (User) getIntent().getSerializableExtra("user");

        // Get user from other activities
        sign_out_btn = findViewById(R.id.sign_out_btn);
        sign_out_btn.setOnClickListener(this);

        nameText = findViewById(R.id.text_username_menu);

        //create Linear and Buttons
        mainLayout = findViewById(R.id.main_layout);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        buttons = new ArrayList<>();
        if(!isFirstTime) {
            createButtons();
        }

        // Get location permission
        getGoogleMapsPermissions();
    }

    // Get current user
    private void getUserFromDB() {
        db.collection("Users")
                .document(mAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class); //check how to get user from document
                        String name = String.valueOf(user.getEmail().charAt(0)).toUpperCase() + user.getEmail().split("@")[0].substring(1);
                        nameText.setText("Hello " + name);
                        if(isFirstTime) {
                            createButtons();
                            isFirstTime = false;
                        }
                        else {
                            startShiftEndShiftDecide();
                        }
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
        buttons.add(new Button(this));
        buttons.get(0).setText("My Paycheck");
        buttons.add(new Button(this));
        buttons.get(1).setText("My Rate");
        buttons.add(new Button(this));
        buttons.get(2).setText("My Shifts");
        buttons.add(new Button(this));
        buttons.get(3).setText("This is my work location!");

        int numOfShifts = user.getShifts().size();
        if(numOfShifts == 0) {
            buttons.add(new Button(this));
            buttons.get(4).setText("Start Shift");
        }
        else {
            if (user.getShifts().get(numOfShifts - 1).getEndHour() != null) {
                buttons.add(new Button(this));
                buttons.get(4).setText("Start Shift");
            } else {
                buttons.add(new Button(this));
                buttons.get(4).setText("End Shift");
            }
        }
        for (Button button : buttons) {
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            button.setLayoutParams(params);
            button.setAllCaps(false);
            button.setOnClickListener(this);
            button.setBackgroundResource(R.color.colorPrimary);
            button.setTextColor(getResources().getColor(R.color.white));
            if(button.getText().toString().equals("Start Shift") || button.getText().toString().equals("End Shift")) {
                button.setTypeface(Typeface.create("casual", Typeface.NORMAL), Typeface.BOLD);
                // TODO: set type of this button
            }
            else
                button.setTypeface(Typeface.create("casual", Typeface.NORMAL), Typeface.NORMAL);
            linearLayout.addView(button);
        }
        mainLayout.addView(linearLayout);
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
            switch(clickedButton.getText().toString()) {
                case "My Paycheck":
                    goToActivity(PaycheckActivity.class);
                    break;

                case "My Rate":
                    Intent intent = new Intent(getApplicationContext(), RateActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    //goToActivity(RateActivity.class);
                    break;

                case "My Shifts":
                    goToActivity(ShiftActivity.class);
                    break;

                case "Log out":
                    logOut();
                    break;

                case "Start Shift":
                    startShift();
                    break;

                case "End Shift":
                    endShift();
                    break;

                case "This is my work location!":
                    if(checkPermission()) {
                        setLocation();
                        updateUserInDB();
                    }
                    else
                        Toast.makeText(MainActivity.this, "You should let a permission location first!", Toast.LENGTH_SHORT).show();
                default:
                    // do nothing
                    break;
            }
        }
    }

    public void startShift() {

        Calendar c = Calendar.getInstance();
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        int currentMonth = c.get(Calendar.MONTH);
        int currentHour = c.get(Calendar.HOUR_OF_DAY); // Return the hour in 24 hrs format (ranging from 0-23)
        int currentMinute = c.get(Calendar.MINUTE);
        String currentTime = String.format("%02d:%02d", currentHour, currentMinute);

        ArrayList<Shift> shifts;

        // Start Shift(String hourlyWage, String day, String month, String beginHour, String beginMinute)
        if(user.getShifts() != null) {
            shifts = user.getShifts();
        }
        else {
            shifts = new ArrayList<>();
        }

        shifts.add(new Shift(String.valueOf(user.getHourlyWage()), String.valueOf(currentDay),String.valueOf(currentMonth+1),
                currentTime.split(":")[0], currentTime.split(":")[1]));
        user.setShifts(shifts);
        updateUserInDB();
        buttons.get(4).setText("End Shift");
    }

    public void endShift() {

        Calendar c = Calendar.getInstance();
        int currentHour = c.get(Calendar.HOUR_OF_DAY); // Return the hour in 24 hrs format (ranging from 0-23)
        int currentMinute = c.get(Calendar.MINUTE);
        String currentTime = String.format("%02d:%02d", currentHour, currentMinute);

        int numOfShifts = user.getShifts().size();
        ArrayList<Shift> shifts = user.getShifts();

        Shift oldShift = shifts.get(numOfShifts - 1);
        Shift newShift = new Shift(oldShift.getHourlyWage(), oldShift.getDay(), oldShift.getMonth(), oldShift.getBeginHour(),
                                    currentTime.split(":")[0], oldShift.getBeginMinute(), currentTime.split(":")[1],
                                    user);

        shifts.remove(numOfShifts - 1);
        shifts.add(numOfShifts - 1, newShift);
        user.setShifts(shifts);
        updateUserInDB();
        buttons.get(4).setText("Start Shift");
    }

    public void updateUserInDB() {
        db.collection("Users")
                .document(user.getId())
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Save detailes Successfully", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), " An error has occurred", Toast.LENGTH_SHORT).show());
    }

    public void startShiftEndShiftDecide() {
        int numOfShifts = user.getShifts().size();
        if(numOfShifts == 0) {
            buttons.get(4).setText("Start Shift");
        }
        else {
            if (user.getShifts().get(numOfShifts - 1).getEndHour() != null) {
                buttons.get(4).setText("Start Shift");
            } else {
                buttons.get(4).setText("End Shift");
            }
        }
    }

    public boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void getGoogleMapsPermissions() {
        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};
        if (!checkPermission()) {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void setLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    getApplicationContext().checkSelfPermission(COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        if (currentLocation == null) {
            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        float metersToUpdate = 1;
        long intervalMilliseconds = 1000;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, intervalMilliseconds, metersToUpdate, this);
        Map<String, Double> workLocation = new HashMap<>();
        workLocation.put("lat", currentLocation.getLatitude());
        workLocation.put("lon", currentLocation.getLongitude());
        user.setWorkLocation(workLocation);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.currentLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
