package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTaskPage extends AppCompatActivity {
    private final String TAG = "Add Task";
    DBHandler dbHandler = new DBHandler(this,null,null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_page);
        Bundle categoryBundle = getIntent().getExtras();
        String taskCategory = categoryBundle.getString("category");
        EditText taskTitle = findViewById(R.id.taskTitle);
        String TaskTitle = taskTitle.getText().toString();
        EditText taskDetails = findViewById(R.id.taskDetails);
        String TaskDetails = taskDetails.getText().toString();
        EditText taskStartTime = findViewById(R.id.taskStartTime);
        EditText taskDeadLine = findViewById(R.id.taskDeadline);
        Button createTaskButton = findViewById(R.id.createTaskButton);

        taskStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "taskStartTime");
                showDateTimeDialog(taskStartTime);
            }
        });

        taskDeadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "taskDeadline");
                showDateTimeDialog(taskDeadLine);
            }
        });

        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskObject newTaskObject = new TaskObject();
                newTaskObject.setTaskName(TaskTitle);
                newTaskObject.setTaskCategory(taskCategory);
                newTaskObject.setTaskDetails(TaskDetails);
                newTaskObject.setTaskStartTime(String.valueOf(taskStartTime));
                newTaskObject.setTaskDeadLine(String.valueOf(taskDeadLine));
                newTaskObject.setTaskStatus(false);
                dbHandler.addTask(newTaskObject);
                Toast.makeText(AddTaskPage.this,"Task Added Successfully", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(AddTaskPage.this,ToDoList.class);
                startActivity(myIntent);
            }
        });
    }
    public EditText showDateTimeDialog(EditText date_time){
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
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
                        date_time.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(AddTaskPage.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(AddTaskPage.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
        return date_time;
    }
}