package com.example.gabriel.taskmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gabriel.taskmanager.R;
import com.example.gabriel.taskmanager.model.Task;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(Context context, ArrayList<Task> offices) {
        super(context, 0, offices);
    }

    static class ViewHolder {
        public TextView nameTaskTV;
        public TextView commitTaskTV;
        public TextView startDateTV;
        public TextView endDateTV;
        public TextView executionTimeTV;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        ViewHolder holder;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.task_item, parent, false);
            holder = new ViewHolder();
            holder.nameTaskTV = listItemView.findViewById(R.id.name_task_tv);
            holder.commitTaskTV = listItemView.findViewById(R.id.commit_task_tv);
            holder.startDateTV = listItemView.findViewById(R.id.start_date_tv);
            holder.endDateTV = listItemView.findViewById(R.id.deadline_tv);
            holder.executionTimeTV = listItemView.findViewById(R.id.execution_time_tv);
            listItemView.setTag(holder);

        }else {
            holder = (ViewHolder) listItemView.getTag();
        }

        Task currentTask = getItem(position);

        holder.nameTaskTV.setText(currentTask.getmName());

        holder.commitTaskTV.setText(currentTask.getmCommit());

        holder.startDateTV.setText(currentTask.getmStartDate());

        holder.endDateTV.setText(currentTask.getmDeadline());

        holder.executionTimeTV.setText(currentTask.getmExecutionTime());

        if(currentTask.getmStartDate()==null){
            listItemView.setBackgroundResource(R.color.light_grin);
        }else if(currentTask.getmDeadline()!=null){
            listItemView.setBackgroundResource(R.color.red);
        }else {
            listItemView.setBackgroundResource(R.color.orange);
        }

        return listItemView;
    }
}
