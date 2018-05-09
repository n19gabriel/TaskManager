package com.example.gabriel.taskmanager.utils;

import android.content.SharedPreferences;

import com.example.gabriel.taskmanager.model.Task;

import org.json.JSONException;

import java.util.ArrayList;

public class Utils {
    private JSONhelper jsoNhelper;
    public static final String APP_PREFERENCES = "TaskList";

    public Utils() {
        jsoNhelper = new JSONhelper();
    }

    public void saveListTask(ArrayList<Task> tasks, SharedPreferences sharedPreferences ){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APP_PREFERENCES, jsoNhelper.serialization(tasks));
        editor.commit();
    }

    public ArrayList<Task> loadListTask(SharedPreferences sharedPreferences ) throws JSONException {
        return jsoNhelper.deserialization(sharedPreferences.getString(APP_PREFERENCES, ""));
    }

    public String load(SharedPreferences sharedPreferences ){
        String saveText = sharedPreferences.getString(APP_PREFERENCES,"");
        return saveText;
    }
}
