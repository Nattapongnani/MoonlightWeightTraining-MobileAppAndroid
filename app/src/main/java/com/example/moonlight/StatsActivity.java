package com.example.moonlight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    TextView _totalPrograms;
    TextView _totalWorkouts;
    TextView _totalTrainingVolume;

    ListView listViewPrograms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Summary");
        }

        _totalPrograms = findViewById(R.id.mTotalPrograms);
        _totalWorkouts = findViewById(R.id.mTotalWorkouts);
        _totalTrainingVolume = findViewById(R.id.mTotalTrainingVolume);

        int totalPrograms = db.getCountPrograms();
        int totalWorkouts = db.getCountWorkouts();
        double totalTrainingVolume = db.getSumTrainingVolume();

        _totalPrograms.setText(String.valueOf(totalPrograms));
        _totalWorkouts.setText(String.valueOf(totalWorkouts));
        _totalTrainingVolume.setText(String.valueOf(totalTrainingVolume));

        showMostVolumes();


        // Start Navigation -------------------------------------
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.stats);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.program:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.weight:
                        startActivity(new Intent(getApplicationContext(), WeightActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.stats:
                        return true;
                }
                return false;
            }
        });
        // End Navigation -------------------------------------
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

        if (id == R.id.mWorkoutLogs) {
            Intent intent = new Intent(StatsActivity.this, WorkoutLogs.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showMostVolumes() {
        listViewPrograms = findViewById(R.id.mListMostVolumes);

        ArrayList<MyAdapter> listProgram = db.getMostVolumes();

        AdapterMyPrograms myPrograms = new AdapterMyPrograms(this, listProgram);

        listViewPrograms.setAdapter(myPrograms);

        listViewPrograms.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                int pid = listProgram.get(position).getIdProgram();

                Intent intent = new Intent(StatsActivity.this, SummaryViewProgramDetails.class);
                intent.putExtra("pid", String.valueOf(pid));
                startActivity(intent);
                finish();
            }
        });
    }
}






















