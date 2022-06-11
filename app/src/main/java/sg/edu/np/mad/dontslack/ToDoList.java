package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.widget.Adapter;

import java.util.ArrayList;

public class ToDoList extends AppCompatActivity implements SelectListener{
    private String TAG = "ToDoList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        ArrayList<TaskObject> taskList = new ArrayList<TaskObject>();

        RecyclerView recyclerView = findViewById(R.id.toDoListRecyclerView);
        ToDoListAdapter myAdapter = new ToDoListAdapter(taskList, this);
        LinearLayoutManager toDOListLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(toDOListLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onItemClicked(TaskObject taskObject) {

    }
}