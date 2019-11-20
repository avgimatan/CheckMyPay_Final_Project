package com.example.checkmypay;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

class MyBroadcastReceiver extends Application {

    //Firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private User user;

    @Override
    public void onCreate() {
        super.onCreate();

        getUserFromDB();
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
                        startShift();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        // TODO: Send notification that start shift failed

                    }
                });
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
        //buttons.get(4).setText("End Shift");
    }

    public void updateUserInDB() {
        db.collection("Users")
                .document(user.getId())
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Save detailes Successfully", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), " An error has occurred", Toast.LENGTH_SHORT).show());
    }


}
