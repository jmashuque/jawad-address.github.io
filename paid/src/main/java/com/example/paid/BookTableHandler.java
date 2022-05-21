package com.example.paid;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BookTableHandler {

    public static final String TABLE_BOOK = "book";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_FIRST = "first";
    public static final String COLUMN_LAST = "last";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_PROVINCE = "province";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_POSTAL = "postal";

    private static final String DATABASE_CREATE = "create table " + TABLE_BOOK + "( " +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_GENDER + " text not null, " +
            COLUMN_FIRST + " text not null, " +
            COLUMN_LAST + " text not null, " +
            COLUMN_ADDRESS + " text not null, " +
            COLUMN_PROVINCE + " text not null, " +
            COLUMN_COUNTRY + " text not null, " +
            COLUMN_POSTAL + " text not null" + ");";

    public static void onCreate(SQLiteDatabase database) {

        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(BookTableHandler.class.getName(), "Upgrading database from version " +
                oldVersion + " to " + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        onCreate(database);
    }
}
