package sg.edu.np.mad.dontslack;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    ArrayList<TaskObject> data;
//    SelectListener selectListener;
    public NotificationAdapter (ArrayList <TaskObject> input){
        data = input;
//        selectListener = listener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notification_recycler, parent, false );
        return new NotificationViewHolder(item);
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, @SuppressLint("RecyclerView") int position){
        String taskName = data.get(position).getTaskName();
        holder.objectName.setText(taskName);
//        String taskDate = data.get(position).getTaskDeadLine();
//        holder.taskButton.setOnClickListener(v -> selectListener.onItemClicked(data.get(position)));
    }

    public int getItemCount(){
        return data.size();
    }
}