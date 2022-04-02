package com.example.moonlight;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class AdapterMyExercises extends ArrayAdapter {

    ArrayList<MyAdapter> arraySelected;
    Context context;

    public static double trainingVolume;

    public AdapterMyExercises(Context _context, ArrayList<MyAdapter> _selected) {
        super(_context, R.layout.card_item_my_exercise, _selected);

        this.context = _context;
        this.arraySelected = _selected;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item_my_exercise, parent, false);

        TextView titleView =  view.findViewById(R.id.mExerciseName);
        TextView setsView =  view.findViewById(R.id.mSets);
        TextView weightView =  view.findViewById(R.id.mWeight);
        TextView repsView = view.findViewById(R.id.mReps);
        TextView totalVolumesView = view.findViewById(R.id.mTotalVolumes);

        ImageButton editBtn = view.findViewById(R.id.mEdit);
        ImageButton deleteBtn = view.findViewById(R.id.mDelete);

        final MyAdapter item = arraySelected.get(position);

        titleView.setText(item.exerciseName);
        setsView.setText(String.valueOf(item.sets));
        weightView.setText(String.valueOf(item.weight));
        repsView.setText(String.valueOf(item.reps));
        totalVolumesView.setText(String.valueOf(item.totalVolume));

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit(position);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(position);
            }
        });

        // calculate training volume
        calTrainingVolume();

        return view;
    }

    private void edit(final int position) {
        // create an AlertDialog that'll come up when text is clicked
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        // create view of dialog for layout xml
        View promptsView = LayoutInflater.from(getContext()).inflate(R.layout.alert_edit_exercise,null);

        TextView exerciseName = (TextView) promptsView.findViewById(R.id.mExerciseName);
        EditText input_sets = (EditText) promptsView.findViewById(R.id.mSets);
        EditText input_weight = (EditText) promptsView.findViewById(R.id.mWeight);
        EditText input_reps = (EditText) promptsView.findViewById(R.id.mReps);

        // set promptsView to alertdialog builder
        alertDialog.setView(promptsView);

        // marks value
        MyAdapter item = arraySelected.get(position);

        exerciseName.setText(item.exerciseName);
        input_sets.setText(String.valueOf(item.sets));
        input_weight.setText(String.valueOf(item.weight));
        input_reps.setText(String.valueOf(item.reps));

        // set up buttons
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String _exerciseName = item.exerciseName;
                int _sets = Integer.parseInt(String.valueOf(input_sets.getText()));
                double _weight = Double.parseDouble(String.valueOf(input_weight.getText()));
                int _reps= Integer.parseInt(String.valueOf(input_reps.getText()));
                double _totalVolume = _sets * _weight * _reps;

                // change value
                arraySelected.set(position, new MyAdapter(_exerciseName, _sets, _weight, _reps, _totalVolume));

                // calculate new training volume
                calTrainingVolume();

                // update list
                notifyDataSetChanged();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // show it
        alertDialog.show();
    }

    public void delete(final int position) {
        // create an AlertDialog that'll come up when text is clicked
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        // set title and message
        alertDialog.setIcon(R.drawable.ic_baseline_do_not_disturb_on_24);
        alertDialog.setTitle("Remove");
        alertDialog.setMessage("Are you sure to remove this item ?");

        // set up buttons
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // remove from list
                remove(arraySelected.get(position));

                // calculate new training volume
                calTrainingVolume();

                //update list
                notifyDataSetChanged();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Log.d("Process ", String.format("training volume = %.1f", getTrainingVolume()));
                dialog.cancel();
            }
        });

        // show it
        alertDialog.show();
    }

    public void calTrainingVolume(){
        trainingVolume = 0.0;
        if( arraySelected.size() != 0){
            for (int i = 0; i < arraySelected.size(); i++){
                trainingVolume = trainingVolume + arraySelected.get(i).totalVolume;
            }
        }
        NewProgram.updateTrainingVolume(trainingVolume);
    }

}