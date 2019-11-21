package com.example.checkmypay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "MyBroadcastReceiver";

    //Firebase
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private static User user;
    private static boolean isStart;

    @Override
    public void onReceive(Context context, Intent intent) {
        final PendingResult pendingResult = goAsync();
        Task asyncTask = new Task(pendingResult, intent);
        asyncTask.execute();
    }


    // Get current user
    private static void getUserFromDB() {
        db.collection("Users")
                .document(mAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                        startShiftEndShiftDecide();
                        if(isStart)
                            startShift();
                        else
                            endShift();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    public static void startShiftEndShiftDecide() {
        int numOfShifts = user.getShifts().size();
        if(numOfShifts == 0) {
            isStart = true;
        }
        else {
            if (user.getShifts().get(numOfShifts - 1).getEndHour() != null) {
                isStart = true;
            } else {
                isStart = false;
            }
        }
    }

    public static void startShift() {

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

        shifts.add(new Shift(user.getHourlyWage(), String.valueOf(currentDay),String.valueOf(currentMonth+1),
                currentTime.split(":")[0], currentTime.split(":")[1]));
        user.setShifts(shifts);
        updateUserInDB();
    }

    public static void endShift() {

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
    }

    public static void updateUserInDB() {
        db.collection("Users")
                .document(user.getId())
                .set(user)
                .addOnSuccessListener(aVoid -> {
                  Log.d("", "Save detailes Successfully");
                }).addOnFailureListener(e -> Log.d(""," An error has occurred"));
    }


    private static class Task extends AsyncTask<String, Integer, String> {

        private final PendingResult pendingResult;
        private final Intent intent;

        private Task(PendingResult pendingResult, Intent intent) {
            this.pendingResult = pendingResult;
            this.intent = intent;
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder sb = new StringBuilder();
            sb.append("Action: " + intent.getAction() + "\n");
            sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
            String log = sb.toString();
            Log.d(TAG, log);

            getUserFromDB();

            return log;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Must call finish() so the BroadcastReceiver can be recycled.
            pendingResult.finish();
        }
    }


}
