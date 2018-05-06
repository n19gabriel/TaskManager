package com.example.gabriel.taskmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    private String mName;
    private String mCommit;

    public Task() {
    }

    public Task(String mName, String mCommit) {
        this.mName = mName;
        this.mCommit = mCommit;
    }

    protected Task(Parcel in) {
        mName = in.readString();
        mCommit = in.readString();
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getCommit() {
        return mCommit;
    }

    public void setCommit(String mCommit) {
        this.mCommit = mCommit;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mCommit);
    }
}
