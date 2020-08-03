package com.example.sabriatialidiet.activities;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sabriatialidiet.R;
import com.example.sabriatialidiet.db.DataBase;
import com.example.sabriatialidiet.utils.MyDate;

import java.util.ArrayList;
import java.util.Map;

public class PickDishActivity extends AppCompatActivity {
    private DataBase db;
    private Map<String, Object> map;
    private ArrayList<Map<String, Object>> data;
    private SimpleAdapter sAdapter;
    private long selectedElementId = -1;
    ListView listView;
    public static MyDate date;
    private static final int SET_WEIGHT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_dish);


        db = DataBase.getDataBase(this);
        data = DataBase.cursorToArrayList(db.getDishes());

        String[] from = new String[]{DataBase.DISH_COLUMN_NAME, DataBase.DISH_COLUMN_CALORIES_PER_100_GM};
        int[] to = new int[]{R.id.db_item_name, R.id.db_item_right_text};

        sAdapter = new SimpleAdapter(this, data, R.layout.database_item, from, to);
        listView = findViewById(R.id.pick_dish_list);
        listView.setAdapter(sAdapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) findViewById(R.id.pick_dish_picked_dish_name)).setText(((TextView) view.findViewById(R.id.db_item_name)).getText().toString());
                selectedElementId = id;
                onSetPickedDishWeight(view);
            }
        });

        findViewById(R.id.pick_dish_OK_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedElementId < 0) {
                    finish();
                    return;
                }
                String pickedDishName = (String) data.get((int) selectedElementId).get(DataBase.DISH_COLUMN_NAME);
                int pickedWeight = Integer.parseInt(((EditText) findViewById(R.id.pick_dish_picked_dish_weight)).getText().toString());
                Cursor cursor = db.getDayDish(date, pickedDishName);
                if (cursor == null)
                    db.addDayDish(date, pickedDishName, pickedWeight);
                else
                    db.updateDayDish(date, pickedDishName, pickedWeight + cursor.getInt(cursor.getColumnIndex(DataBase.DAYS_DISH_COLUMN_WEIGHT)));
                finish();
            }
        });
        findViewById(R.id.pick_dish_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onSetPickedDishWeight(View view) {
        showDialog(SET_WEIGHT);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == SET_WEIGHT) {
            final Dialog dialog = new Dialog(this);


            dialog.setTitle(getString(R.string.enter_dish_weight));
            dialog.setContentView(R.layout.set_weight_dialog);

            Button okButton = dialog.findViewById(R.id.set_weight_dialog_OK_button);
            Button cancelButton = dialog.findViewById(R.id.set_weight_dialog_cancel_button);


            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        EditText weight = dialog.findViewById(R.id.weight_ev);
                        Log.d("looloo", "onClick: " + weight.getText().toString());
                        ((EditText) findViewById(R.id.pick_dish_picked_dish_weight)).setText((weight.getText().toString()));
                        dialog.dismiss();
                    }catch (Exception ex) {
                        ((EditText) findViewById(R.id.pick_dish_picked_dish_weight)).setText("100");
                        dialog.dismiss();
                    }
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            return dialog;
        }
        return super.onCreateDialog(id);
    }
}
