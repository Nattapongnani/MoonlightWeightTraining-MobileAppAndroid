package com.example.moonlight;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarViewHolder extends RecyclerView.ViewHolder {

    public final View parentView;
    public final TextView txtDayOfMonth;

    public CalendarViewHolder(@NonNull View itemView) {
        super(itemView);

        txtDayOfMonth = itemView.findViewById(R.id.mDay);
        parentView = itemView.findViewById(R.id.mParentView);
    }
}
