package sg.edu.np.mad.dontslack;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class toDoListWork extends Fragment implements SelectListener{
    DBHandler dbHandler = new DBHandler(this.getContext(),null,null,1);
    ArrayList<TaskObject> taskList = new ArrayList<TaskObject>();
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_to_do_list_work, container, false);
        storeTaskDataToArray();

        RecyclerView recyclerView = view.findViewById(R.id.toDoListRecyclerView);
        ToDoListAdapter myAdapter = new ToDoListAdapter(taskList, this);
        LinearLayoutManager toDOListLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(toDOListLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);
        return view;
    }


    private void storeTaskDataToArray() {
        Cursor cursor = dbHandler.readAllTaskData();
        if (cursor.getCount() == 0) {

        } else {
            while (cursor.moveToNext()) {
                TaskObject task = new TaskObject();
                task.setTaskName(cursor.getString(0));
                task.setTaskStatus(Boolean.parseBoolean(cursor.getString(1)));
                task.setTaskDetails(cursor.getString(2));
                task.setTaskDoDate(cursor.getString(3));
                task.setTaskDeadLine(cursor.getString(4));
                taskList.add(task);
            }
        }
    }

    @Override
    public void onItemClicked(TaskObject taskObject) {

    }
}