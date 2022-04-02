package com.example.moonlight;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewProgram extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    ListView listViewExercise;

    EditText edtProgramName;

    static TextView txtTrainingVolume;

    ArrayList<MyAdapter> selected = new ArrayList<MyAdapter>();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        Intent intent = new Intent(NewProgram.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_program);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("New Program");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        txtTrainingVolume = (TextView) findViewById(R.id.mVolumes);

    }

    public void selectExercise(View view) {
        // create array list for keep select item
        ArrayList<Integer> listSelected = new ArrayList<Integer>();

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Exercise");

        // add a checkbox list
        String[] exercises = {"Barbell", "Dumbbell", "Bar", "aaa", "bbb"};
        builder.setMultiChoiceItems(exercises, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // user selected or remove selected
                if (isChecked) {
                    listSelected.add(which);
                } else if (listSelected.contains(which)) {
                    listSelected.remove(Integer.valueOf(which));
                }
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user ok
                for (Integer i : listSelected) {
                    selected.add(new MyAdapter(exercises[i], 0, 0.0, 0, 0.0));
                }
                Log.d("Process ", "...");
                showSelectExercise();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", null);

        // create and show the alert dialog
        builder.create();
        builder.show();
    }

    private void showSelectExercise(){

        listViewExercise = findViewById(R.id.mListExercise);

        if(selected.size() != 0){
            AdapterMyExercises myExercise = new AdapterMyExercises(this, selected);
            listViewExercise.setAdapter(myExercise);
        }

    }

    public static void updateTrainingVolume(double _trainingVolume){
        txtTrainingVolume.setText(String.valueOf(_trainingVolume));
    }

    //Menu Button
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    //Menu Click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mSave) {
            edtProgramName = findViewById(R.id.mProgramName);
            String _programname = edtProgramName.getText().toString();

            if(_programname == ""){
                _programname = "My Program";
            }

            //Get current date
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String time = formatter.format(date);

            int _workouts = selected.size();

            double _trainingVolume = Double.parseDouble(txtTrainingVolume.getText().toString());

            // add to database
            db.addNewProgram(new MyAdapter(_programname, time, _workouts, _trainingVolume, "-", 0), selected);

            // create an AlertDialog that'll come up when text is clicked
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setIcon(R.drawable.ic_baseline_event_available_24);
            alertDialog.setTitle("Saved New Program");

            // set up buttons
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Log.d("Workouts ", String.valueOf(selected.size()));
                    for(int i = 0; i < selected.size(); i++){
                        Log.d("Data ",
                                String.format("index %d: %s sets: %d weights: %.1f reps: %d total volumes: %.1f",
                                i, selected.get(i).getExerciseName(),
                                        selected.get(i).getSets(),
                                        selected.get(i).getWeight(),
                                        selected.get(i).getReps(),
                                        selected.get(i).getTotalVolumes()));

                    }
                    Intent intent = new Intent(NewProgram.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            // show it
            alertDialog.show();

        }

        return super.onOptionsItemSelected(item);
    }
}