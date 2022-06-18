package sg.edu.np.mad.dontslack;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListViewHolder> {
    ArrayList<TaskObject> data;
    SelectListener selectListener;
    public ToDoListAdapter (ArrayList <TaskObject> input, SelectListener listener){
        data = input;
        selectListener = listener;
    }

    @NonNull
    @Override
    public ToDoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_list_recycler, parent, false );
        return new ToDoListViewHolder(item);
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(ToDoListViewHolder holder, @SuppressLint("RecyclerView") int position){
        String taskName = data.get(position).getTaskName();
        holder.taskName.setText(taskName);
        String taskDate = data.get(position).getTaskDeadLine();
        holder.taskButton.setOnClickListener(v -> selectListener.onItemClicked(data.get(position)));


        if(data.get(position).isTaskStatus()){
            holder.taskDate.setText("Task Completed!");
            holder.taskButton.getBackground().setTint(holder.taskButton.getResources().getColor(R.color.greyish_brown));
            //.setImageResource(R.drawable.ic_tick_background);
            //(Color.parseColor("#000000"));
        }
        else{
            holder.taskDate.setText("DeadLine: "+ taskDate);
            holder.taskButton.getBackground().setTint(holder.taskButton.getResources().getColor(R.color.beigy_brown));
        }

    }

    public int getItemCount(){
        return data.size();
    }
}