package com.example.gabriel.taskmanager.utils;

import com.example.gabriel.taskmanager.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONhelper {

    public String serialization(ArrayList<Task> tasks){
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(tasks);
        return jsonArray.toString();
    }

    public ArrayList<Task> deserialization(String jsontxt) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsontxt);
        ArrayList<Task> tasks = new ArrayList<>();
        tasks = jsonParser(jsonArray);
        return tasks;
    }

    private ArrayList<Task> jsonParser(JSONArray jsonArray) throws JSONException {
        ArrayList<Task> tasks = new ArrayList<>();
        if(tasks!=null) {
            JSONArray jArray = jsonArray.getJSONArray(0);
            for (int index = 0; index < jArray.length(); index++) {
                JSONObject jsonObject = jArray.getJSONObject(index);
                String name = jsonObject.getString("mName");
                String commit = jsonObject.getString("mCommit");
                String startDate = jsonObject.getString("mStartDate");
                String deadline = jsonObject.getString("mDeadline");
                String executionTime = jsonObject.getString("mExecutionTime");
                tasks.add(new Task(name, commit, startDate, deadline, executionTime));
            }
        }
        return tasks;
    }
}
