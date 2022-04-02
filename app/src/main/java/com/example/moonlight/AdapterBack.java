package com.example.moonlight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterBack extends RecyclerView.Adapter<AdapterBack.BackViewHolder> {

    String data1[], data2[], data3[];
    int images[];
    Context context;

    public AdapterBack(Context ct, String s1[], String s2[], String s3[], int img[]){
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        images = img;
    }

    @NonNull
    @Override
    public BackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_back, parent, false);
        return new BackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BackViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.myText1.setText(data1[position]);
        holder.myImage.setImageResource(images[position]);

        holder.backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Des_backActivity.class);
                intent.putExtra("data1", data1[position]);
                intent.putExtra("data2", data2[position]);
                intent.putExtra("data3", data3[position]);
                intent.putExtra("backImage", images[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class BackViewHolder extends RecyclerView.ViewHolder{

        TextView myText1;
        ImageView myImage;
        ConstraintLayout backLayout;

        public BackViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.myText1);
            myImage = itemView.findViewById(R.id.myImageView);
            backLayout = itemView.findViewById(R.id.backLayout);
        }
    }
}