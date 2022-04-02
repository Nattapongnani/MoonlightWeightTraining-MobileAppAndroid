package com.example.moonlight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    ListView listViewPrograms;

    Spinner spnSort;

    int sortBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("My Programs");
        }

        Spinner sortSpinner = (Spinner) findViewById(R.id.mSort);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource
                (this, R.array.sort_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sortSpinner.setAdapter(sortAdapter);

        spnSort = findViewById(R.id.mSort);

        spnSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                sortBy = position;
                showMyPrograms();
                Log.d("Position Spinner",String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                sortBy = 0;
            }

        });

        showMyPrograms();

        // Start Navigation bottom -----------------------------------------------
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.program);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.program:
                        return true;
                    case R.id.weight:
                        startActivity(new Intent(getApplicationContext(), WeightActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.stats:
                        startActivity(new Intent(getApplicationContext(), StatsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        // End Navigation bottom -----------------------------------------------

    }

    //Menu Button
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_programs, menu);
        return true;
    }

    //Menu Click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mAddNew) {
            Intent intent = new Intent(MainActivity.this, NewProgram.class);
            startActivity(intent);
            finish();
        }

        if (id == R.id.mClearAll) {
            // create an AlertDialog that'll come up when text is clicked
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setTitle("Clear All");
            alertDialog.setMessage("If you clear all program, your performed history will clear too");

            // set up buttons
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.clearAll();
                    showMyPrograms();
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



        return super.onOptionsItemSelected(item);
    }


    // show all my programs
    public void showMyPrograms() {
        listViewPrograms = findViewById(R.id.mListPrograms);

        ArrayList<MyAdapter> listProgram = db.getAllPrograms(sortBy);

        AdapterMyPrograms myPrograms = new AdapterMyPrograms(this, listProgram);
        listViewPrograms.setAdapter(myPrograms);

        listViewPrograms.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                int pid = listProgram.get(position).getIdProgram();
                Log.d("Program ID", String.valueOf(pid));

                Intent intent = new Intent(MainActivity.this, ProgramDetails.class);
                intent.putExtra("pid", String.valueOf(pid));
                startActivity(intent);
                finish();
            }
        });
    }
}