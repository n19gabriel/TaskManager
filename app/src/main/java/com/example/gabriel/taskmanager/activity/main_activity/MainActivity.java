package com.example.gabriel.taskmanager.activity.main_activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gabriel.taskmanager.R;
import com.example.gabriel.taskmanager.activity.new_task_activity.NewTaskActivity;
import com.example.gabriel.taskmanager.adapter.TaskAdapter;
import com.example.gabriel.taskmanager.model.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private FloatingActionButton mNewTaskBT;
    private ListView mTaskList;
    private ArrayList<Task> mTasks;
    private TaskAdapter mTaskAdapter;
            ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        mNewTaskBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
                intent.putParcelableArrayListExtra("Tasks", mTasks);
                startActivityForResult(intent,1);
            }
        });
    }

    private void initialization(){
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mNewTaskBT = findViewById(R.id.newTask_bt);
        mTaskList = findViewById(R.id.task_list);
        if(mTasks==null) {
            mTasks = new ArrayList<Task>();
            mTaskAdapter = new TaskAdapter(MainActivity.this, mTasks);
            mTaskList.setAdapter(mTaskAdapter);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {return;}
            mTasks = data.getParcelableArrayListExtra("Tasks");
            mTaskAdapter = new TaskAdapter(MainActivity.this, mTasks);
            mTaskList.setAdapter(mTaskAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("Tasks",mTasks);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTasks = savedInstanceState.getParcelableArrayList("Tasks");
        mTaskAdapter = new TaskAdapter(MainActivity.this, mTasks);
        mTaskList.setAdapter(mTaskAdapter);
    }
}
