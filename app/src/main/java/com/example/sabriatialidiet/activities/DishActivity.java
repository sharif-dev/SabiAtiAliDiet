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
import com.example.sabriatialidiet.entities.Dish;
import com.example.sabriatialidiet.listeners.ToWindowOnClickWithClosing;

import java.util.ArrayList;
import java.util.Map;

public class DishActivity extends AppCompatActivity {
    DataBase db; // object of database

    Map<String, Object> map;
    ArrayList<Map<String, Object>> data; // main data
    SimpleAdapter sAdapter;
    long selectedElementId=-1;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        findViewById(R.id.from_dishes_to_menu).setOnClickListener(new ToWindowOnClickWithClosing(this, MyMenuActivity.class));

        db = DataBase.getDataBase(this); // open DB
        data=DataBase.cursorToArrayList(db.getDishes());

        // collation columns forming
        String[] from = new String[] {DataBase.DISH_COLUMN_NAME, DataBase.DISH_COLUMN_CALORIES_PER_100_GM }; // columns names
        int[] to = new int[] { R.id.db_item_name, R.id.db_item_right_text}; // places to write (View id)

        sAdapter = new SimpleAdapter(this, data ,  R.layout.database_item, from, to);
        listView= findViewById(R.id.dish_list_view);
        listView.setAdapter(sAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedElementId = id;
            }
        });
    }



    public void onDeleteDish(View view)
    {
        if(selectedElementId<0)
            Toast.makeText(this, getString(R.string.pick_dish), Toast.LENGTH_SHORT).show();
        else
        {
            String dishName=(String)data.get((int)selectedElementId).get(DataBase.DISH_COLUMN_NAME);
            db.deleteDish(dishName);
            data.remove((int)selectedElementId);
            selectedElementId=-1;
            sAdapter.notifyDataSetChanged();
        }
    }

    public void onEditDish(View view)
    {
        if (selectedElementId<0) {
            Toast.makeText(this, getString(R.string.pick_dish), Toast.LENGTH_SHORT).show();
            return;
        }
        //fill with new data
        Dish dish = new Dish();
        map=data.get((int)selectedElementId);
        dish.setName(map.get(DataBase.DISH_COLUMN_NAME).toString());
        dish.setCalories(Integer.parseInt(map.get(DataBase.DISH_COLUMN_CALORIES_PER_100_GM).toString()));
        DishesEditorActivity.dish=dish;
        onCreateDish(view);
    }

    public void onCreateDish(View view)
    {
        Intent intent = new Intent(this, DishesEditorActivity.class);
        startActivity(intent); // transfer control to editor
        selectedElementId=-1;
    }

    @Override
    protected void onRestart() {
        data.clear();
        data.addAll(DataBase.cursorToArrayList(db.getDishes()));
        sAdapter.notifyDataSetChanged();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close(); // close DB
    }
}
