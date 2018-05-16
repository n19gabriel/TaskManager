package com.example.gabriel.taskmanager.activity.main_activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.gabriel.taskmanager.R;
import com.example.gabriel.taskmanager.activity.new_task_activity.NewTaskActivity;
import com.example.gabriel.taskmanager.activity.settings_activity.SettingsActivity;
import com.example.gabriel.taskmanager.adapter.RVTaskAdapter;
import com.example.gabriel.taskmanager.data.TaskLab;
import com.example.gabriel.taskmanager.model.Task;
import com.example.gabriel.taskmanager.utils.MenuUtils;
import com.example.gabriel.taskmanager.utils.MenuUtilsSort;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity  {

    private Toolbar mToolbar;
    private FloatingActionButton mNewTaskBT;
    private RecyclerView mTaskList;
    private ArrayList<Task> mTasks;
    private RVTaskAdapter rvTaskAdapter;
    private ContextMenuDialogFragment mContextMenuSort;
    private ContextMenuDialogFragment mContextMenu;
    private FragmentManager fragmentManager;
    private mAsyncTask asyncTask;
    private boolean mDialogDeleteAllStan;
    private boolean twice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        mNewTaskBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTask();
            }
        });

        mContextMenuSort.setItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view, int i) {
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        sortArrayTask(mTasks,0);
                        break;
                    case 2:
                        sortArrayTask(mTasks,1);
                        break;
                    case 3:
                        sortArrayTask(mTasks,2);
                        break;
                    case 4:
                        sortArrayTask(mTasks,3);
                        break;
                }
            }
        });
        mContextMenu.setItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view, int i) {
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        addNewTask();
                        break;
                    case 2:
                        break;
                    case 3:
                        deleteAllTasks();
                        break;
                    case 4:
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        finishActivity();
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        asyncTask = new mAsyncTask();
        asyncTask.execute();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null && requestCode != RESULT_OK ) {return;}
        asyncTask = new mAsyncTask();
        asyncTask.execute();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("mDialogDeleteAllStan", mDialogDeleteAllStan );
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mDialogDeleteAllStan = savedInstanceState.getBoolean("mDialogDeleteAllStan");
        if(mDialogDeleteAllStan){
            deleteAllTasks();
        }
    }

    @Override
    public void onBackPressed() {
        if(twice){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
        final RelativeLayout relativeLayout = findViewById(R.id.main_rl);
        Snackbar.make(relativeLayout, "Please press BACK again to exit", Snackbar.LENGTH_LONG)
                .show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
            }
        },3000);
        twice = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id==R.id.menu) {
            mContextMenu.show(fragmentManager, "menu");
            return true;
        }else if(id == R.id.menu_sort) {
            mContextMenuSort.show(fragmentManager,"sort");
            return true;
        }
        return true;
    }

    private void initialization(){
        //ToolBar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.Current_tasks);
        mContextMenuSort = MenuUtilsSort.setupMenu(this);
        mContextMenu = MenuUtils.setupMenu(this);
        fragmentManager = getSupportFragmentManager();
        //
        mNewTaskBT = findViewById(R.id.newTask_bt);
        mTaskList = findViewById(R.id.task_list);
        mTaskList.setLayoutManager(new LinearLayoutManager(this));
        mTaskList.setHasFixedSize(true);
        //
        mTasks = new ArrayList<>();
    }

    private void restartAdapter(){
        rvTaskAdapter = new RVTaskAdapter(MainActivity.this,mTasks);
        mTaskList.setAdapter(rvTaskAdapter);
    }

    private void sortArrayTask(ArrayList<Task> tasks, int sortIndex){
        switch (sortIndex){
            case 0:
                Collections.sort(mTasks, new Comparator<Task>() {
                    @Override
                    public int compare(Task o1, Task o2) {
                        return o1.getmName().compareTo(o2.getmName());
                    }
                });
                restartAdapter();
                break;
            case 1:
                Collections.sort(mTasks, new Comparator<Task>() {
                    @Override
                    public int compare(Task o1, Task o2) {
                        return o2.getmName().compareTo(o1.getmName());
                    }
                });
                restartAdapter();
                break;
            case 2:
                Collections.sort(mTasks, new Comparator<Task>() {
                    @Override
                    public int compare(Task o1, Task o2) {
                        if(o1.getmStartDate()==null || o2.getmStartDate()==null){
                            return o2.getmName().compareTo(o1.getmName());
                        }
                        return o1.getmStartDate().compareTo(o2.getmStartDate());
                    }
                });
                restartAdapter();
                break;
            case 3:
                Collections.sort(mTasks, new Comparator<Task>() {
                    @Override
                    public int compare(Task o1, Task o2) {
                        if(o1.getmStartDate()==null || o2.getmStartDate()==null){
                            return o2.getmName().compareTo(o1.getmName());
                        }
                        return o2.getmStartDate().compareTo(o1.getmStartDate());
                    }
                });
                restartAdapter();
                break;
        }
    }

    private void finishActivity() {
        finish();
    }

    private void addNewTask(){
        Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
        startActivityForResult(intent,1);
    }

    private void deleteAllTasks(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.delete_all_tasks);
        mDialogDeleteAllStan = true;
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TaskLab.getTaskLab(MainActivity.this).removeAllNotes();
                asyncTask = new mAsyncTask();
                asyncTask.execute();
                mDialogDeleteAllStan = false;
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialogDeleteAllStan = false;
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private final class mAsyncTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            mTasks = TaskLab.getTaskLab(MainActivity.this).getTasks();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            restartAdapter();
            super.onPostExecute(aVoid);
        }
    }
}
