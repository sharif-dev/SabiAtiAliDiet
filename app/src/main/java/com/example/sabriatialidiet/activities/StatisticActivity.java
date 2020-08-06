package com.example.sabriatialidiet.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import com.example.sabriatialidiet.R;
import com.example.sabriatialidiet.db.DataBase;
import com.example.sabriatialidiet.listeners.ToWindowOnClickWithClosing;
import com.example.sabriatialidiet.profile.Profile;


public class StatisticActivity extends AppCompatActivity {
    DataBase db;
    Profile profile;
    TextView bmi;


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
