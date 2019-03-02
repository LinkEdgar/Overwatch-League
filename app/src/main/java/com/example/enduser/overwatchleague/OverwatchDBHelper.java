package com.example.enduser.overwatchleague;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OverwatchDBHelper extends SQLiteOpenHelper{

        public static final String DATABASE_NAME = "food.db";

        public static final int DATABASE_VERSION = 1;
        public OverwatchDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String SQL_CREATE_FOOD_TABLE =
                    "CREATE TABLE " + OverwatchDbContract.TeamEntry.TABLE_NAME + " (" +
                            OverwatchDbContract.TeamEntry.COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY," +
                            OverwatchDbContract.TeamEntry.COLUMN_NAME_FACEBOOK + " TEXT," +
                            OverwatchDbContract.TeamEntry.COLUMN_NAME_HOME_LOCATION + " TEXT,"+
                            OverwatchDbContract.TeamEntry.COLUMN_NAME_ICON + " TEXT," +
                            OverwatchDbContract.TeamEntry.COLUMN_NAME_TEAM_NAME + " TEXT," +
                            OverwatchDbContract.TeamEntry.COLUMN_NAME_YOUTUBE + " TEXT," +
                            OverwatchDbContract.TeamEntry.COLUMN_NAME_PRIMARY_COLOR + " TEXT," +
                            OverwatchDbContract.TeamEntry.COLUMN_NAME_SECONDARY_COLOR + " TEXT," +
                            OverwatchDbContract.TeamEntry.COLUMN_NAME_INSTAGRAM + " TEXT)";
            sqLiteDatabase.execSQL(SQL_CREATE_FOOD_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OverwatchDbContract.TeamEntry.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
}
