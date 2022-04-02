package com.example.moonlight;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarUtils {

    public static LocalDate selectedDate;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<LocalDate> arrayDaysInMonth(LocalDate date){

        ArrayList<LocalDate> days = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = CalendarUtils.selectedDate .withDayOfMonth(1);

        int daysOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++){
            if((i <= daysOfWeek) || (i > daysInMonth + daysOfWeek)) {
                days.add(null);
            }else{
            days.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(),i - daysOfWeek));
            }
        }
        return days;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<LocalDate> arrayDaysInWeek(LocalDate date){

        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = sundayForDate(selectedDate);
        LocalDate endDate = current.plusWeeks(1);

        while (current.isBefore(endDate)){
            {
               days.add(current);
               current = current.plusDays(1);
            }
        }

        return days;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static LocalDate sundayForDate(LocalDate current) {
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while (current.isAfter(oneWeekAgo)){
            {
                if(current.getDayOfWeek() == DayOfWeek.SUNDAY)
                    return current;

                current = current.minusDays(1);
            }
        }
        return null;
    }
}
