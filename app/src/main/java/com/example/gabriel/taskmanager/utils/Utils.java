package com.example.gabriel.taskmanager.utils;

import android.content.SharedPreferences;

public class Utils {
    public void saveSetings(SharedPreferences sharedPreferences, int value, String tag ){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(tag,value);
        editor.commit();
    }

    public int loadSetings(SharedPreferences sharedPreferences, String tag){
        int saveText = sharedPreferences.getInt(tag,0);
        return saveText;
    }

    public void saveVisited(SharedPreferences sharedPreferences, boolean value, String tag ){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(tag,value);
        editor.commit();
    }

    public boolean loadVisited(SharedPreferences sharedPreferences, String tag){
        boolean hasVisited = sharedPreferences.getBoolean(tag,false);
        return hasVisited;
    }
}
