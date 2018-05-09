package com.example.gabriel.taskmanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class TaskDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "TaskBase.db";

    public TaskDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TaskDBTable.NAME_TABEL + " (" +
                TaskDBTable._ID + " INTEGER PRIMARY KEY," +
                TaskDBTable.UUID + " INTEGER NOT NULL," +
                TaskDBTable.NAME + " TEXT," +
                TaskDBTable.COMMITTASK + " TEXT," +
                TaskDBTable.STARTDATE + " TEXT," +
                TaskDBTable.DEADLINE + " TEXT," +
                TaskDBTable.EXECUTIONTIME + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskDBTable.NAME_TABEL);
        onCreate(db);
    }

    class TaskDBTable implements BaseColumns {
        static final String NAME_TABEL = "tasks";
        static final String UUID = "uuid";
        static final String NAME = "name";
        static final String COMMITTASK = "commitTask";
        static final String STARTDATE = "startDate";
        static final String DEADLINE = "deadline";
        static final String EXECUTIONTIME = "execuTiontime";
    }
}
