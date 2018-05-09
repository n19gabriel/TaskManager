package com.example.gabriel.taskmanager.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.gabriel.taskmanager.model.Task;

public class TaskCursorWrapper extends CursorWrapper {

    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getNote() {
        String uuidString = getString(getColumnIndex(TaskDBHelper.TaskDBTable.UUID));
        String name = getString(getColumnIndex(TaskDBHelper.TaskDBTable.NAME));
        String commit = getString(getColumnIndex(TaskDBHelper.TaskDBTable.COMMITTASK));
        String startDate = (getString(getColumnIndex(TaskDBHelper.TaskDBTable.STARTDATE)));
        String deadline = (getString(getColumnIndex(TaskDBHelper.TaskDBTable.DEADLINE)));
        String executionTime = (getString(getColumnIndex(TaskDBHelper.TaskDBTable.EXECUTIONTIME)));

        Task task = new Task(name,commit,startDate,deadline,executionTime);
        task.setmId(uuidString);

        return task;
    }
}
