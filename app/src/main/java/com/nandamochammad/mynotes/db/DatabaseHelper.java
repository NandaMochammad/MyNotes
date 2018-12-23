package com.nandamochammad.mynotes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nandamochammad.mynotes.entity.NoteModel;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbnoteapp";

    public String TAG = "masuk";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_NOTE,
            DatabaseContract.NoteColumns._ID,
            DatabaseContract.NoteColumns.TITLE,
            DatabaseContract.NoteColumns.DESCRIPTION,
            DatabaseContract.NoteColumns.DATE
    );

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
        Log.d(TAG, "DatabaseHelper.onCreate: DB have Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_NOTE);
        onCreate(db);
        Log.d(TAG, "DatabaseHelper.onUpgrade: DB have Upgraded");
    }
}
