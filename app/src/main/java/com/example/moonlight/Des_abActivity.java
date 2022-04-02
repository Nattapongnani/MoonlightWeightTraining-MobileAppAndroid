package com.example.moonlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Des_abActivity extends AppCompatActivity {

    ImageView mainImageView;
    TextView title, description;

    String data1, data2, data3;
    int myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_des_ab);

        mainImageView = findViewById(R.id.mainImageView);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);

        getData();
        setData();

        mainImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data3));
                startActivity(webIntent);
            }
        });
    }

    private void getData() {
        if(getIntent().hasExtra("abImage") &&
                getIntent().hasExtra("data1") &&
                getIntent().hasExtra("data2") &&
                getIntent().hasExtra("data3")){
            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            data3 = getIntent().getStringExtra("data3");
            myImage = getIntent().getIntExtra("abImage", 1);
        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        title.setText(data1);
        description.setText(data2);
        mainImageView.setImageResource(myImage);
    }
}