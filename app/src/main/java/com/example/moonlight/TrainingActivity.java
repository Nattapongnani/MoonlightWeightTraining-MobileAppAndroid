package com.example.moonlight;

import static com.example.moonlight.CalendarUtils.arrayDaysInWeek;
import static com.example.moonlight.CalendarUtils.monthYearFromDate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class TrainingActivity extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    TextView _totalSessionCount;
    TextView _performedVolumes;

    TextView txtMonthYear;
    RecyclerView reCalendarView;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Training Activity");
        }

        setInfo();

        initWidgets();

        CalendarUtils.selectedDate = LocalDate.now();

        setWeekView();
    }

    private void setInfo() {
        _totalSessionCount = findViewById(R.id.mTotalSessionCount);
        _performedVolumes = findViewById(R.id.mPerformedVolumes);

        int totalSessionCount = db.getSumSession();
        double performedVolumes = db.getSumPerformedVolumes();

        _totalSessionCount.setText(String.valueOf(totalSessionCount));
        _performedVolumes.setText(String.valueOf(performedVolumes));
    }

    private void initWidgets()
    {
        reCalendarView = findViewById(R.id.mCalendarView);
        txtMonthYear = findViewById(R.id.mMonthYear);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView(){
        txtMonthYear.setText(monthYearFromDate(CalendarUtils.selectedDate ));
        ArrayList<LocalDate> days = arrayDaysInWeek(CalendarUtils.selectedDate );


        AdapterCalendar adapterCalendar = new AdapterCalendar(days, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),7);
        reCalendarView.setLayoutManager(layoutManager);
        reCalendarView.setAdapter(adapterCalendar);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previous(View view){
        CalendarUtils.selectedDate  = CalendarUtils.selectedDate .minusWeeks(1);
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void next(View view){
        CalendarUtils.selectedDate  = CalendarUtils.selectedDate .plusWeeks(1);
        setWeekView();
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
            Intent intent = new Intent(TrainingActivity.this, StatsActivity.class);
            startActivity(intent);
            finish();
        }

        if (id == R.id.mWorkoutLogs) {
            Intent intent = new Intent(TrainingActivity.this, WorkoutLogs.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}