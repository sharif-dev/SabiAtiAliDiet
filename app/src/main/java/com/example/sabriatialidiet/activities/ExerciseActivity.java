package com.example.sabriatialidiet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.sabriatialidiet.R;
import com.example.sabriatialidiet.db.DataBase;
import com.example.sabriatialidiet.entities.Exercise;
import com.example.sabriatialidiet.listeners.ToWindowOnClickWithClosing;

import java.util.ArrayList;
import java.util.Map;

public class ExerciseActivity extends AppCompatActivity {
    DataBase db;
    Map<String, Object> map;
    ArrayList<Map<String, Object>> data; // main data
    SimpleAdapter sAdapter;
    long selectedElementId = -1;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        findViewById(R.id.from_exercises_to_menu).setOnClickListener(new ToWindowOnClickWithClosing(this, MyMenuActivity.class));

        db = DataBase.getDataBase(this); // open DB

        data = DataBase.cursorToArrayList(db.getExercises());

        String[] from = new String[]{DataBase.EXERCISE_COLUMN_NAME};
        int[] to = new int[]{R.id.db_item_name};

        sAdapter = new SimpleAdapter(this, data, R.layout.database_item, from, to);
        listView = (ListView) findViewById(R.id.exercise_list_view);
        listView.setAdapter(sAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedElementId = id;
            }
        });
    }



    public void onDeleteExercise(View view) {
        if (selectedElementId < 0)
            Toast.makeText(this, getString(R.string.pick_exercise), Toast.LENGTH_SHORT).show();
        else {
            String exerciseName = (String) data.get((int) selectedElementId).get(DataBase.EXERCISE_COLUMN_NAME);
            db.deleteExercise(exerciseName);
            data.remove((int) selectedElementId);
            selectedElementId = -1;
            sAdapter.notifyDataSetChanged();
        }
    }

    public void onEditExercise(View view) {
        if (selectedElementId < 0) {
            Toast.makeText(this, getString(R.string.pick_exercise), Toast.LENGTH_SHORT).show();
            return;
        }
        Exercise exercise = new Exercise();
        map = data.get((int) selectedElementId);
        exercise.setName(map.get(DataBase.EXERCISE_COLUMN_NAME).toString());
        exercise.setTimeCoefficient(Integer.parseInt(map.get(DataBase.EXERCISE_COLUMN_TIME_COEFF).toString()));
        exercise.setQuantityCoefficient(Integer.parseInt(map.get(DataBase.EXERCISE_COLUMN_QUANTITY_COEFF).toString()));
        ExerciseEditorActivity.exercise = exercise;
        onCreateExercise(view);
    }

    public void onCreateExercise(View view) {
        Intent intent = new Intent(this, ExerciseEditorActivity.class);
        startActivity(intent);
        selectedElementId = -1;
        ;
    }

    @Override
    protected void onRestart() {
        data.clear();
        data.addAll(DataBase.cursorToArrayList(db.getExercises()));
        sAdapter.notifyDataSetChanged();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
