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

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

        //Backbutton
        ImageView goBackButton = findViewById(R.id.toDoListBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent2 = new Intent(ToDoList.this, HomePage.class);
                startActivity(myIntent2);
            }
        });

        Button workTaskButton = findViewById(R.id.workFragmentButton);
        Button personalTaskButton = findViewById(R.id.personalFragmentButton);

        setDefaultCategory();
        workTaskButton.setBackgroundColor(Color.parseColor("#DFDFDF"));
        personalTaskButton.setBackgroundColor(Color.parseColor("#F1F1F1"));

        workTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskCategory.equals("work")){
                    Toast.makeText(ToDoList.this,"On work list",Toast.LENGTH_SHORT).show();
                }
                else{
                    taskCategory = "work";
                    workTaskButton.setBackgroundColor(Color.parseColor("#DFDFDF"));
                    personalTaskButton.setBackgroundColor(Color.parseColor("#F1F1F1"));
                    storeTaskDataToArray();
                    replaceFragment(new toDoListWork(),workTaskList);
                }
            }
        });


        personalTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskCategory.equals("personal")){
                    Toast.makeText(ToDoList.this,"On personal list",Toast.LENGTH_SHORT).show();
                }
                else{
                    taskCategory = "personal";
                    workTaskButton.setBackgroundColor(Color.parseColor("#F1F1F1"));
                    personalTaskButton.setBackgroundColor(Color.parseColor("#DFDFDF"));
                    storeTaskDataToArray();
                    replaceFragment(new toDoListPersonal(),personalTaskList);
                }
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

    private void setDefaultCategory(){
        taskCategory = "work";
        storeTaskDataToArray();
        replaceFragment(new toDoListWork(),workTaskList);
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
            Toast.makeText(this,"No Task Yet", Toast.LENGTH_SHORT).show();

        } else {
            if (taskCategory.equals("work")){
                workTaskList.clear();
                while (cursor.moveToNext()) {
                    TaskObject task = new TaskObject();
                    task.setTaskName(cursor.getString(0));
                    task.setTaskCategory(cursor.getString(1));
                    task.setTaskStatus(cursor.getString(2).equals("1"));
                    task.setTaskDescription(cursor.getString(3));
                    task.setTaskStartTime(cursor.getString(4));
                    task.setTaskDeadLine(cursor.getString(5));
                    workTaskList.add(task);
                }
            }else{
                personalTaskList.clear();
                while (cursor.moveToNext()) {
                    TaskObject task = new TaskObject();
                    task.setTaskName(cursor.getString(0));
                    task.setTaskCategory(cursor.getString(1));
                    task.setTaskStatus(cursor.getString(2).equals("1"));
                    task.setTaskDescription(cursor.getString(3));
                    task.setTaskStartTime(cursor.getString(4));
                    task.setTaskDeadLine(cursor.getString(5));
                    personalTaskList.add(task);
                }
            }
        }
    }


}