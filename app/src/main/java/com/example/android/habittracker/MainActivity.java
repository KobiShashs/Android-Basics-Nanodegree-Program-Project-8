package com.example.android.habittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitDbHelper;
import com.example.android.habittracker.data.HabitContract.HabitEntry;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getName();
    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new HabitDbHelper(this);

        addHabit("Squash", "Sports", 2, 200);
        addHabit("Android", "Computers", 1, 300);
        addHabit("iOS", "Computers", 0, 23);

        read();

        editHabit(230);//iOS
        read();

        deleteDB();
    }

    public void addHabit(String name, String section, int frequency, int cost) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, name);
        values.put(HabitEntry.COLUMN_HABIT_SECTION, section);
        values.put(HabitEntry.COLUMN_HABIT_FREQUENCY, frequency);
        values.put(HabitEntry.COLUMN_HABIT_COST, cost);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
        if (newRowId == -1) {
            Log.w(TAG, "Error with saving habit");
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Log.w(TAG, "habit saved with row id: " + newRowId);
        }
    }

//    public void getAllHabits() {
//        SQLiteDatabase db = mDbHelper.getReadableDatabase();
//
//        String[] projection = {
//                HabitEntry._ID,
//                HabitEntry.COLUMN_HABIT_NAME,
//                HabitEntry.COLUMN_HABIT_SECTION,
//                HabitEntry.COLUMN_HABIT_FREQUENCY,
//                HabitEntry.COLUMN_HABIT_COST
//        };
//
//        Cursor cursor = db.query(
//                HabitEntry.TABLE_NAME,
//                projection,
//                null,
//                null,
//                null,
//                null,
//                null);
//
//        try {
//
//
//            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
//            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
//            int sectionColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_SECTION);
//            int frequencyColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_FREQUENCY);
//            int costColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_COST);
//
//            while (cursor.moveToNext()) {
//                int currentID = cursor.getInt(idColumnIndex);
//                String currentName = cursor.getString(nameColumnIndex);
//                String currentSection = cursor.getString(sectionColumnIndex);
//                int currentFrequency = cursor.getInt(frequencyColumnIndex);
//                int currentCost = cursor.getInt(costColumnIndex);
//
//                String res = "*************************************************************" + "\n\n" +
//                        "ID       : " + String.valueOf(currentID) + "\n\n" +
//                        "Name     : " + currentName + "\n\n" +
//                        "Section  : " + currentSection + "\n\n" +
//                        "Frequency: " + getFrequencyValue(currentFrequency) + "\n\n" +
//                        "Cost     : " + String.valueOf(currentCost) + "\n\n" +
//                        "*************************************************************" + "\n\n";
//                Log.w(TAG, res);
//            }
//        } catch (SQLiteException e) {
//            Log.w(TAG, e.fillInStackTrace());
//        } finally {
//            cursor.close();
//        }
//
//    }
    public Cursor read(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_SECTION,
                HabitEntry.COLUMN_HABIT_FREQUENCY,
                HabitEntry.COLUMN_HABIT_COST
        };

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }

    public String getFrequencyValue(int value) {

        switch (value) {
            case 0: {
                return "UNKNOWN FREQUENCY";
            }
            case 1: {
                return "DAILY FREQUENCY";
            }
            case 2: {
                return "WEEKLY FREQUENCY";
            }
            default: {
                return "UNKNOWN FREQUENCY";
            }
        }
    }

    public void deleteDB() {
        mDbHelper.deleteDatabase(this);

    }

    public void editHabit(int cost) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_COST, cost);
        String where = HabitEntry.COLUMN_HABIT_NAME + "=?";
        String[] whereArgs = {"iOS"};
        long newRowId = db.update(HabitEntry.TABLE_NAME, values, where, whereArgs);
        if (newRowId == -1) {
            Log.w(TAG, "Error with editing");
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Log.w(TAG, "Edit succeed" + newRowId);
        }
    }
}
