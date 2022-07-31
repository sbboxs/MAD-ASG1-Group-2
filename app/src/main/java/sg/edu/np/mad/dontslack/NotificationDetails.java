package sg.edu.np.mad.dontslack;

import androidx.annotation.RequiresApi;
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
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.util.ArrayList;

public class NotificationDetails extends AppCompatActivity {
    //GLobal variables
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY_currentCategory = "currentCategory";

    DBHandler dbHandler = new DBHandler(this, null,null,1);
    String currentCategory;

    ArrayList<TaskObject> taskList = new ArrayList<>();
    ArrayList<CalendarEvent> eventList = new ArrayList<>();

    //Getting the different date format for task and calendar
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String date = sdf.format(System.currentTimeMillis());
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat csdf = new SimpleDateFormat("yyyy-MM-dd");
    String calendarDate = csdf.format(System.currentTimeMillis());

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        setDefaultCategory();
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        //Defining of buttons
        Button taskButton = findViewById(R.id.taskFragmentButton);
        Button eventButton = findViewById(R.id.eventFragmentButton);
        Button dunSlackButton = findViewById(R.id.dunSlackButton);

        //Task category button
        taskButton.setOnClickListener(v -> {
            //Check if user is on the current category
            if(currentCategory.equals("task")){
                Toast.makeText(NotificationDetails.this,"Currently on task category",Toast.LENGTH_SHORT).show();
            }
            else{
                //If not on the current category, set current category to task
                currentCategory = "task";
                //Change the task and event button to display which category button is been pressed.
                taskButton.setBackgroundColor(Color.parseColor("#CC6B49"));
                taskButton.setTextColor(Color.parseColor("#FFFFFF"));
                eventButton.setBackgroundColor(Color.parseColor("#F1F1F1"));
                eventButton.setTextColor(Color.parseColor("#000000"));
                //Save the current category to shared preferences to allow the adapter to identify which category is the user in latest
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_currentCategory, currentCategory);
                editor.apply();
                //Get data from database and set the fragment
                storeTaskDataToArray();
                replaceTaskFragment(new NotificationDetailsFragment(),taskList);
            }
        });

        //Event category button
        eventButton.setOnClickListener(v -> {
            //If on current category display toast message
            if(currentCategory.equals("event")){
                Toast.makeText(NotificationDetails.this,"Currently on task category",Toast.LENGTH_SHORT).show();
            }
            else{
                //If not on current category , set the category to event
                currentCategory = "event";
                //Change the task and event button to display which category button is been pressed.
                taskButton.setBackgroundColor(Color.parseColor("#F1F1F1"));
                taskButton.setTextColor(Color.parseColor("#000000"));
                eventButton.setBackgroundColor(Color.parseColor("#CC6B49"));
                eventButton.setTextColor(Color.parseColor("#FFFFFF"));
                //Save the current category to shared preferences to allow the adapter to identify which category is the user in latest
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_currentCategory, currentCategory);
                editor.apply();
                //Get data from database and set the fragment
                storeTaskDataToArray();
                replaceEventFragment(new NotificationDetailsFragment(),eventList);
            }
        });

        //This is the button allow user to go the home page of the app
        dunSlackButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(NotificationDetails.this, HomePage.class);
            startActivity(myIntent);
        });
    }

    //When the activity is first launch this method will be called and set the default
    @RequiresApi(api = Build.VERSION_CODES.O)
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void storeTaskDataToArray() {
        //Check current category
        if(currentCategory.equals("task")){
            //If data already stored previously, will just continue without getting the repetitive process of getting data
            if(!(taskList.size() >0)){
                //If no task is found display toast message
                Cursor cursor = dbHandler.readTaskByDate(date);
                if (cursor.getCount() == 0) {
                    Toast.makeText(this,"No Task is found", Toast.LENGTH_SHORT).show();

                } else {
                    //else if task are found, get from db and store in list
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
            //else, if the category is at event
            //If data already stored previously, will just continue without getting the repetitive process of getting data
            if(!(eventList.size()>0)){
                Cursor cursor = dbHandler.readCalendarTaskData(calendarDate);
                //If no event is found display toast message
                if (cursor.getCount() == 0) {
                    Toast.makeText(this,"No event is found", Toast.LENGTH_SHORT).show();
                } else {
                    //else if event are found, get from db and store in list
                    while (cursor.moveToNext()) {
                        CalendarEvent event = new CalendarEvent();
                        event.setName(cursor.getString(0));
                        event.setDate(LocalDate.parse(cursor.getString(1)));
                        event.setTime(cursor.getString(2));
                        eventList.add(event);
                    }
                }
            }
        }
    }

    //This is the method to replace fragment to display task
    //Reason of having 2 separate fragment is this different type of object is passing to the fragment
    private void replaceTaskFragment(Fragment fragment, ArrayList<TaskObject>objectList) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle objectListBundle = new Bundle();
        objectListBundle.putSerializable("objectList", (Serializable) objectList);
        fragment.setArguments(objectListBundle);
        fragmentTransaction.replace(R.id.notificationFrameLayout,fragment);
        fragmentTransaction.commit();
    }

    //This is the method to replace fragment to display event
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