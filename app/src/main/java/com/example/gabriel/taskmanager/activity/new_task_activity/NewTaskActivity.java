package com.example.gabriel.taskmanager.activity.new_task_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gabriel.taskmanager.R;
import com.example.gabriel.taskmanager.model.Task;
import com.example.gabriel.taskmanager.utils.Utils;

import org.json.JSONException;

import java.util.ArrayList;

public class NewTaskActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText mTaskNameET;
    private EditText mTaskCommitET;
    private Button mAddNewTaskBT;
    private Button mExitBT;
    private ArrayList<Task> mTasks;
    private Utils mUtils;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        initialization();

        mAddNewTaskBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        mExitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putIntent();
            }
        });
    }
    private void initialization(){
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mTaskNameET = findViewById(R.id.task_name_et);
        mTaskCommitET = findViewById(R.id.task_Commit_et);
        mAddNewTaskBT = findViewById(R.id.add_new_task_bt);
        mExitBT = findViewById(R.id.exit_bt);
        mTasks = new ArrayList<>();
        mUtils = new Utils();
        sharedPreferences = getSharedPreferences("ListTask", MODE_PRIVATE);

        mTasks = getIntent().getParcelableArrayListExtra("Tasks");
        //try {
        //    mTasks = mUtils.loadListTask(sharedPreferences);
        //} catch (JSONException e) {
        //    e.printStackTrace();
        //}
    }
    private void addTask(){
        String name = mTaskNameET.getText().toString().trim();
        String commit = mTaskCommitET.getText().toString().trim();
        if(!name.isEmpty() && !commit.isEmpty() ) {
            mTasks.add(new Task(name, commit));
            mTaskNameET.setText("");
            mTaskCommitET.setText("");
        }else{
                Toast.makeText(this, "Fill the space", Toast.LENGTH_SHORT).show();
            }
    }

    private void putIntent(){
        Intent intent = new Intent();
        mUtils.saveListTask(mTasks,sharedPreferences);
        //try {
        //    mTasks = mUtils.loadListTask(sharedPreferences);
        //} catch (JSONException e) {
        //    e.printStackTrace();
        //}
        intent.putParcelableArrayListExtra("Tasks", mTasks);
        setResult(RESULT_OK, intent);
        finish();
    }
}
