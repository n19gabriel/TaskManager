package com.example.gabriel.taskmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gabriel.taskmanager.R;
import com.example.gabriel.taskmanager.model.Task;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(Context context, ArrayList<Task> offices) {
        super(context, 0, offices);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.task_item, parent, false);
        }

        Task currentTask = getItem(position);

        TextView nameTaskTV = listItemView.findViewById(R.id.name_task_tv);

        nameTaskTV.setText(currentTask.getName());

        TextView commitTaskTV = listItemView.findViewById(R.id.commit_task_tv);

        commitTaskTV.setText(currentTask.getCommit());

        TextView startDateTV = listItemView.findViewById(R.id.start_date_tv);

        startDateTV.setText(currentTask.getStartDate());

        TextView endDateTV = listItemView.findViewById(R.id.deadline_tv);

        endDateTV.setText(currentTask.getDeadLine());

        TextView executionTimeTV = listItemView.findViewById(R.id.execution_time_tv);

        executionTimeTV.setText(currentTask.getExecutionTime());

        return listItemView;
    }
}
