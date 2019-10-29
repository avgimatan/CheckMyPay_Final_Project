package com.example.checkmypay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private Map<String,Button> buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        user = (User) getIntent().getSerializableExtra("user");
        buttons = new HashMap<>();

        //create Grid
        LinearLayout mainLayout = findViewById(R.id.main_layout);
        GridLayout gridLayout = createGridLayout(2, 2);
        mainLayout.addView(gridLayout);

        buttons.put("My Paycheck",new Button(this));
        buttons.put("My Salary",new Button(this));
        buttons.put("My Rate",new Button(this));
        buttons.put("My Setting",new Button(this));

        buttons.get("My Paycheck").setText("My Paycheck");
        buttons.get("My Salary").setText("My Salary");
        buttons.get("My Rate").setText("My Rate");
        buttons.get("My Setting").setText("My Setting");


        for (Button button : buttons.values())
        {
            button.setLayoutParams(new ViewGroup.LayoutParams(400,400));
            button.setAllCaps(false);
            button.setOnClickListener(this);
            gridLayout.addView(button);
        }


    }


    private GridLayout createGridLayout(int colsNum, int rowsNum) {

        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(colsNum);
        gridLayout.setRowCount(rowsNum);
        gridLayout.setOrientation(GridLayout.HORIZONTAL);


/*        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout parentRelativeLayout = new LinearLayout(this);
        parentRelativeLayout.setLayoutParams(layoutParams);
        parentRelativeLayout.setGravity(Gravity.CENTER);
        parentRelativeLayout.addView(gridLayout);*/

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

    @Override
    public void onClick(View view) {
        if(view instanceof Button) {
            Button clickedButton = (Button)findViewById(view.getId());

            if (clickedButton.equals(buttons.get("My Paycheck"))){
                goToPaycheck();
            } if (clickedButton == buttons.get("My Salary")) {
                goToSalary();
            } if (clickedButton == buttons.get("My Rate")) {
                goToRate();
            } if (clickedButton == buttons.get("My Setting")) {
                goToSetting();
            }
        }
    }
}
