package com.example.moonlight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SummaryViewProgramDetails extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    int pid;

    TextView _programName;
    TextView _createDate;
    TextView _workouts;
    TextView _trainingVolume;
    TextView _lastPerformed;
    TextView _sessionCount;

    ListView listMyExercise;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        Intent intent = new Intent(SummaryViewProgramDetails.this, StatsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_view_program_details);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Program Details");
        }

        _programName = findViewById(R.id.mProgramName);
        _createDate = findViewById(R.id.mCreateDate);
        _workouts = findViewById(R.id.mWorkouts);
        _trainingVolume = findViewById(R.id.mTrainingVolume);
        _lastPerformed = findViewById(R.id.mLastPerformed);
        _sessionCount = findViewById(R.id.mSessionCount);

        // get program id
        pid = Integer.parseInt(getIntent().getExtras().getString("pid"));

        setDetails();
    }

    public void setDetails() {
        // set program details
        MyAdapter program = db.getProgram(pid);
        Log.d("Program Name", program.getProgramName());

        _programName.setText(program.getProgramName());
        _createDate.setText(program.getCreateDate());
        _workouts.setText(String.valueOf(program.getWorkouts()));
        _trainingVolume.setText(String.valueOf(program.getTrainingVolume()));
        _lastPerformed.setText(program.getLastPerformed());
        _sessionCount.setText(String.valueOf(program.getSessionCount()));

        // set exercise details
        ArrayList<MyAdapter> exercise = db.getDetails(pid);

        AdapterMyDetails myExercise = new AdapterMyDetails(this, exercise);
        listMyExercise = findViewById(R.id.mListExercise);
        listMyExercise.setAdapter(myExercise);
    }

}