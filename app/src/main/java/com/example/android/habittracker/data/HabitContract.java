package com.example.android.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by user on 9/1/2016.
 */
public class HabitContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private HabitContract() {}

    /* Inner class that defines the table contents */
    public static class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "hebits";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "name";
        public static final String COLUMN_HABIT_SECTION = "section";
        public static final String COLUMN_HABIT_FREQUENCY = "frequency";
        public static final String COLUMN_HABIT_COST = "cost";

        public static final int FREQUENCY_UNKNOWN =0;
        public static final int FREQUENCY_DAILY =1;
        public static final int FREQUENCY_WEEKLY =2;

    }

}
