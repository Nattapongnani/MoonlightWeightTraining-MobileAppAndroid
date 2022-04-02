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

public class AdapterMyWorkoutLogs extends ArrayAdapter {

    DatabaseHelper db = new DatabaseHelper(getContext());

    ArrayList<MyAdapter> arrayWorkoutLogs;

    Context context;

    public AdapterMyWorkoutLogs(@NonNull Context _context, ArrayList<MyAdapter> _workoutLogs) {
        super(_context, R.layout.card_item_my_workout_logs, _workoutLogs);

        this.arrayWorkoutLogs = _workoutLogs;
        this.context = _context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item_my_workout_logs, parent, false);
        TextView volumesView = view.findViewById(R.id.mVolumes);
        TextView programView =  view.findViewById(R.id.mProgramName);
        TextView dateView =  view.findViewById(R.id.mDate);


        final MyAdapter item = arrayWorkoutLogs.get(position);

        MyAdapter program = db.getProgram(item.fromProgramId); // find program name by id

        volumesView.setText(String.format("%.1f kg", item.getVolumes()));
        programView.setText(program.getProgramName());
        dateView.setText(item.performedDate);

        return view;
    }

}
