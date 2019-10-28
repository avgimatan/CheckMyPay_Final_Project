package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class MenuActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        user = (User) getIntent().getSerializableExtra("user");

        //create Grid
        LinearLayout main_layout = findViewById(R.id.main_layout);
        GridLayout gridLayout = createGridLayout(2, 2);
        main_layout.addView(gridLayout);

        for (int i = 0; i < 4; i++ ) {
            Button button = new Button(this);
            button.setBackgroundResource(R.drawable.common_google_signin_btn_text_dark);
            button.setLayoutParams(new ViewGroup.LayoutParams(360,400));
            button.setPadding(10,10,10,10);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            gridLayout.addView(button);
        }


    }


    private GridLayout createGridLayout(int colsNum, int rowsNum) {

        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(colsNum);
        gridLayout.setRowCount(rowsNum);
        gridLayout.setOrientation(GridLayout.HORIZONTAL);

        return gridLayout;
    }

    // check this function
    /*
    public void goToActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }*/// check

    public void goToSetting() {
        Intent intent = new Intent(this, SettingActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    public void goToRate() {
        Intent intent = new Intent(this, RateActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    public void goToPaycheck() {
        Intent intent = new Intent(this, PaycheckActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    public void goToSalary() {
        Intent intent = new Intent(this, SalaryActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
