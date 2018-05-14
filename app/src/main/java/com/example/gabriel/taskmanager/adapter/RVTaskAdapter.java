package com.example.gabriel.taskmanager.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gabriel.taskmanager.R;
import com.example.gabriel.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.Collection;

public class RVTaskAdapter extends RecyclerView.Adapter<RVTaskAdapter.TaskViewHolder> {

    private ArrayList<Task> taskArrayList;

    public RVTaskAdapter(ArrayList<Task> taskArrayList) {
        this.taskArrayList = taskArrayList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(taskArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTaskTV;
        private TextView commitTaskTV;
        private TextView startDateTaskTV;
        private TextView deadlineTaskTV;
        private TextView executionTaskTV;

        public TaskViewHolder(View itemView) {
            super(itemView);
            nameTaskTV = itemView.findViewById(R.id.name_task_tv);
            commitTaskTV = itemView.findViewById(R.id.commit_task_tv);
            startDateTaskTV = itemView.findViewById(R.id.start_date_tv);
            deadlineTaskTV = itemView.findViewById(R.id.deadline_tv);
            executionTaskTV = itemView.findViewById(R.id.execution_time_tv);
        }

        public void bind(Task task) {
            nameTaskTV.setText(task.getmName());

            commitTaskTV.setText(task.getmCommit());

            startDateTaskTV.setText(task.getmStartDate());

            deadlineTaskTV.setText(task.getmDeadline());

            executionTaskTV.setText(task.getmExecutionTime());
        }
    }
}