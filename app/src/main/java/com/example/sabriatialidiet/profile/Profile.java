package com.example.sabriatialidiet.profile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.sabriatialidiet.activities.ProfileActivity;

public class Profile {
    private boolean gender = true;
    private int age = 18;
    private int height = 175;
    private int weight = 70;
    private int calorie_to_lose = 1500;
    private static Profile profile = null;

    private Profile(Context context) {
        SharedPreferences sPref = context.getSharedPreferences("profile_preferences", Context.MODE_PRIVATE);
        if (!sPref.contains("age")) {
            SharedPreferences.Editor editor = sPref.edit();
            editor.putBoolean("gender", gender);
            editor.putInt("age", age);
            editor.putInt("height", height);
            editor.putInt("weight", weight);
            calorie_to_lose = 100;
            editor.putInt("aimCalorie", calorie_to_lose);
            editor.apply();
        } else {
            gender = sPref.getBoolean("gender", gender);
            age = sPref.getInt("age", age);
            height = sPref.getInt("height", height);
            weight = sPref.getInt("weight", weight);
            calorie_to_lose = sPref.getInt("aimCalorie", calorie_to_lose);
        }
    }

    public static Profile getProfile(Context context) {
        if (profile == null) {
            profile = new Profile(context);
        }
        return profile;
    }

    public String getBMI(){

        float result;
        result =(float) (getWeight()) * 10000 /( getHeight()*getHeight());
        if (result < 16.0){
            return " Severely Underweight ";
        }
        if (result<18.4){
            return "Underweight";
        }
        if (result<24.9){
            return "Normal";
        }
        if (result<29.9){
            return "Overweight";
        }
        if (result<34.9){
            return "Moderately Obese";
        }
        if (result<39.9){
            return "Severely Obese";
        }
        return "Morbidly Obese";
    }



    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public boolean getGender() {
        return gender;
    }

    public int getCalorie_to_lose() {
        return calorie_to_lose;
    }

    public void setGender(Activity activity, boolean gender) {
        if (activity instanceof ProfileActivity) {
            this.gender = gender;
        }
    }

    public void setAge(Activity activity, int age) {
        if (activity instanceof ProfileActivity) {
            this.age = age;
        }
    }

    public void setWeight(Activity activity, int weight) {
        if (activity instanceof ProfileActivity) {
            this.weight = weight;
        }
    }

    public void setHeight(Activity activity, int height) {
        if (activity instanceof ProfileActivity) {
            this.height = height;
        }
    }

    public void setAimCalorie(Activity activity, int aimCalorie) {
        if (activity instanceof ProfileActivity) {
            this.calorie_to_lose = aimCalorie;
        }
    }

    public void saveData(Context context) {
        SharedPreferences sPref = context.getSharedPreferences("profile_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean("gender", gender);
        editor.putInt("age", age);
        editor.putInt("height", height);
        editor.putInt("weight", weight);
        editor.putInt("aimCalorie", calorie_to_lose);
        editor.commit();
    }
}
