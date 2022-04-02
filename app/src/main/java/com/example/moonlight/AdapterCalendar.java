package com.example.moonlight;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class AdapterCalendar extends RecyclerView.Adapter<CalendarViewHolder> {

    ArrayList<LocalDate> arrayDays;

    public AdapterCalendar(ArrayList<LocalDate> _days, TrainingActivity trainingActivity) {
        this.arrayDays = _days;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_cell,parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        // week view
        layoutParams.height = (int) parent.getHeight();



        return new CalendarViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        LocalDate date = arrayDays.get(position);
        if(date == null){
            holder.txtDayOfMonth.setText("");
        } else {
            holder.txtDayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalendarUtils.selectedDate)){

            }
        }

    }

    @Override
    public int getItemCount() {
        return arrayDays.size();
    }

}
