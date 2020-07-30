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

//
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;

public class StatisticActivity extends AppCompatActivity {
//    private BarChart barChart;
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
//        barChart = findViewById(R.id.statistic_histogram);
        db = DataBase.getDataBase(this);
        profile = Profile.getProfile(this);
        bmi = (TextView) findViewById(R.id.bmi_tv);
        bmi.setText(profile.getBMI());

    }
//
//    private void showDiagram() {
//        switch (diagramID) {
//            case DAY_DIAGRAM:
//                showDayDiagram();
//                break;
//            case WEEK_DIAGRAM:
//                showWeekDiagram();
//                break;
//            case MONTH_DIAGRAM:
//                showMonthDiagram();
//                break;
//            case YEAR_DIAGRAM:
//                showYearDiagram();
//                break;
//        }
//    }
//
//    private void showDayDiagram() {
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        ArrayList<String> labels = new ArrayList<>();
//        Day day = db.getDay(new MyDate());
//        int i = 0;
//        for (Map.Entry<Dish, Integer> entry : day.getDishes().entrySet()) {
//            entries.add(new BarEntry(entry.getKey().parseCalories(entry.getValue()), i++));
//            labels.add(entry.getKey().getName());
//        }
//        BarDataSet dataset = new BarDataSet(entries, "");
//        BarData data = new BarData(labels, dataset);
//        barChart.setData(data);
//        barChart.animateY(3000);
//        barChart.setDescription(getString(R.string.day_diagram_description));
//        barChart.notifyDataSetChanged();
//    }
//
//    private void showWeekDiagram() {
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        ArrayList<String> labels = new ArrayList<>();
//        MyDate date = new MyDate();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        Day day;
//        for (int i = 0; i < 7; i++) {
//            calendar.add(Calendar.DAY_OF_WEEK, -6 + i);
//            day = db.getDay(new MyDate(calendar.getTime()));
//            entries.add(new BarEntry(day.getReceivedCalories() - day.getSpentCalories(profile.getWeight()) - profile.getAimCalorie(), i));
//            labels.add(Day.getDayOfWeekByDate(new MyDate(calendar.getTime())));
//            calendar.setTime(date);
//        }
//        BarDataSet dataset = new BarDataSet(entries, "");
//        BarData data = new BarData(labels, dataset);
//        barChart.setData(data);
//        barChart.animateY(2000);
//        barChart.setDescription(getString(R.string.week_diagram_description));
//        barChart.notifyDataSetChanged();
//    }
//
//    private void showMonthDiagram() {
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        ArrayList<String> labels = new ArrayList<>();
//        MyDate date = new MyDate();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        Day day;
//        for (int i = 0; i < 31; i++) {
//            calendar.add(Calendar.DAY_OF_MONTH, -30 + i);
//            day = db.getDay(new MyDate(calendar.getTime()));
//            entries.add(new BarEntry(day.getReceivedCalories() - day.getSpentCalories(profile.getWeight()) - profile.getAimCalorie(), i));
//            labels.add(((Integer) calendar.get(Calendar.DAY_OF_MONTH)).toString());
//            calendar.setTime(date);
//        }
//        BarDataSet dataset = new BarDataSet(entries, "");
//        BarData data = new BarData(labels, dataset);
//        barChart.setData(data);
//        barChart.animateY(2000);
//        barChart.setDescription(getString(R.string.month_diagram_description));
//        barChart.notifyDataSetChanged();
//    }
//
//    private void showYearDiagram() {
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        ArrayList<String> labels = new ArrayList<>();
//        MyDate date = new MyDate();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        date = new MyDate(calendar.getTime());
//        Day day;
//        long calories_per_month;
//        int daysInMonth;
//        for (int j = 0; j < 12; j++) {
//            calories_per_month = 0;
//            daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//            for (int i = 0; i < daysInMonth; i++) {
//                calendar.add(Calendar.DAY_OF_MONTH, i);
//                day = db.getDay(new MyDate(calendar.getTime()));
//                calories_per_month += day.getReceivedCalories() - day.getSpentCalories(profile.getWeight()) - profile.getAimCalorie();
//                calendar.setTime(date);
//            }
//            entries.add(new BarEntry(calories_per_month / calendar.getActualMaximum(Calendar.DAY_OF_MONTH), j));
//            labels.add(Day.getMonthByNumber(calendar.get(Calendar.MONTH)));
//            calendar.add(Calendar.MONTH, -1);
//            date = new MyDate(calendar.getTime());
//        }
//        BarDataSet dataset = new BarDataSet(entries, "");
//        BarData data = new BarData(labels, dataset);
//        barChart.setData(data);
//        barChart.animateY(2000);
//        barChart.setDescription(getString(R.string.year_diagram_description));
//        barChart.notifyDataSetChanged();
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(0, DAY_DIAGRAM, 0, getString(R.string.for_day));
//        menu.add(0, WEEK_DIAGRAM, 1, getString(R.string.for_week));
//        menu.add(0, MONTH_DIAGRAM, 2, getString(R.string.for_month));
//        menu.add(0, YEAR_DIAGRAM, 3, getString(R.string.for_year));
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        diagramID = item.getItemId();
//        showDiagram();
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
