package com.example.gabriel.taskmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;
import com.example.gabriel.taskmanager.R;
import com.example.gabriel.taskmanager.activity.new_task_activity.NewTaskActivity;
import com.example.gabriel.taskmanager.data.TaskLab;
import com.example.gabriel.taskmanager.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RVTaskAdapter extends RecyclerView.Adapter<RVTaskAdapter.TaskViewHolder> {

    private ArrayList<Task> taskArrayList;
    private Context context;

    public RVTaskAdapter(Context context, ArrayList<Task> taskArrayList) {
        this.context = context;
        this.taskArrayList = taskArrayList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(taskArrayList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {
        private SimpleDateFormat simpleDateFormat;
        private SwipeLayout swipeLayout;
        private TextView nameTaskTV;
        private TextView commitTaskTV;
        private TextView startDateTaskTV;
        private TextView deadlineTaskTV;
        private TextView executionTaskTV;
        private Button deleteB;
        private Button changeB;
        private ImageButton restartBT;
        private ImageButton startFinishBT;
        private ImageButton pauseBT;
        private LinearLayout dateLL;

        public TaskViewHolder(View itemView) {
            super(itemView);
            simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            swipeLayout = itemView.findViewById(R.id.swipe);
            nameTaskTV = itemView.findViewById(R.id.name_task_tv);
            commitTaskTV = itemView.findViewById(R.id.commit_task_tv);
            startDateTaskTV = itemView.findViewById(R.id.start_date_tv);
            deadlineTaskTV = itemView.findViewById(R.id.deadline_tv);
            executionTaskTV = itemView.findViewById(R.id.execution_time_tv);
            deleteB = itemView.findViewById(R.id.delete_task_b);
            changeB = itemView.findViewById(R.id.change_task_b);
            restartBT =itemView.findViewById(R.id.restart_bt);
            startFinishBT = itemView.findViewById(R.id.start_finish_bt);
            pauseBT = itemView.findViewById(R.id.pause_bt);
            dateLL = itemView.findViewById(R.id.date_LL);
        }

        public void bind(final Task task, final int position) {
            nameTaskTV.setText(task.getmName());

            commitTaskTV.setText(task.getmCommit());

            startDateTaskTV.setText(task.getmStartDate());

            deadlineTaskTV.setText(task.getmDeadline());

            executionTaskTV.setText(task.getmExecutionTime());

            if(task.getmDeadline()!=null){
                restartBT.setVisibility(View.VISIBLE);
                pauseBT.setVisibility(View.VISIBLE);
                startFinishBT.setImageResource(R.drawable.ic_event_busy_white_24dp);
                dateLL.setBackgroundResource(R.color.red);
            }else if(task.getmStartDate()!=null){
                pauseBT.setVisibility(View.INVISIBLE);
                startFinishBT.setImageResource(R.drawable.ic_event_available_white_24dp);
                dateLL.setBackgroundResource(R.color.orange);
            }else {
                startFinishBT.setImageResource(R.drawable.ic_event_note_white_24dp);
                restartBT.setVisibility(View.INVISIBLE);
                pauseBT.setVisibility(View.INVISIBLE);
                dateLL.setBackgroundResource(R.color.light_grin);
            }

            deleteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SwipeItemMangerInterface mItemManger = new SwipeItemMangerInterface() {
                        @Override
                        public void openItem(int position) {

                        }

                        @Override
                        public void closeItem(int position) {

                        }

                        @Override
                        public void closeAllExcept(SwipeLayout layout) {

                        }

                        @Override
                        public void closeAllItems() {

                        }

                        @Override
                        public List<Integer> getOpenItems() {
                            return null;
                        }

                        @Override
                        public List<SwipeLayout> getOpenLayouts() {
                            return null;
                        }

                        @Override
                        public void removeShownLayouts(SwipeLayout layout) {

                        }

                        @Override
                        public boolean isOpen(int position) {
                            return false;
                        }

                        @Override
                        public Attributes.Mode getMode() {
                            return null;
                        }

                        @Override
                        public void setMode(Attributes.Mode mode) {

                        }
                    };
                    mItemManger.removeShownLayouts(swipeLayout);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, taskArrayList.size());
                    mItemManger.closeAllItems();
                    TaskLab.getTaskLab(context).removeTask(task.getmId());
                    taskArrayList.remove(task);

                }
            });
            changeB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewTaskActivity.class);
                    intent.putExtra("Task", task);
                    context.startActivity(intent);
                }
            });

            restartBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    restartTask(task);
                }
            });

            startFinishBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        starOrFinishTask(task);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });

            pauseBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pauseTask(task);
                }
            });
        }

        private void starOrFinishTask(Task task) throws ParseException {
            if(task.getmStartDate()==null){
                restartBT.setVisibility(View.VISIBLE);
                startFinishBT.setImageResource(R.drawable.ic_event_available_white_24dp);
                dateLL.setBackgroundResource(R.color.orange);
                task.setmStartDate(getDate());
                TaskLab.getTaskLab(context).updateTask(task);
                startDateTaskTV.setText(task.getmStartDate());
            }else if(task.getmDeadline()==null){
                pauseBT.setVisibility(View.VISIBLE);
                startFinishBT.setImageResource(R.drawable.ic_event_busy_white_24dp);
                task.setmDeadline(getDate());
                Date dateStart = simpleDateFormat.parse(task.getmStartDate());
                Date deadline = simpleDateFormat.parse(task.getmDeadline());
                long time =  deadline.getTime()-dateStart.getTime();
                long hour = time / 3600000;
                long minutes = (time % 3600000)/1000/60;
                task.setmExecutionTime(hour+":"+minutes);
                dateLL.setBackgroundResource(R.color.red);
                TaskLab.getTaskLab(context).updateTask(task);
                deadlineTaskTV.setText(task.getmDeadline());
                executionTaskTV.setText(task.getmExecutionTime());
            }else {
                startFinishBT.setImageResource(R.drawable.ic_event_note_white_24dp);
                restartBT.setVisibility(View.INVISIBLE);
                pauseBT.setVisibility(View.INVISIBLE);
                task.setmStartDate(null);
                task.setmDeadline(null);
                task.setmExecutionTime(null);
                dateLL.setBackgroundResource(R.color.light_grin);
                TaskLab.getTaskLab(context).updateTask(task);
                startDateTaskTV.setText(task.getmStartDate());
                deadlineTaskTV.setText(task.getmDeadline());
                executionTaskTV.setText(task.getmExecutionTime());
            }
        }

        private String getDate(){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            return simpleDateFormat.format(calendar.getTime());
        }

        private void restartTask(Task task){
            if(task.getmStartDate()!=null){
                pauseBT.setVisibility(View.INVISIBLE);
                startFinishBT.setImageResource(R.drawable.ic_event_busy_white_24dp);
                dateLL.setBackgroundResource(R.color.orange);
                task.setmStartDate(getDate());
                task.setmDeadline(null);
                task.setmExecutionTime(null);
                startDateTaskTV.setText(task.getmStartDate());
                deadlineTaskTV.setText(task.getmDeadline());
                executionTaskTV.setText(task.getmExecutionTime());
                TaskLab.getTaskLab(context).updateTask(task);
            }
        }
        private void pauseTask(Task task){
            if(task.getmDeadline()!=null){
                pauseBT.setVisibility(View.INVISIBLE);
                startFinishBT.setImageResource(R.drawable.ic_event_busy_white_24dp);
                dateLL.setBackgroundResource(R.color.orange);
                task.setmDeadline(null);
                task.setmExecutionTime(null);
                deadlineTaskTV.setText(task.getmDeadline());
                executionTaskTV.setText(task.getmExecutionTime());
                TaskLab.getTaskLab(context).updateTask(task);
            }
        }
    }
}