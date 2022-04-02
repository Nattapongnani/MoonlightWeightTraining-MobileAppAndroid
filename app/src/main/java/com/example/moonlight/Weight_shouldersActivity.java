package com.example.moonlight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Weight_shouldersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String s1[], s2[], youtube[];
    int images[] = {R.drawable.c_incline_barbell_bench_press,
            R.drawable.c_flat_barbell_bench_press,
            R.drawable.c_decline_barbell_bench_press,
            R.drawable.c_incline_dumbbell_bench_press,
            R.drawable.c_decline_dumbbell_bench_press,
            R.drawable.c_dumbberll_chest_fly};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_shoulders);

        recyclerView = findViewById(R.id.recyclerView1);
        s1 = getResources().getStringArray(R.array.shoulders);
        s2 = getResources().getStringArray(R.array.shoulders_des);
        youtube = getResources().getStringArray(R.array.shoulders_youtube);
        AdapterShoulders adapterShoulders = new AdapterShoulders(this, s1, s2, youtube, images);
        recyclerView.setAdapter(adapterShoulders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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
    }
}