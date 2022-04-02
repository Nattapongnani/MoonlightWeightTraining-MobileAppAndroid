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

import java.util.ArrayList;

public class EditProgram extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    int pid;

    EditText programName;

    static TextView trainingVolume;

    ListView listViewExercise;

    ArrayList<MyAdapter> selected = new ArrayList<MyAdapter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_program);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Edit Program");
        }

        programName = findViewById(R.id.mProgramName);
        trainingVolume = findViewById(R.id.mTrainingVolume);

        // get program id
        pid = Integer.parseInt(getIntent().getExtras().getString("pid"));

        // set program details
        MyAdapter program = db.getProgram(pid);
        programName.setText(program.getProgramName());
        trainingVolume.setText(String.valueOf(program.getTrainingVolume()));

        // set my exercise from DB in selected array
        selected = db.getDetails(pid);

        showSelectExercise();
        /*selected = AdapterMyEditExercise.arrayExercise;
            Log.d("name", String.valueOf(selected.get(1).getInProgramId()));*/
    }

    private void showSelectExercise(){

        listViewExercise = findViewById(R.id.mListExercise);

        if(selected.size() != 0){
            AdapterMyEditExercise myExercise = new AdapterMyEditExercise(this, selected);
            listViewExercise.setAdapter(myExercise);
        }

    }

    public static void updateTrainingVolume(double _trainingVolume){
        trainingVolume.setText(String.valueOf(_trainingVolume));
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
                    // MyAdapter(int _id, String _exerciseName, int _sets, double _weight, int _reps, double _totalVolume, int _inProgramId)
                    selected.add(new MyAdapter(0, exercises[i], 0, 0.0, 0, 0.0, pid));
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

    //Menu Button
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update, menu);
        return true;
    }

    //Menu Click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mUpdate) {
            programName = findViewById(R.id.mProgramName);
            String _programname = programName.getText().toString();

            if(_programname == ""){
                _programname = "My Program";
            }

            int _workouts = selected.size();

            double _trainingVolume = Double.parseDouble(trainingVolume.getText().toString());

            // add to database
            //db.updateProgram(new MyAdapter(pid, _programname, _trainingVolume), exercise);

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
                    Intent intent = new Intent(EditProgram.this, ProgramDetails.class);
                    intent.putExtra("pid", String.valueOf(pid));
                    startActivity(intent);
                    finish();
                }
            });

            // show it
            alertDialog.show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        Intent intent = new Intent(EditProgram.this, ProgramDetails.class);
        intent.putExtra("pid", String.valueOf(pid));
        startActivity(intent);
        finish();
    }
}