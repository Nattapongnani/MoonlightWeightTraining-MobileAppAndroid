package com.example.moonlight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterMyDetails extends ArrayAdapter {

    ArrayList<MyAdapter> arrayDetails;
    Context context;

    public AdapterMyDetails(Context _context, ArrayList<MyAdapter> _details) {
        super(_context, R.layout.card_item_my_details, _details);

        this.context = _context;
        this.arrayDetails = _details;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_item_my_details, parent, false);

        TextView titleView =  view.findViewById(R.id.mExerciseName);
        TextView setsView =  view.findViewById(R.id.mSets);
        TextView weightView =  view.findViewById(R.id.mWeight);
        TextView repsView = view.findViewById(R.id.mReps);
        TextView totalVolumesView = view.findViewById(R.id.mTotalVolumes);

        final MyAdapter item = arrayDetails.get(position);

        titleView.setText(item.exerciseName);
        setsView.setText(String.valueOf(item.sets));
        weightView.setText(String.valueOf(item.weight));
        repsView.setText(String.valueOf(item.reps));
        totalVolumesView.setText(String.valueOf(item.totalVolume));

        return view;
    }
}