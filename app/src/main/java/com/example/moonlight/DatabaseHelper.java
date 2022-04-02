package com.example.moonlight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "weightTrainingDB";

    public static final String TABLE_MY_PROGRAMS = "myProgramsTB";
    public static final String TABLE_MY_EXERCISES = "myExercisesTB";
    public static final String TABLE_MY_PERFORMED = "myPerformedTB";

    // myProgramsTB Columns names
    private static final String KEY_ID_myProgramsTB = "p_id";
    private static final String KEY_PROGRAM_NAME = "program_name";
    private static final String KEY_CREATE_DATE = "create_date";
    private static final String KEY_WORKOUTS = "workouts";
    private static final String KEY_TRAINING_VOLUME = "training_vol";
    private static final String KEY_LAST_PERFORMED = "last_performed";
    private static final String KEY_SESSION_COUNT = "session_count";

    // myExercisesTB Columns names
    private static final String KEY_ID_myExercisesTB = "e_id";
    private static final String KEY_EXERCISE = "exercise";
    private static final String KEY_SETS = "sets";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_REPS = "reps";
    private static final String KEY_TOTAL_VOLUMES = "total_volumes";
    private static final String KEY_IN_myProgramsTB = "p_id";

    // myPerformedTB Columns names
    private static final String KEY_ID_myPerformedTB = "per_id";
    private static final String KEY_PERFORM_DATE = "perform_date";
    private static final String KEY_FROM_myProgramsTB = "p_id";
    private static final String KEY_VOLUMES = "volumes";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_MY_PROGRAMS_TB = "CREATE TABLE " + TABLE_MY_PROGRAMS +
                "(" + KEY_ID_myProgramsTB + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_PROGRAM_NAME + " TEXT," +
                KEY_CREATE_DATE + " TEXT," +
                KEY_WORKOUTS + " INTEGER," +
                KEY_TRAINING_VOLUME + " DOUBLE," +
                KEY_LAST_PERFORMED + " TEXT," +
                KEY_SESSION_COUNT + " INTEGER" + ")";

        String CREATE_MY_EXERCISES_TB = "CREATE TABLE " + TABLE_MY_EXERCISES +
                "(" + KEY_ID_myExercisesTB + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_EXERCISE + " TEXT," +
                KEY_SETS + " INTEGER," +
                KEY_WEIGHT + " DOUBLE," +
                KEY_REPS + " INTEGER," +
                KEY_TOTAL_VOLUMES  + " DOUBLE," +
                KEY_IN_myProgramsTB + " INTEGER" + ")";

        String CREATE_MY_PERFORMED_TB = "CREATE TABLE " + TABLE_MY_PERFORMED +
                "(" + KEY_ID_myPerformedTB + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_PERFORM_DATE + " TEXT," +
                KEY_FROM_myProgramsTB + " INTEGER," +
                KEY_VOLUMES  + " DOUBLE" + ")";

        db.execSQL(CREATE_MY_PROGRAMS_TB);
        db.execSQL(CREATE_MY_EXERCISES_TB);
        db.execSQL(CREATE_MY_PERFORMED_TB);
    }

    // Upgrading database (drop older table if existed, & create table again)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MY_PROGRAMS );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MY_EXERCISES );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MY_PERFORMED );
        onCreate(db);
    }

    // Adding new program
    public void addNewProgram(MyAdapter program, ArrayList<MyAdapter> exercise) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues program_values = new ContentValues();

        program_values.put(KEY_PROGRAM_NAME, program.getProgramName());
        program_values.put(KEY_CREATE_DATE, program.getCreateDate());
        program_values.put(KEY_WORKOUTS, program.getWorkouts());
        program_values.put(KEY_TRAINING_VOLUME, program.getTrainingVolume());
        program_values.put(KEY_LAST_PERFORMED, program.getLastPerformed());
        program_values.put(KEY_SESSION_COUNT, program.getSessionCount());

        // Inserting Row
        long id_program = db.insert(TABLE_MY_PROGRAMS , null, program_values);

        Log.d("ROW ID ", String.valueOf(id_program));

        ContentValues exercise_values = new ContentValues();

        for(int i = 0; i < exercise.size(); i++){
            exercise_values.put(KEY_EXERCISE, exercise.get(i).getExerciseName());
            exercise_values.put(KEY_SETS, exercise.get(i).getSets());
            exercise_values.put(KEY_WEIGHT, exercise.get(i).getWeight());
            exercise_values.put(KEY_REPS, exercise.get(i).getReps());
            exercise_values.put(KEY_TOTAL_VOLUMES, exercise.get(i).getTotalVolumes());
            exercise_values.put(KEY_IN_myProgramsTB, id_program);

            // Inserting Row
            db.insert(TABLE_MY_EXERCISES , null, exercise_values);
        }

        db.close(); // Closing database connection
    }

    // Adding Performed
    public void addPerformed(MyAdapter performed, int _session) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues performed_values = new ContentValues();

        performed_values.put(KEY_PERFORM_DATE, performed.getPerformedDate());
        performed_values.put(KEY_FROM_myProgramsTB, performed.getFromProgramId());
        performed_values.put(KEY_VOLUMES, performed.getVolumes());

        // Inserting Row
        long id_performed = db.insert(TABLE_MY_PERFORMED , null, performed_values);

        Log.d("ROW ID ", String.valueOf(id_performed));

        // Updating single program
        int session = _session + 1;
        String sql = "UPDATE " + TABLE_MY_PROGRAMS +" SET " + KEY_LAST_PERFORMED + " = '" + performed.getPerformedDate()
            + "', " + KEY_SESSION_COUNT + " = " + session + " WHERE " + KEY_ID_myProgramsTB + " = " + performed.getFromProgramId();

        db.execSQL(sql);

        // Closing database connection
        db.close();
    }

    // Getting All Programs
    public ArrayList<MyAdapter> getAllPrograms(int sortBy) {

        ArrayList<MyAdapter> list = new ArrayList<MyAdapter>();
        String selectQuery;
        if ( sortBy == 0){ // last create
            selectQuery = "SELECT * FROM " + TABLE_MY_PROGRAMS + " ORDER BY " + KEY_ID_myProgramsTB + " DESC";
        } else if ( sortBy == 1) { // first create
            selectQuery = "SELECT * FROM " + TABLE_MY_PROGRAMS + " ORDER BY " + KEY_ID_myProgramsTB + " ASC";
        } else if ( sortBy == 2) { // a-z
            selectQuery = "SELECT * FROM " + TABLE_MY_PROGRAMS + " ORDER BY " + KEY_PROGRAM_NAME + " ASC";
        }else {
            selectQuery = "SELECT * FROM " + TABLE_MY_PROGRAMS + " ORDER BY " + KEY_PROGRAM_NAME + " DESC";
        }

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MyAdapter obj = new MyAdapter();
                obj.setIdProgram(Integer.parseInt(cursor.getString(0)));
                obj.setProgramName(cursor.getString(1));
                obj.setCreateDate(cursor.getString(2));
                obj.setWorkouts(cursor.getInt(3));
                obj.setTrainingVolume(cursor.getDouble(4));
                obj.setLastPerformed(cursor.getString(5));
                obj.setSessionCount(cursor.getInt(6));

                // Adding to list
                list.add(obj);
            } while (cursor.moveToNext());
        }

        // return list
        return list;
    }

    // Getting single program
    public MyAdapter getProgram(int id) {

        String selectQuery = "SELECT * FROM " + TABLE_MY_PROGRAMS + " WHERE " + KEY_ID_myProgramsTB + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        MyAdapter programDetails = new MyAdapter(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getDouble(4),
                cursor.getString(5),
                cursor.getInt(6));

        // return program
        return programDetails;
    }

    // Getting All Exercise In Program
    public ArrayList<MyAdapter> getDetails(int id) {

        ArrayList<MyAdapter> list = new ArrayList<MyAdapter>();

        String selectQuery = "SELECT * FROM " + TABLE_MY_EXERCISES + " WHERE " + KEY_IN_myProgramsTB + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MyAdapter obj = new MyAdapter();
                obj.setIdExercise(cursor.getInt(0));
                obj.setExerciseName(cursor.getString(1));
                obj.setSets(cursor.getInt(2));
                obj.setWeight(cursor.getDouble(3));
                obj.setReps(cursor.getInt(4));
                obj.setTotalVolume(cursor.getDouble(5));
                obj.setInProgramId(cursor.getInt(6));

                // Adding to list
                list.add(obj);
            } while (cursor.moveToNext());
        }

        // return list
        return list;
    }

    // Getting All Logs
    public ArrayList<MyAdapter> getAllWorkoutLogs(int _sortBy) {

        ArrayList<MyAdapter> list = new ArrayList<MyAdapter>();

        String selectQuery;

        if ( _sortBy == 0){ // last performed
            selectQuery = "SELECT * FROM " + TABLE_MY_PERFORMED + " ORDER BY " + KEY_ID_myPerformedTB + " DESC";
        } else { // first performed
            selectQuery = "SELECT * FROM " + TABLE_MY_PERFORMED + " ORDER BY " + KEY_ID_myPerformedTB + " ASC";
        }

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MyAdapter obj = new MyAdapter();
                obj.setIdPerformed(cursor.getInt(0));
                obj.setPerformedDate(cursor.getString(1));
                obj.setFromProgramId(cursor.getInt(2));
                obj.setVolumes(cursor.getDouble(3));

                // Adding to list
                list.add(obj);
            } while (cursor.moveToNext());
        }

        // return list
        return list;
    }

    // Getting Programs Count
    public int getCountPrograms() {
        String countQuery = "SELECT * FROM " + TABLE_MY_PROGRAMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();
        // return count
        return count;
    }

    // Getting Workouts Count
    public int getCountWorkouts() {
        String countQuery = "SELECT * FROM " + TABLE_MY_EXERCISES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();
        // return count
        return count;
    }

    // Getting Sum TrainingVolume
    public double getSumTrainingVolume() {
        double mySum = 0.0;

        String sql = "SELECT SUM(" + KEY_TRAINING_VOLUME + ") FROM " + TABLE_MY_PROGRAMS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            mySum = cursor.getInt(0);
        }

        cursor.close();

        return mySum;
    }

    // Getting Sum Session Count
    public int getSumSession() {
        int mySum = 0;

        String sql = "SELECT SUM(" + KEY_SESSION_COUNT + ") FROM " + TABLE_MY_PROGRAMS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            mySum = cursor.getInt(0);
        }

        cursor.close();

        return mySum;
    }

    // Getting Sum Performed Volumes
    public double getSumPerformedVolumes() {
        double mySum = 0.0;

        String sql = "SELECT SUM(" + KEY_VOLUMES + ") FROM " + TABLE_MY_PERFORMED;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            mySum = cursor.getInt(0);
        }

        cursor.close();

        return mySum;
    }

    // Getting Program (Top 5 Most Volumes)
    public ArrayList<MyAdapter> getMostVolumes() {

        ArrayList<MyAdapter> list = new ArrayList<MyAdapter>();

        String selectQuery = "SELECT * FROM " + TABLE_MY_PROGRAMS + " ORDER BY " + KEY_TRAINING_VOLUME + " DESC LIMIT 5";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MyAdapter obj = new MyAdapter();
                obj.setIdProgram(Integer.parseInt(cursor.getString(0)));
                obj.setProgramName(cursor.getString(1));
                obj.setCreateDate(cursor.getString(2));
                obj.setWorkouts(cursor.getInt(3));
                obj.setTrainingVolume(cursor.getDouble(4));
                obj.setLastPerformed(cursor.getString(5));
                obj.setSessionCount(cursor.getInt(6));

                // Adding to list
                list.add(obj);
            } while (cursor.moveToNext());
        }

        // return list
        return list;
    }

    // Updating Program
    public void updateProgram(MyAdapter program, ArrayList<MyAdapter> exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "UPDATE " + TABLE_MY_PROGRAMS +" SET " + KEY_PROGRAM_NAME + " = '" + program.getProgramName()
                + "', " + KEY_TRAINING_VOLUME + " = " + program.getTrainingVolume() + " WHERE " + KEY_ID_myProgramsTB + " = " + program.getIdProgram();

        db.execSQL(sql);


        // Closing database connection
        db.close();
    }

    // Clearing All
    public void clearAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_MY_PERFORMED);
        db.execSQL("DELETE FROM "+ TABLE_MY_EXERCISES);
        db.execSQL("DELETE FROM "+ TABLE_MY_PROGRAMS);
        db.close();
    }

    // Deleting single program
    public void deleteProgram(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql_program = "DELETE FROM " + TABLE_MY_PROGRAMS + " WHERE " + KEY_ID_myProgramsTB + " = " + id;
        String sql_exercise = "DELETE FROM " + TABLE_MY_EXERCISES + " WHERE " + KEY_IN_myProgramsTB + " = " + id;
        String sql_performed = "DELETE FROM " + TABLE_MY_PERFORMED + " WHERE " + KEY_FROM_myProgramsTB + " = " + id;

        db.execSQL(sql_program);
        db.execSQL(sql_exercise);
        db.execSQL(sql_performed);

        db.close();
    }
}
