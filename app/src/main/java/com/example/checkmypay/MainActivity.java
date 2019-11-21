package com.example.checkmypay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import static android.app.Notification.EXTRA_NOTIFICATION_ID;
import static com.example.checkmypay.App.CHANNEL_1;

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
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    //Notification
    private NotificationManagerCompat mNotificationManager;
    private boolean isStart;

    @Override
    protected void onStart() {
        super.onStart();
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

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mNotificationManager = NotificationManagerCompat.from(this);

        // Get user from other activities
        sign_out_btn = findViewById(R.id.sign_out_btn);
        sign_out_btn.setOnClickListener(this);
        nameText = findViewById(R.id.text_username_menu);

        //create Linear and Buttons
        mainLayout = findViewById(R.id.main_layout);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        buttons = new ArrayList<>();
        if (!isFirstTime) {
            createButtons();
        }


        // Get location permission
        getGoogleMapsPermissions();

        if (checkPermission()) {
            initLocation();
        }
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
                        if (isFirstTime) {
                            createButtons();
                            isFirstTime = false;
                        } else {
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
        buttons.add(new Button(this));
        buttons.get(4).setText("Show route to my work");


        int numOfShifts = user.getShifts().size();
        if (numOfShifts == 0) {
            buttons.add(new Button(this));
            buttons.get(5).setText("Start Shift");
        } else {
            if (user.getShifts().get(numOfShifts - 1).getEndHour() != null) {
                buttons.add(new Button(this));
                buttons.get(5).setText("Start Shift");
            } else {
                buttons.add(new Button(this));
                buttons.get(5).setText("End Shift");
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
            if (button.getText().toString().equals("Start Shift") || button.getText().toString().equals("End Shift")) {
                button.setTypeface(Typeface.create("casual", Typeface.NORMAL), Typeface.BOLD);
            } else
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

    public void goToMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
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
            switch (clickedButton.getText().toString()) {
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
                    sendStartNotification(view);
                    break;

                case "End Shift":
                    endShift();
                    sendEndNotification(view);
                    break;

                case "This is my work location!":
                    if (checkPermission()) {
                        setLocation();
                        updateUserInDB();
                    } else
                        Toast.makeText(MainActivity.this, "You should let a permission location first!", Toast.LENGTH_SHORT).show();
                    break;

                case "Show route to my work":
                    if (user.getWorkLocation() != null) {
                        if (user.getWorkLocation().size() != 0 && checkPermission()) {
                            goToMapsActivity();
                        }
                    }
                    else
                        Toast.makeText(MainActivity.this, "You should set your work location first!", Toast.LENGTH_SHORT).show();
                    break;

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

        if(currentDay <= user.getStartDate()) {

        }

        // Start Shift(String hourlyWage, String day, String month, String beginHour, String beginMinute)
        if (user.getShifts() != null) {
            shifts = user.getShifts();
        } else {
            shifts = new ArrayList<>();
        }

        shifts.add(new Shift(user.getHourlyWage(), String.valueOf(currentDay), String.valueOf(currentMonth + 1),
                currentTime.split(":")[0], currentTime.split(":")[1]));
        user.setShifts(shifts);
        updateUserInDB();
        buttons.get(5).setText("End Shift");
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
        buttons.get(5).setText("Start Shift");
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
        if (numOfShifts == 0) {
            buttons.get(5).setText("Start Shift");
            isStart = true;
        } else {
            if (user.getShifts().get(numOfShifts - 1).getEndHour() != null) {
                buttons.get(5).setText("Start Shift");
                isStart = true;
            } else {
                buttons.get(5).setText("End Shift");
                isStart = false;
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
        Map<String, Double> workLocation = new HashMap<>();
        workLocation.put("lat", currentLocation.getLatitude());
        workLocation.put("lon", currentLocation.getLongitude());
        user.setWorkLocation(workLocation);
        updateUserInDB();
    }

    private void initLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    getApplicationContext().checkSelfPermission(COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        if (currentLocation == null) {
            assert locationManager != null;
            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        float metersToUpdate = 0;
        long intervalMilliseconds = 1000;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, intervalMilliseconds, metersToUpdate, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        float[] results = new float[1];
        if (location != null) {
            this.currentLocation = location;

            /*Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                    user.getWorkLocation().get("lat"), user.getWorkLocation().get("lon"), results);

            if (results[0] <= 100) {
                sendNotification();
                user.setHourlyWage(888);
                updateUserInDB();
            }*/
        }
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

    public void sendStartNotification(View view) {

        String contentText;
        contentText = "You started shift!\nPress here to end this shift";

        Intent snoozeIntent = new Intent(this, MyBroadcastReceiver.class);
        snoozeIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);


        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_1)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Shift Notification")
                .setContentText(contentText)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(contentText))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(snoozePendingIntent)
                .setAutoCancel(true);

        mNotificationManager.notify(1, notification.build());
    }

    public void sendEndNotification(View view) {

        int numOfShifts = user.getShifts().size();
        String contentText = "The shift ended.\nYour profit: " + user.getShifts().get(numOfShifts - 1).getShiftProfit();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_1)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Shift Notification")
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        buttons.get(5).setText("Start Shift");

        mNotificationManager.notify(1, builder.build());
    }
}
