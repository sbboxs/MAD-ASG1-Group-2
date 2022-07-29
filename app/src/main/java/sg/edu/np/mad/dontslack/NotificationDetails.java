package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NotificationDetails extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY_currentCategory = "currentCategory";

    DBHandler dbHandler = new DBHandler(this, null,null,1);
    String currentCategory;

    ArrayList<TaskObject> taskList = new ArrayList<>();
    ArrayList<CalendarEvent> eventList = new ArrayList<>();

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String date = sdf.format(System.currentTimeMillis());

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        setDefaultCategory();
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        Button taskButton = findViewById(R.id.taskFragmentButton);
        Button eventButton = findViewById(R.id.eventFragmentButton);
        Button dunSlackButton = findViewById(R.id.dunSlackButton);

        taskButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(currentCategory.equals("task")){
                    Toast.makeText(NotificationDetails.this,"Currently on task category",Toast.LENGTH_SHORT).show();
                }
                else{
                    currentCategory = "task";
                    taskButton.setBackgroundColor(Color.parseColor("#CC6B49"));
                    taskButton.setTextColor(Color.parseColor("#FFFFFF"));
                    eventButton.setBackgroundColor(Color.parseColor("#F1F1F1"));
                    eventButton.setTextColor(Color.parseColor("#000000"));
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_currentCategory, currentCategory);
                    editor.apply();
                    storeTaskDataToArray();
                    replaceTaskFragment(new NotificationDetailsFragment(),taskList);
                }
            }
        });

        eventButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(currentCategory.equals("event")){
                    Toast.makeText(NotificationDetails.this,"Currently on task category",Toast.LENGTH_SHORT).show();
                }
                else{
                    currentCategory = "event";
                    taskButton.setBackgroundColor(Color.parseColor("#F1F1F1"));
                    taskButton.setTextColor(Color.parseColor("#000000"));
                    eventButton.setBackgroundColor(Color.parseColor("#CC6B49"));
                    eventButton.setTextColor(Color.parseColor("#FFFFFF"));
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_currentCategory, currentCategory);
                    editor.apply();
                    storeTaskDataToArray();
                    replaceEventFragment(new NotificationDetailsFragment(),eventList);
                }
            }

        });

        dunSlackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(NotificationDetails.this, HomePage.class);
                startActivity(myIntent);
            }
        });
    }

    private void setDefaultCategory(){
        currentCategory = "task";
        storeTaskDataToArray();
        replaceTaskFragment(new NotificationDetailsFragment(),taskList);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_currentCategory, currentCategory);
        editor.apply();
    }

    //Retrieve all data and stored base on category
    private void storeTaskDataToArray() {
        //Check current category
        if(currentCategory.equals("task")){
            //If data already stored previously, skip.
            if(!(taskList.size() >0)){
                Cursor cursor = dbHandler.readTaskByDate(date);
                if (cursor.getCount() == 0) {
                    Toast.makeText(this,"No Task is found", Toast.LENGTH_SHORT).show();

                } else {
                    while (cursor.moveToNext()) {
                        TaskObject task = new TaskObject();
                        task.setTaskName(cursor.getString(0));
                        task.setTaskCategory(cursor.getString(1));
                        task.setTaskStatus(cursor.getString(2).equals("1"));
                        task.setTaskDescription(cursor.getString(3));
                        task.setTaskStartTime(cursor.getString(4));
                        task.setTaskDeadLine(cursor.getString(5));
                        taskList.add(task);
                    }
                }
            }
        }
        else{
            if(!(eventList.size()>0)){
                Cursor cursor = dbHandler.readCalendarTaskData(date);
                if (cursor.getCount() == 0) {
                    Toast.makeText(this,"No event is found", Toast.LENGTH_SHORT).show();
                } else {
                    while (cursor.moveToNext()) {
                        TaskObject task = new TaskObject();
                        task.setTaskName(cursor.getString(0));
                        task.setTaskCategory(cursor.getString(1));
                        task.setTaskStatus(cursor.getString(2).equals("1"));
                        task.setTaskDescription(cursor.getString(3));
                        task.setTaskStartTime(cursor.getString(4));
                        task.setTaskDeadLine(cursor.getString(5));
                        taskList.add(task);
                    }
                }
            }
        }
    }

    private void replaceTaskFragment(Fragment fragment, ArrayList<TaskObject>objectList) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle objectListBundle = new Bundle();
        objectListBundle.putSerializable("objectList", (Serializable) objectList);
        fragment.setArguments(objectListBundle);
        fragmentTransaction.replace(R.id.notificationFrameLayout,fragment);
        fragmentTransaction.commit();
    }

    private void replaceEventFragment(Fragment fragment, ArrayList<CalendarEvent>objectList) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle objectListBundle = new Bundle();
        objectListBundle.putSerializable("objectList", (Serializable) objectList);
        fragment.setArguments(objectListBundle);
        fragmentTransaction.replace(R.id.notificationFrameLayout,fragment);
        fragmentTransaction.commit();
    }
}