package com.example.moonlight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdapterMyPrograms extends ArrayAdapter {

    ArrayList<MyAdapter> arrayPrograms;

    Context context;

    public AdapterMyPrograms(@NonNull Context _context, ArrayList<MyAdapter> _programs) {
        super(_context, R.layout.card_item_my_program, _programs);

        this.arrayPrograms = _programs;
        this.context = _context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item_my_program, parent, false);
        TextView titleView =  view.findViewById(R.id.mProgramName);
        TextView workoutView =  view.findViewById(R.id.mWorkouts);
        TextView trainingVolumeView =  view.findViewById(R.id.mTrainingVolume);
        TextView lastPerformedView = view.findViewById(R.id.mLastPerformed);

        final MyAdapter item = arrayPrograms.get(position);

        titleView.setText(item.programName);
        workoutView.setText(String.valueOf(item.workouts));
        trainingVolumeView.setText(String.valueOf(item.trainingVolume));
        lastPerformedView.setText(String.valueOf(item.lastPerformed));

        return view;
    }

}
