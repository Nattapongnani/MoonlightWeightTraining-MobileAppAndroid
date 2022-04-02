package com.example.moonlight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class WorkoutLogs extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    ListView listViewWorkoutLogs;

    Spinner spnSort;

    int sortBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_logs);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Workout Logs");
        }

        Spinner sortSpinner = (Spinner) findViewById(R.id.mSort);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource
                (this, R.array.sort_array_2, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sortSpinner.setAdapter(sortAdapter);

        spnSort = findViewById(R.id.mSort);

        spnSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                sortBy = position;
                showWorkoutLogs();
                Log.d("Position Spinner",String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                sortBy = 0;
            }

        });

        showWorkoutLogs();
    }

    // show all logs
    public void showWorkoutLogs() {
        listViewWorkoutLogs = findViewById(R.id.mListWorkoutLogs);

        ArrayList<MyAdapter> listWorkoutLogs = db.getAllWorkoutLogs(sortBy);

        AdapterMyWorkoutLogs myWorkoutLogs = new AdapterMyWorkoutLogs(this, listWorkoutLogs);
        listViewWorkoutLogs.setAdapter(myWorkoutLogs);

    }

    //Menu Button
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_state, menu);
        return true;
    }

    //Menu Click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mSummary) {
            Intent intent = new Intent(WorkoutLogs.this, StatsActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}