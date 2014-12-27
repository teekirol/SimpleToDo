package com.codepath.simpletodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.codepath.simpletodo.ToDoContract.ToDoEntry;

public class ToDoDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ToDo.db";

    public ToDoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE " + ToDoEntry.TABLE_NAME + "(" +
            ToDoEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ToDoEntry.COLUMN_NAME_TEXT + " TEXT," +
            ToDoEntry.COLUMN_NAME_COMPLETED + " BOOLEAN DEFAULT 0)"
        );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Copy table content into new db
        db.execSQL("DROP TABLE IF EXISTS " + ToDoEntry.TABLE_NAME);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}