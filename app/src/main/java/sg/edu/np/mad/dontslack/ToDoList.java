package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class ToDoList extends AppCompatActivity{
    private String TAG = "ToDoList";
    DBHandler dbHandler = new DBHandler(this, null,null,1);
    ArrayList<TaskObject> workTaskList = new ArrayList<TaskObject>();
    ArrayList<TaskObject> personalTaskList = new ArrayList<TaskObject>();
    String taskCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();


        Button workTaskButton = findViewById(R.id.workFragmentButton);
        Button personalTaskButton = findViewById(R.id.personalFragmentButton);

        workTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskCategory = "work";
                workTaskButton.setBackgroundColor(Color.LTGRAY);
                personalTaskButton.setBackgroundColor(Color.rgb(225,227,229));
                storeTaskDataToArray();
                replaceFragment(new toDoListWork(),workTaskList);
            }
        });


        personalTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskCategory = "personal";
                workTaskButton.setBackgroundColor(Color.rgb(225,227,229));
                personalTaskButton.setBackgroundColor(Color.LTGRAY);
                storeTaskDataToArray();
                replaceFragment(new toDoListPersonal(),personalTaskList);
            }
        });

        FloatingActionButton addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ToDoList.this,AddTaskPage.class);
                myIntent.putExtra("category",taskCategory);
                startActivity(myIntent);
            }
        });
    }


    private void replaceFragment(Fragment fragment, ArrayList<TaskObject>taskList) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle taskListBundle = new Bundle();
        taskListBundle.putSerializable("taskList", (Serializable) taskList);
        fragment.setArguments(taskListBundle);
        fragmentTransaction.replace(R.id.toDoListFrameLayout,fragment);
        fragmentTransaction.commit();
    }

    private void storeTaskDataToArray() {
        Cursor cursor = dbHandler.readAllTaskData(taskCategory);
        if (cursor.getCount() == 0) {
            Toast.makeText(this,"No Data Found", Toast.LENGTH_SHORT).show();

        } else {
            if (taskCategory.equals("work")){
                workTaskList.clear();
                while (cursor.moveToNext()) {
                    TaskObject task = new TaskObject();
                    task.setTaskName(cursor.getString(0));
                    task.setTaskStatus(Boolean.parseBoolean(cursor.getString(1)));
                    task.setTaskDetails(cursor.getString(2));
                    task.setTaskStartTime(cursor.getString(3));
                    task.setTaskDeadLine(cursor.getString(4));
                    workTaskList.add(task);
                }
            }else{
                personalTaskList.clear();
                while (cursor.moveToNext()) {
                    TaskObject task = new TaskObject();
                    task.setTaskName(cursor.getString(0));
                    task.setTaskStatus(Boolean.parseBoolean(cursor.getString(1)));
                    task.setTaskDetails(cursor.getString(2));
                    task.setTaskStartTime(cursor.getString(3));
                    task.setTaskDeadLine(cursor.getString(4));
                    personalTaskList.add(task);
                }
            }

        }
    }
}