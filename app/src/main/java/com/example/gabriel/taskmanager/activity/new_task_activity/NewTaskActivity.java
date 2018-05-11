package com.example.gabriel.taskmanager.activity.new_task_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gabriel.taskmanager.R;
import com.example.gabriel.taskmanager.data.TaskLab;
import com.example.gabriel.taskmanager.model.Task;


public class NewTaskActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText mTaskNameET;
    private EditText mTaskCommitET;
    private Button mAddNewTaskBT;
    private Button mExitBT;
    private Task mTask;

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
                finishActivity();
            }
        });
    }

    private void initialization()  {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mTaskNameET = findViewById(R.id.task_name_et);
        mTaskCommitET = findViewById(R.id.task_Commit_et);
        mAddNewTaskBT = mToolbar.findViewById(R.id.add_task_bt);
        mExitBT = mToolbar.findViewById(R.id.exit_bt);

        mTask = getIntent().getParcelableExtra("Task");
        if(mTask != null){
            mAddNewTaskBT.setText(R.string.change);
            mTaskNameET.setText(mTask.getmName());
            mTaskCommitET.setText(mTask.getmCommit());
        }
    }

    private void addTask(){
        String name = mTaskNameET.getText().toString().trim();
        String commit = mTaskCommitET.getText().toString().trim();
        if(mTask!=null && !name.isEmpty() && !commit.isEmpty()){
            mTask.setmName(name);
            mTask.setmCommit(commit);
            TaskLab.getTaskLab(NewTaskActivity.this).updateTask(mTask);
        }else if(!name.isEmpty() && !commit.isEmpty() ) {
            TaskLab.getTaskLab(NewTaskActivity.this).addTask(new Task(name,commit));
            mTaskNameET.setText("");
            mTaskCommitET.setText("");
        }else{
                Toast.makeText(this, "Fill the space", Toast.LENGTH_SHORT).show();
        }
    }

    private void finishActivity() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
