package com.example.gabriel.taskmanager.activity.new_task_activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gabriel.taskmanager.R;
import com.example.gabriel.taskmanager.data.TaskLab;
import com.example.gabriel.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.Locale;


public class NewTaskActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText mTaskNameET;
    private EditText mTaskCommitET;
    private Button mAddNewTaskBT;
    private Button mExitBT;
    private Task mTask;
    private ImageButton mNameVoiceIB;
    private ImageButton mCommitVoiceIB;

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

        mNameVoiceIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillingInVoice(1);
            }
        });

        mCommitVoiceIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillingInVoice(2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK && data != null){
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                mTaskNameET.setText(result.get(0));
            }
        }else if(requestCode == 2){
            if(resultCode == RESULT_OK && data != null){
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                mTaskCommitET.setText(result.get(0));
            }
        }
    }

    private void initialization()  {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mTaskNameET = findViewById(R.id.task_name_et);
        mTaskCommitET = findViewById(R.id.task_Commit_et);
        mAddNewTaskBT = mToolbar.findViewById(R.id.add_task_bt);
        mExitBT = mToolbar.findViewById(R.id.exit_bt);
        mNameVoiceIB = findViewById(R.id.voice_name_bt);
        mCommitVoiceIB = findViewById(R.id.voice_commit_bt);

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
            Task task = new Task(name, commit);
            TaskLab.getTaskLab(NewTaskActivity.this).addTask(task);
            mTaskNameET.setText("");
            mTaskCommitET.setText("");
        }else{
                Toast.makeText(this, "Fill the space", Toast.LENGTH_SHORT).show();
        }
    }

    private void fillingInVoice(int requestCode){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        try{
            startActivityForResult(intent,requestCode);
        }catch (ActivityNotFoundException a){
            Toast.makeText(getApplicationContext(),"Intent problem", Toast.LENGTH_SHORT).show();
        }

    }

    private void finishActivity() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
