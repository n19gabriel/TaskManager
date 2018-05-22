package com.example.gabriel.taskmanager.activity.new_task_activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gabriel.taskmanager.R;
import com.example.gabriel.taskmanager.data.TaskLab;
import com.example.gabriel.taskmanager.file_helper.FileHelper;
import com.example.gabriel.taskmanager.model.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class NewTaskActivity extends AppCompatActivity {
    private static final int GALLERY = 3;
    private static final int CAMERA = 14;
    private static final int REQUEST_STORAGE_PERMISSION = 4;
    private Toolbar mToolbar;
    private EditText mTaskNameET;
    private EditText mTaskCommitET;
    private Button mAddNewTaskBT;
    private Button mExitBT;
    private Task mTask;
    private ImageButton mNameVoiceIB;
    private ImageButton mCommitVoiceIB;
    private CircleImageView mImageTask;
    private Bitmap bitmap;
    private FileHelper mFileHelper;

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

        mImageTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
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
        }else if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    mImageTask.setImageBitmap(bitmap);
                    int px = dpToPx(NewTaskActivity.this, 100);
                    bitmap = Bitmap.createScaledBitmap(bitmap, px,px, false);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(NewTaskActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            mImageTask.setImageBitmap(bitmap);
            int px = dpToPx(NewTaskActivity.this, 100);
            bitmap = Bitmap.createScaledBitmap(bitmap, px,px, false);
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
        mImageTask = findViewById(R.id.image_task);
        mFileHelper = new FileHelper(NewTaskActivity.this);

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
            mFileHelper.saveImageToExternalStorage(bitmap, mTask.getmName());
        }else if(!name.isEmpty() && !commit.isEmpty() ) {
            Task task = new Task(name, commit);
            mFileHelper.saveImageToExternalStorage(bitmap, task.getmName());
            TaskLab.getTaskLab(NewTaskActivity.this).addTask(task);
            mTaskNameET.setText("");
            mTaskCommitET.setText("");
            mImageTask.setImageResource(R.mipmap.ic_launcher_background);
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

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        // Check for the external storage permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // If you do not have permission, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        } else {
            // Launch the camera if the permission exists
            launchCamera();
        }
    }

    private int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void launchCamera() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, CAMERA);
    }

    private void finishActivity() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
