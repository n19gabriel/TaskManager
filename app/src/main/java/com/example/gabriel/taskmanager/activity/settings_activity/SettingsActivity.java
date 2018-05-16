package com.example.gabriel.taskmanager.activity.settings_activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gabriel.taskmanager.R;

import yuku.ambilwarna.AmbilWarnaDialog;

public class SettingsActivity extends AppCompatActivity {
    private LinearLayout mLinearLayoutNotStartTaskColor;
    private LinearLayout mLinearLayoutStartTaskColor;
    private LinearLayout mLinearLayoutFinishTaskColor;
    private TextView mNotStartTaskColorTV;
    private TextView mStartTaskColorTV;
    private TextView mFinishTaskColorTV;
    private int mDefaultColorNotStart;
    private int mDefaultColorStart;
    private int mDefaultColorFinish;
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
    }

    private void initialization(){
        mLinearLayoutNotStartTaskColor = findViewById(R.id.color_not_start_task);
        mLinearLayoutStartTaskColor = findViewById(R.id.color_start_task);
        mLinearLayoutFinishTaskColor = findViewById(R.id.color_finish_task);
        mNotStartTaskColorTV = findViewById(R.id.color_not_start_task_tv);
        mStartTaskColorTV = findViewById(R.id.color_start_task_tv);
        mFinishTaskColorTV = findViewById(R.id.color_finish_task_tv);
        mDefaultColorNotStart = ContextCompat.getColor(SettingsActivity.this,R.color.light_grin);
        mDefaultColorStart = ContextCompat.getColor(SettingsActivity.this,R.color.orange);
        mDefaultColorFinish = ContextCompat.getColor(SettingsActivity.this,R.color.red);
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
                       mDefaultColorNotStart = color;
                       mNotStartTaskColorTV.setBackgroundColor(mDefaultColorNotStart);
                       break;
                   case 1:
                       mDefaultColorStart = color;
                       mStartTaskColorTV.setBackgroundColor(mDefaultColorStart);
                       break;
                   case 2:
                       mDefaultColorFinish = color;
                       mFinishTaskColorTV.setBackgroundColor(mDefaultColorFinish);
                       break;
               }
            }
        });
        ambilWarnaDialog.show();
    }
}
