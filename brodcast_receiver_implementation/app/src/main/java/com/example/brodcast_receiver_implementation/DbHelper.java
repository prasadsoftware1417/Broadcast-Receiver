package com.example.brodcast_receiver_implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "numberDb1";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE = "CREATE TABLE " + DbContract.TABLE_NAME + "(ID integer primary key autoincrement," + DbContract.INCOMING_NUMBER + " text );";
    private static final String DROP = "DROP TABLE IF EXISTS " + DbContract.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP);
        onCreate(db);
    }

    public void saveNumber(String number, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.INCOMING_NUMBER, number);
        database.insert(DbContract.TABLE_NAME, null, contentValues);
    }

    public Cursor readNumber(SQLiteDatabase database) {
        String[] projection = {"ID", DbContract.INCOMING_NUMBER};

        return (database.query(DbContract.TABLE_NAME, projection, null, null, null, null, null));

    }
}