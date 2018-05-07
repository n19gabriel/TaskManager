package com.example.gabriel.taskmanager.utils;

import android.content.SharedPreferences;

import com.example.gabriel.taskmanager.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Utils {
    public static final String APP_PREFERENCES = "TaskList";

    public void saveListTask(ArrayList<Task> tasks,SharedPreferences sharedPreferences ){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APP_PREFERENCES, serialization(tasks));
        editor.commit();
    }

    public ArrayList<Task> loadListTask(SharedPreferences sharedPreferences ) throws JSONException {
        return deserialization(sharedPreferences.getString(APP_PREFERENCES, ""));
    }

    private String serialization(ArrayList<Task> tasks){
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(tasks);
        return jsonArray.toString();
    }

    private ArrayList<Task> deserialization(String jsontxt) throws JSONException {
            JSONArray jsonArray = new JSONArray(jsontxt);
            ArrayList<Task> tasks = new ArrayList<>();
            for(int i = 0; i< jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("mName");
                String commit = jsonObject.getString("mCommit");
                tasks.add(new Task(name,commit));
            }
            return tasks;
    }
}
