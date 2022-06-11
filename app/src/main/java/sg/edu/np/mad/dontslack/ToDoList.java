package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ToDoList extends AppCompatActivity implements SelectListener{
    DBHandler dbHandler = new DBHandler(this,null,null,1);
    private String TAG = "ToDoList";
    ArrayList<TaskObject> taskList = new ArrayList<TaskObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        storeTaskDataToArray();

        RecyclerView recyclerView = findViewById(R.id.toDoListRecyclerView);
        ToDoListAdapter myAdapter = new ToDoListAdapter(taskList, this);
        LinearLayoutManager toDOListLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(toDOListLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);

        Button addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ToDoList.this,AddTaskPage.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onItemClicked(TaskObject taskObject) {

    }


    private void storeTaskDataToArray() {
        Cursor cursor = dbHandler.readAllTaskData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
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
}