package com.example.moonlight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WeightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        //Click
        TextView txt_chest = findViewById(R.id.id_chest);
        TextView txt_ab = findViewById(R.id.id_ab);
        TextView txt_back = findViewById(R.id.id_back);
        TextView txt_shoulders = findViewById(R.id.id_shoulders);
        TextView txt_leg = findViewById(R.id.id_leg);
        txt_chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeightActivity.this, Weight_chestActivity.class);
                startActivity(intent);
            }
        });
        txt_ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeightActivity.this, Weight_abActivity.class);
                startActivity(intent);
            }
        });
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeightActivity.this, Weight_backActivity.class);
                startActivity(intent);
            }
        });
        txt_shoulders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeightActivity.this, Weight_shouldersActivity.class);
                startActivity(intent);
            }
        });
        txt_leg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeightActivity.this, Weight_legActivity.class);
                startActivity(intent);
            }
        });


        // Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.weight);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.program:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.weight:
                        return true;
                    case R.id.stats:
                        startActivity(new Intent(getApplicationContext(), StatsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}