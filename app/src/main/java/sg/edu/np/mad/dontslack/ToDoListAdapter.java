package sg.edu.np.mad.dontslack;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void onBindViewHolder(@NonNull ToDoListViewHolder holder, @SuppressLint("RecyclerView") int position){
        //Setting the data to display in viewholder
        String taskName = data.get(position).getTaskName();
        //This is to avoid the task name too long
        if(taskName.length()>22){
            String simplifyTaskName = taskName.substring(0,22) + "...";
            holder.taskName.setText(simplifyTaskName);
        }
        else{
            //If task length is not long enough will display fullname of the task
            holder.taskName.setText(taskName);
        }
        String taskDate = data.get(position).getTaskDeadLine();
        //CLick listener
        holder.taskButton.setOnClickListener(v -> selectListener.onItemClicked(data.get(position)));

        //Check if the task is completed
        if(data.get(position).isTaskStatus()){
            //If task completed will just display "Task Completed" instead of the task deadline
            holder.taskDate.setText("Task Completed!");
            holder.taskButton.getBackground().setTint(holder.taskButton.getResources().getColor(R.color.greyish_brown));
            //.setImageResource(R.drawable.ic_tick_background);
            //(Color.parseColor("#000000"));
        }
        else{
            //If task not completed display the deadline instead of "Task Completed"
            holder.taskDate.setText("DeadLine: "+ taskDate);
            holder.taskButton.getBackground().setTint(holder.taskButton.getResources().getColor(R.color.beigy_brown));
        }

    }

    public int getItemCount(){
        return data.size();
    }
}