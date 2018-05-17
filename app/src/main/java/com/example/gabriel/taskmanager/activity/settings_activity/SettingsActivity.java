package com.example.gabriel.taskmanager.activity.settings_activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gabriel.taskmanager.R;
import com.example.gabriel.taskmanager.utils.Utils;

import yuku.ambilwarna.AmbilWarnaDialog;

public class SettingsActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES = "mSettings";
    private android.support.v7.widget.Toolbar mToolbar;
    private LinearLayout mLinearLayoutNotStartTaskColor;
    private LinearLayout mLinearLayoutStartTaskColor;
    private LinearLayout mLinearLayoutFinishTaskColor;
    private LinearLayout mLinearLayoutDefaultSetting;
    private TextView mNotStartTaskColorTV;
    private TextView mStartTaskColorTV;
    private TextView mFinishTaskColorTV;
    private int mDefaultColorNotStart;
    private int mDefaultColorStart;
    private int mDefaultColorFinish;
    private Utils mUtils;
    private SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initialization();

        mLinearLayoutNotStartTaskColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker(0);
            }
        });

        mLinearLayoutStartTaskColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker(1);
            }
        });

        mLinearLayoutFinishTaskColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker(2);
            }
        });

        mLinearLayoutDefaultSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaulColor();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mSharedPreferences.contains("DEFAULT_COLOR_NOT_START")){
            mNotStartTaskColorTV.setBackgroundColor(mUtils.loadSetings(mSharedPreferences,"DEFAULT_COLOR_NOT_START"));
        }
        if(mSharedPreferences.contains("DEFAULT_COLOR_START")){
            mStartTaskColorTV.setBackgroundColor(mUtils.loadSetings(mSharedPreferences,"DEFAULT_COLOR_START"));
        }
        if(mSharedPreferences.contains("DEFAULT_COLOR_FINISH")){
            mFinishTaskColorTV.setBackgroundColor(mUtils.loadSetings(mSharedPreferences,"DEFAULT_COLOR_FINISH"));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id==android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initialization(){
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mLinearLayoutNotStartTaskColor = findViewById(R.id.color_not_start_task);
        mLinearLayoutStartTaskColor = findViewById(R.id.color_start_task);
        mLinearLayoutFinishTaskColor = findViewById(R.id.color_finish_task);
        mLinearLayoutDefaultSetting = findViewById(R.id.default_setting);
        mNotStartTaskColorTV = findViewById(R.id.color_not_start_task_tv);
        mStartTaskColorTV = findViewById(R.id.color_start_task_tv);
        mFinishTaskColorTV = findViewById(R.id.color_finish_task_tv);
        mDefaultColorNotStart = ContextCompat.getColor(SettingsActivity.this,R.color.light_grin);
        mDefaultColorStart = ContextCompat.getColor(SettingsActivity.this,R.color.orange);
        mDefaultColorFinish = ContextCompat.getColor(SettingsActivity.this,R.color.red);
        mUtils = new Utils();
        mSharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    private void defaulColor(){
        mNotStartTaskColorTV.setBackgroundColor(mDefaultColorNotStart);
        mUtils.saveSetings(mSharedPreferences,mDefaultColorNotStart,"DEFAULT_COLOR_NOT_START");
        mStartTaskColorTV.setBackgroundColor(mDefaultColorStart);
        mUtils.saveSetings(mSharedPreferences,mDefaultColorStart,"DEFAULT_COLOR_START");
        mFinishTaskColorTV.setBackgroundColor(mDefaultColorFinish);
        mUtils.saveSetings(mSharedPreferences,mDefaultColorFinish,"DEFAULT_COLOR_FINISH");
    }

    private void openColorPicker(final int defaultColor){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
               switch (defaultColor){
                   case 0:
                       mNotStartTaskColorTV.setBackgroundColor(color);
                       mUtils.saveSetings(mSharedPreferences,color,"DEFAULT_COLOR_NOT_START");
                       break;
                   case 1:
                       mDefaultColorStart = color;
                       mStartTaskColorTV.setBackgroundColor(color);
                       mUtils.saveSetings(mSharedPreferences,color,"DEFAULT_COLOR_START");
                       break;
                   case 2:
                       mFinishTaskColorTV.setBackgroundColor(color);
                       mUtils.saveSetings(mSharedPreferences,color,"DEFAULT_COLOR_FINISH");
                       break;
               }
            }
        });
        ambilWarnaDialog.show();
    }
}
