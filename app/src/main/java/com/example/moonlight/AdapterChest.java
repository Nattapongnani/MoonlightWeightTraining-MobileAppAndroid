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

public class AdapterChest extends RecyclerView.Adapter<AdapterChest.ChestViewHolder> {

    String data1[], data2[], data3[];
    int images[];
    Context context;

    public AdapterChest(Context ct, String s1[], String s2[], String s3[], int img[]){
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        images = img;
    }

    @NonNull
    @Override
    public ChestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_chest, parent, false);
        return new ChestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChestViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.myText1.setText(data1[position]);
        holder.myImage.setImageResource(images[position]);

        holder.chestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Des_chestActivity.class);
                intent.putExtra("data1", data1[position]);
                intent.putExtra("data2", data2[position]);
                intent.putExtra("data3", data3[position]);
                intent.putExtra("chestImage", images[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class ChestViewHolder extends RecyclerView.ViewHolder{

        TextView myText1;
        ImageView myImage;
        ConstraintLayout chestLayout;

        public ChestViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.myText1);
            myImage = itemView.findViewById(R.id.myImageView);
            chestLayout = itemView.findViewById(R.id.chestLayout);
        }
    }
}
