package com.example.moonlight;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProgramDetails extends AppCompatActivity {

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
        Intent intent = new Intent(ProgramDetails.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_details);

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

    public void performedClick(View view) {
        // create an AlertDialog that'll come up when text is clicked
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Performed");
        alertDialog.setMessage("Do you want to perform your program ?");

        // set up buttons
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Get current date
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String time = formatter.format(date);

                double volumes = Double.parseDouble(_trainingVolume.getText().toString());
                int session = Integer.parseInt(_sessionCount.getText().toString());

                db.addPerformed(new MyAdapter(time, pid, volumes), session);

                setDetails();

                okPerformed();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // show it
        alertDialog.show();
    }

    public void okPerformed() {

        // create an AlertDialog that'll come up when text is clicked
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setIcon(R.drawable.ic_baseline_check_circle_outline_24);
        alertDialog.setTitle("Performed This Program");

        // set up buttons
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // show it
        alertDialog.show();
    }

    //Menu Button
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_or_edit, menu);
        return true;
    }

    //Menu Click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mDelete) {
            // create an AlertDialog that'll come up when text is clicked
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setIcon(R.drawable.ic_baseline_delete_24);
            alertDialog.setTitle("Delete");
            alertDialog.setMessage("Are you sure to delete this item ?");

            // set up buttons
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.deleteProgram(pid);

                    Intent intent = new Intent(ProgramDetails.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            // show it
            alertDialog.show();
        }

        if (id == R.id.mEdit) {
            Intent intent = new Intent(ProgramDetails.this, EditProgram.class);
            intent.putExtra("pid", String.valueOf(pid));
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}