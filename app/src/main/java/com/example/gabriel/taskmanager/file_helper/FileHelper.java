package com.example.gabriel.taskmanager.file_helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.gabriel.taskmanager.R;
import com.example.gabriel.taskmanager.activity.main_activity.MainActivity;
import com.example.gabriel.taskmanager.data.TaskLab;
import com.example.gabriel.taskmanager.model.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class FileHelper {

    final static String APP_PATH_SD_CARD = "/DesiredSubfolderName/";
    final static String APP_THUMBNAIL_PATH_SD_CARD = "thumbnails";
    private Context context;

    public FileHelper(Context context) {
        this.context = context;
    }
    public boolean saveImageToExternalStorage(Bitmap image, String filename) {
        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;

        try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(fullPath, filename + ".png");
            file.createNewFile();
            OutputStream fOut = new FileOutputStream(file);

            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();

            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
            Toast.makeText(context, "save", Toast.LENGTH_SHORT);
            return true;

        } catch (Exception e) {
            Log.e("saveToExternalStorage()", e.getMessage());
            return false;
        }
    }

    private boolean isSdReadable() {

        boolean mExternalStorageAvailable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = true;
            Log.i("isSdReadable", "External storage card is readable.");
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            Log.i("isSdReadable", "External storage card is readable.");
            mExternalStorageAvailable = true;
        } else {
            // Something else is wrong. It may be one of many other
            // states, but all we need to know is we can neither read nor write
            mExternalStorageAvailable = false;
        }

        return mExternalStorageAvailable;
    }
    public Bitmap getBitmap(String filename) {

        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;
        Bitmap thumbnail = null;

        // Look for the file on the external storage
        try {
            if (isSdReadable() == true) {
                thumbnail = BitmapFactory.decodeFile(fullPath + "/" + filename + ".png");
            }
        } catch (Exception e) {
            Log.e(" on external storage", e.getMessage());
        }

        return thumbnail;
    }

    private final class mAsyncTask extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = getBitmap(strings[0]);
            return bitmap;
        }
    }

    public Bitmap loadBitmap(String filename) throws ExecutionException, InterruptedException {
        mAsyncTask asyncTask = new mAsyncTask();
        asyncTask.execute(filename);
            return asyncTask.get();
    }
}


