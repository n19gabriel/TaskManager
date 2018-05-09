package com.example.gabriel.taskmanager.activity.main_activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.gabriel.taskmanager.R;
import com.example.gabriel.taskmanager.activity.change_task_activity.ChangeTaskActivity;
import com.example.gabriel.taskmanager.activity.new_task_activity.NewTaskActivity;
import com.example.gabriel.taskmanager.adapter.TaskAdapter;
import com.example.gabriel.taskmanager.model.Task;
import com.example.gabriel.taskmanager.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private FloatingActionButton mNewTaskBT;
    private ListView mTaskList;
    private ArrayList<Task> mTasks;
    private TaskAdapter mTaskAdapter;
    private SimpleDateFormat simpleDateFormat;
    private SharedPreferences msSaredPreferences;
    private Utils mUtils;

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

        mTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = mTasks.get(position);
                try {
                    actionStartEndTime(task);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        mTaskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ChangeTaskActivity.class);
                intent.putParcelableArrayListExtra("Tasks", mTasks);
                intent.putExtra("position", position );
                startActivityForResult(intent,1);
                return false;
            }
        });

    }

    private void initialization(){
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.Current_tasks);


        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        mNewTaskBT = findViewById(R.id.newTask_bt);
        mTaskList = findViewById(R.id.task_list);
        if(mTasks==null) {
            mTasks = new ArrayList<>();
            restartAdapter();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {return;}
            mTasks = data.getParcelableArrayListExtra("Tasks");
            restartAdapter();
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
        restartAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id==R.id.action_settings) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void restartAdapter(){
        mTaskAdapter = new TaskAdapter(MainActivity.this, mTasks);
        mTaskList.setAdapter(mTaskAdapter);
    }

    private void actionStartEndTime(Task task) throws ParseException {
        if(task.getStartDate()==null){
            task.setStartDate(getDate());
            restartAdapter();
        }else if(task.getDeadLine()==null){
            task.setDeadLine(getDate());
            Date dateStart = simpleDateFormat.parse(task.getStartDate());
            Date deadline = simpleDateFormat.parse(task.getDeadLine());
            long time =  deadline.getTime()-dateStart.getTime();
            long hour = time / 3600000;
            long minutes = (time % 3600000)/1000/60;
            task.setExecutionTime(hour+":"+minutes);
            restartAdapter();
        }else{
            task.setStartDate(null);
            task.setDeadLine(null);
            task.setExecutionTime(null);
            restartAdapter();
        }
    }

    private String getDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return simpleDateFormat.format(calendar.getTime()).toString();
    }
}
