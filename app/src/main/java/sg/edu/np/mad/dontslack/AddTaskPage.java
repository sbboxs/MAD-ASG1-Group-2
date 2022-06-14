package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTaskPage extends AppCompatActivity {
    private final String TAG = "Add Task";
    DBHandler dbHandler = new DBHandler(this,null,null,1);
    private static String etTaskTitle;
    private static String etTaskDiscription;
    String newTaskStartTime;
    String newTaskDeadLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_page);
        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        Bundle categoryBundle = getIntent().getExtras();
        String taskCategory = categoryBundle.getString("category");

        EditText taskStartTime = findViewById(R.id.taskStartTime);
        EditText taskDeadLine = findViewById(R.id.taskDeadline);

        taskStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskStartTime.setText("Start Time");
                showDateTimeDialog(taskStartTime);

            }
        });

        taskDeadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDeadLine.setText("Deadline");
                showDateTimeDialog(taskDeadLine);

            }
        });

        Button createTaskButton = findViewById(R.id.createTaskButton);
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newTaskTitle = findViewById(R.id.editTaskTitle);
                etTaskTitle = newTaskTitle.getText().toString();
                EditText newTaskDescription = findViewById(R.id.editTaskDescription);
                etTaskDiscription = newTaskDescription.getText().toString();

                Log.v(TAG,"newTask:" + etTaskTitle);
                Log.v(TAG,"newTask:" + etTaskDiscription);
                Log.v(TAG,"newTask:" + newTaskStartTime);
                Log.v(TAG,"newTask:" + newTaskDeadLine);
                if(etTaskTitle.equals("") || etTaskDiscription.equals("") || newTaskStartTime == null || newTaskDeadLine == null){
                    Toast.makeText(AddTaskPage.this,"Please ensure all fields is filled.",Toast.LENGTH_SHORT).show();
                }
                else{
                    TaskObject newTaskObject = new TaskObject();
                    newTaskObject.setTaskCategory(taskCategory);
                    newTaskObject.setTaskName(etTaskTitle);
                    newTaskObject.setTaskDescription(etTaskDiscription);
                    newTaskObject.setTaskStartTime(newTaskStartTime);
                    newTaskObject.setTaskDeadLine(newTaskDeadLine);
                    newTaskObject.setTaskStatus(false);
                    dbHandler.addTask(newTaskObject);
                    Toast.makeText(AddTaskPage.this,"Task Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(AddTaskPage.this,ToDoList.class);
                    startActivity(myIntent);
                }
            }
        });
    }

    public void showDateTimeDialog(EditText date_time){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy h:mm aaa");
                        if(date_time.getText().toString().equals("Start Time")){
                            date_time.setText(simpleDateFormat.format(calendar.getTime()));
                            newTaskStartTime = date_time.getText().toString();
                        }
                        else{
                            date_time.setText(simpleDateFormat.format(calendar.getTime()));
                            newTaskDeadLine = date_time.getText().toString();
                        }
                    }
                };
                new TimePickerDialog(AddTaskPage.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(AddTaskPage.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}