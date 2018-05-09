package com.example.gabriel.taskmanager.activity.main_activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.gabriel.taskmanager.data.TaskLab;
import com.example.gabriel.taskmanager.model.Task;
import com.example.gabriel.taskmanager.utils.MenuUtils;
import com.example.gabriel.taskmanager.utils.Utils;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity  {

    private Toolbar mToolbar;
    private FloatingActionButton mNewTaskBT;
    private ListView mTaskList;
    private ArrayList<Task> mTasks;
    private TaskAdapter mTaskAdapter;
    private SimpleDateFormat simpleDateFormat;
    private SharedPreferences msSaredPreferences;
    private Utils mUtils;
    private ContextMenuDialogFragment mContextMenu;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        mNewTaskBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
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

        mContextMenu.setItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view, int i) {
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });
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
        }else if(id == R.id.menu_more) {
            mContextMenu.show(fragmentManager,"tag");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialization(){
        //ToolBar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.Current_tasks);
        mContextMenu = MenuUtils.setupMenu(this);
        fragmentManager = getSupportFragmentManager();
        //
        mNewTaskBT = findViewById(R.id.newTask_bt);
        mTaskList = findViewById(R.id.task_list);
        //
        mTasks = new ArrayList<>();
        mTasks = TaskLab.getTaskLab(MainActivity.this).getTasks();
        restartAdapter();
        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    }

    private void restartAdapter(){
        mTaskAdapter = new TaskAdapter(MainActivity.this, mTasks);
        mTaskList.setAdapter(mTaskAdapter);
    }

    private void actionStartEndTime(Task task) throws ParseException {
        if(task.getmStartDate()==null){
            task.setmStartDate(getDate());
            restartAdapter();
        }else if(task.getmDeadline()==null){
            task.setmDeadline(getDate());
            Date dateStart = simpleDateFormat.parse(task.getmStartDate());
            Date deadline = simpleDateFormat.parse(task.getmDeadline());
            long time =  deadline.getTime()-dateStart.getTime();
            long hour = time / 3600000;
            long minutes = (time % 3600000)/1000/60;
            task.setmExecutionTime(hour+":"+minutes);
            restartAdapter();
        }else{
            task.setmStartDate(null);
            task.setmDeadline(null);
            task.setmExecutionTime(null);
            restartAdapter();
        }
    }

    private String getDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return simpleDateFormat.format(calendar.getTime());
    }


}
