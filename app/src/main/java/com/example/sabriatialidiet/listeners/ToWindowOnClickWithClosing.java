package com.example.sabriatialidiet.listeners;

import android.app.Activity;
import android.view.View;

import com.example.sabriatialidiet.activities.MyMenuActivity;


public class ToWindowOnClickWithClosing extends ToWindowOnClick {
    public ToWindowOnClickWithClosing(Activity fromActivity, Class<? extends Activity> toActivityClass) {
        super(fromActivity, toActivityClass);
    }

    @Override
    public void onClick(View v) {
        if (!MyMenuActivity.isOpen) {
            super.onClick(v);
        }
        fromActivity.finish();
    }
}
