package com.example.sabriatialidiet.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.example.sabriatialidiet.R;
import com.example.sabriatialidiet.db.DataBase;
import com.example.sabriatialidiet.entities.Day;
import com.example.sabriatialidiet.entities.Dish;
import com.example.sabriatialidiet.listeners.ToWindowOnClickWithClosing;
import com.example.sabriatialidiet.preferences.Profile;
import com.example.sabriatialidiet.utils.MyDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;


public class StatisticActivity extends AppCompatActivity {
    DataBase db;
    Profile profile;
    TextView bmi;
    public static final int DAY_DIAGRAM = 0;
    public static final int WEEK_DIAGRAM = 1;
    public static final int MONTH_DIAGRAM = 2;
    public static final int YEAR_DIAGRAM = 3;
    private static int diagramID = DAY_DIAGRAM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        findViewById(R.id.from_statistic_to_menu).setOnClickListener(new ToWindowOnClickWithClosing(this, MyMenuActivity.class));
        db = DataBase.getDataBase(this);
        profile = Profile.getProfile(this);
        bmi = (TextView) findViewById(R.id.bmi_tv);
        bmi.setText(profile.getBMI());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
