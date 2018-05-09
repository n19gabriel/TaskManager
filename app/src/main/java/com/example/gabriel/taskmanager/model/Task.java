package com.example.gabriel.taskmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Task implements Parcelable {
    private String mId;
    private String mName;
    private String mCommit;
    private String mStartDate;
    private String mDeadline;
    private String mExecutionTime;


    public Task() {
    }

    public Task(String mName, String mCommit) {
        this.mId = UUID.randomUUID().toString();
        this.mName = mName;
        this.mCommit = mCommit;
    }

    public Task(String mName, String mCommit, String mStartDate, String mDeadline, String mExecutionTime) {
        this.mName = mName;
        this.mCommit = mCommit;
        this.mStartDate = mStartDate;
        this.mDeadline = mDeadline;
        this.mExecutionTime = mExecutionTime;
    }

    protected Task(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mCommit = in.readString();
        mStartDate = in.readString();
        mDeadline = in.readString();
        mExecutionTime = in.readString();
    }

    public String getmName() {
        return mName;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCommit() {
        return mCommit;
    }

    public void setmCommit(String mCommit) {
        this.mCommit = mCommit;
    }

    public String getmStartDate() {
        return mStartDate;
    }

    public void setmStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public String getmDeadline() {
        return mDeadline;
    }

    public void setmDeadline(String mDeadline) {
        this.mDeadline = mDeadline;
    }

    public String getmExecutionTime() {
        return mExecutionTime;
    }

    public void setmExecutionTime(String mExecutionTime) {
        this.mExecutionTime = mExecutionTime;
    }

    @Override
    public String toString() {
        return super.toString();
        //return "mName:"+this.mName+"," +
        //        "mCommit:"+this.mCommit+"," +
         //       "mStartDate:"+this.mStartDate+"," +
         //       "mDeadline:"+this.mDeadline+"," +
         //       "mExecutionTime:"+this.mExecutionTime;
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
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mCommit);
        dest.writeString(mStartDate);
        dest.writeString(mDeadline);
        dest.writeString(mExecutionTime);
    }
}
