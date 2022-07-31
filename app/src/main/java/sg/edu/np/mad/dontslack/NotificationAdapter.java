package sg.edu.np.mad.dontslack;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    //GLobal variable
    ArrayList<TaskObject> taskData;
    ArrayList<CalendarEvent> calendarData;
    String currentCategory;
    public NotificationAdapter (ArrayList <TaskObject> firstInput, ArrayList <CalendarEvent> secondInput, String currentCat){
        currentCategory = currentCat;
        if(currentCategory.equals("task")){
            taskData = firstInput;
        }
        else {
            calendarData = secondInput;
        }
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notification_recycler, parent, false );
        return new NotificationViewHolder(item);
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, @SuppressLint("RecyclerView") int position){
        String objectName;
        String objectDueDate;
        //If user is viewing the task category
        if(currentCategory.equals("task")){
            //The object will be get from the task instead of calendar
            objectName = taskData.get(position).getTaskName();
            objectDueDate = taskData.get(position).getTaskDeadLine();
        }
        else{
            //else if user is viewing the event category
            //The object will be get from the calendar instead of task
            objectName = calendarData.get(position).getName();
            objectDueDate = calendarData.get(position).getDate()+""+calendarData.get(position).getTime();
        }
        //Setting the text view to the detail of the current event or task
        holder.objectDate.setText(objectDueDate);
        holder.objectName.setText(objectName);

    }

    public int getItemCount(){
        if(currentCategory.equals("task")){
            return taskData.size();
        }
        else{
            return  calendarData.size();
        }
    }
}