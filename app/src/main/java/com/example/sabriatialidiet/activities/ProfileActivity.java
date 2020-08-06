package com.example.sabriatialidiet.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.sabriatialidiet.R;
import com.example.sabriatialidiet.listeners.ToWindowOnClickWithClosing;
import com.example.sabriatialidiet.profile.Profile;

public class ProfileActivity extends AppCompatActivity {
    Profile profile;
    TextView optimalCalorieNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profile = Profile.getProfile(this);
        findViewById(R.id.from_profile_to_menu).setOnClickListener(new ToWindowOnClickWithClosing(this, MyMenuActivity.class));

        ((EditText) findViewById(R.id.profile_age)).setText(String.valueOf(profile.getAge()));
        ((EditText) findViewById(R.id.profile_height)).setText(String.valueOf(profile.getHeight()));
        ((EditText) findViewById(R.id.profile_weight)).setText(String.valueOf(profile.getWeight()));
        ((RadioButton) findViewById(R.id.profile_woman_radio_button)).setChecked(!profile.getGender());
        ((EditText) findViewById(R.id.profile_aim_kal_number)).setText(String.valueOf(profile.getCalorie_to_lose()));

    }

    public void onGenderRadioButtonClick(View view) {
        if (view.getId() ==  R.id.profile_man_radio_button ){
            profile.setGender(this, true);
        }else {
            profile.setGender(this, false);

        }


    }

    public void onHeightChangeButtonClick(View view) {
        profile.setHeight(this, Integer.parseInt(((EditText) view).getText().toString()));

    }

    public void onWeightChangeButtonClick(View view) {
        profile.setWeight(this, Integer.parseInt(((EditText) view).getText().toString()));

    }

    public void onAgeChangeButtonClick(View view) {
        profile.setAge(this, Integer.parseInt(((EditText) view).getText().toString()));

    }

    public void onAimCalorieChangeButtonClick(View view) {
        profile.setAimCalorie(this, Integer.parseInt(((EditText) view).getText().toString()));

    }

    @Override
    public void onDestroy() {
        profile.setHeight(this, Integer.parseInt(((EditText) findViewById(R.id.profile_height)).getText().toString()));
        profile.setWeight(this, Integer.parseInt(((EditText) findViewById(R.id.profile_weight)).getText().toString()));
        profile.setAge(this, Integer.parseInt(((EditText) findViewById(R.id.profile_age)).getText().toString()));
        profile.setAimCalorie(this, Integer.parseInt(((EditText) findViewById(R.id.profile_aim_kal_number)).getText().toString()));
        profile.saveData(this);
        super.onDestroy();
    }
}
