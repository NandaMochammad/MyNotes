package com.nandamochammad.mynotes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nandamochammad.mynotes.entity.NoteModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.nandamochammad.mynotes.db.DatabaseContract.NoteColumns.DATE;
import static com.nandamochammad.mynotes.db.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.nandamochammad.mynotes.db.DatabaseContract.NoteColumns.TITLE;
import static com.nandamochammad.mynotes.db.DatabaseContract.TABLE_NOTE;

public class NoteHelper {
    private static String DATABASE_TABLE = TABLE_NOTE;
    private Context context;
    private DatabaseHelper dbHelper;

    public static String TAG = "masuk";

    private SQLiteDatabase db;

    public NoteHelper(Context context){
        this.context = context;
    }

    public NoteHelper open() throws SQLException{
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return  this;
    }

    public void close(){
        dbHelper.close();
    }

    public ArrayList<NoteModel> query(){
        ArrayList<NoteModel> arrayList = new ArrayList<NoteModel>();
        Cursor cursor = db.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID +" DESC",
                null);
        cursor.moveToFirst();
        NoteModel note;
        if(cursor.getCount()>0){
            do{
                note = new NoteModel();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                note.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                note.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                arrayList.add(note);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(NoteModel note){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(TITLE, note.getTitle());
        initialValues.put(DESCRIPTION, note.getDescription());
        initialValues.put(DATE, note.getDate());
        Log.d(TAG, "NoteHelper.insert: Data have inserted\nJudul = "+note.getTitle()+
                "\nDescription = "+note.getDescription()+
                "\nDate = "+note.getDate());
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(NoteModel note){
        ContentValues args = new ContentValues();
        args.put(TITLE, note.getTitle());
        args.put(DESCRIPTION, note.getDescription());
        args.put(DATE, note.getDate());
        Log.d(TAG, "NoteHelper.update: Data have updated\nJudul = "+note.getTitle()+
                "\nDescription = "+note.getDescription()+
                "\nDate = "+note.getDate());
        return db.update(DATABASE_TABLE, args, _ID + "= '" + note.getId() + "'", null);
    }

    public int delete(int id){
        Log.d(TAG, "NoteHelper.delete: Data have deletedted\nID = "+id);
        return db.delete(TABLE_NOTE, _ID + " = '"+id+"'", null);
    }
}
