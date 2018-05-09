package com.example.gabriel.taskmanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gabriel.taskmanager.model.Task;

import java.util.ArrayList;

public class TaskLab {
    private static TaskLab sTaskLab;
    private SQLiteDatabase mDatabase;

    public static TaskLab getTaskLab(Context context) {
        if (sTaskLab == null) {
            sTaskLab = new TaskLab(context);
        }
        return sTaskLab;
    }

    private TaskLab(Context context) {
        Context cont = context.getApplicationContext();
        mDatabase = new TaskDBHelper(cont).getWritableDatabase();
    }

    public void addTask(Task task) {
        ContentValues values = getContentValues(task);
        mDatabase.insert(TaskDBHelper.TaskDBTable.NAME_TABEL, null, values);
    }

    public void removeTask(String id) {
        mDatabase.delete(TaskDBHelper.TaskDBTable.NAME_TABEL,
                TaskDBHelper.TaskDBTable.UUID + " = ?",
                new String[]{id});
    }

    public void removeAllNotes() {
        if (mDatabase != null) mDatabase.delete(TaskDBHelper.TaskDBTable.NAME_TABEL, null, null);
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> notes = new ArrayList<>();
        TaskCursorWrapper cursor = queryTasks(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                notes.add(cursor.getNote());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return notes;

    }

    private static ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskDBHelper.TaskDBTable.UUID, task.getmId());
        values.put(TaskDBHelper.TaskDBTable.NAME, task.getmName());
        values.put(TaskDBHelper.TaskDBTable.COMMITTASK, task.getmCommit());
        values.put(TaskDBHelper.TaskDBTable.STARTDATE, task.getmStartDate());
        values.put(TaskDBHelper.TaskDBTable.DEADLINE, task.getmDeadline());
        values.put(TaskDBHelper.TaskDBTable.EXECUTIONTIME, task.getmExecutionTime());
        return values;
    }

    private TaskCursorWrapper queryTasks(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TaskDBHelper.TaskDBTable.NAME_TABEL,
                null, // Columns - null - choose all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new TaskCursorWrapper(cursor);
    }
}
