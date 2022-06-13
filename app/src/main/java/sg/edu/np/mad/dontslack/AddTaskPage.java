package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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
    String startingTime;
    String deadLine;
    String dateTime;
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

        EditText taskTitle = findViewById(R.id.taskTitle);

        EditText taskDetails = findViewById(R.id.taskDetails);

        EditText taskStartTime = findViewById(R.id.taskStartTime);
        EditText taskDeadLine = findViewById(R.id.taskDeadline);

        taskStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(taskStartTime);
                Log.v(TAG,"11startingTIme:" + startingTime);
            }
        });

        taskDeadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(taskDeadLine);
                Log.v(TAG,"11Deadline:" + deadLine);
            }
        });

        Button createTaskButton = findViewById(R.id.createTaskButton);
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskObject newTaskObject = new TaskObject();
                newTaskObject.setTaskCategory(taskCategory);
                newTaskObject.setTaskName(taskTitle.getText().toString());
                newTaskObject.setTaskDetails(taskDetails.getText().toString());
                Log.v(TAG,"startingTIme:" + startingTime);
                Log.v(TAG,"Deadline:" + deadLine);
                newTaskObject.setTaskStartTime(startingTime);
                newTaskObject.setTaskDeadLine(deadLine);
                newTaskObject.setTaskStatus(false);
                dbHandler.addTask(newTaskObject);
                Toast.makeText(AddTaskPage.this,"Task Added Successfully", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(AddTaskPage.this,ToDoList.class);
                startActivity(myIntent);
            }
        });
    }

    public void showDateTimeDialog(EditText date_time){
        Calendar calendar = Calendar.getInstance();
        Log.v(TAG,"text" + date_time.getText().toString());
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
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy h.mm aaa");
                        date_time.setText(simpleDateFormat.format(calendar.getTime()));
                        dateTime = date_time.getText().toString();
                        Log.v(TAG,"Timing:" + dateTime);
                    }
                };
                new TimePickerDialog(AddTaskPage.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(AddTaskPage.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}