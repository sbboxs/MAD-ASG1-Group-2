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

    public void onBindViewHolder(ToDoListViewHolder holder, @SuppressLint("RecyclerView") int position){
        String taskName = data.get(position).getTaskName();
        holder.taskName.setText(taskName);
        holder.taskSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectListener.onItemClicked(data.get(position));
            }
        });
    }

    public int getItemCount(){
        return data.size();
    }
}