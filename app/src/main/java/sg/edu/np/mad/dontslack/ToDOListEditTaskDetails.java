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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ToDOListEditTaskDetails extends AppCompatActivity {
    private final String TAG = "Edit Task";
    private static String etTaskTitle;
    private static String etTaskDescription;
    private static String etTaskStartTIme;
    private static String etTaskDeadLine;
    private static String originalTaskName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_dolist_edit_task_details);
        DBHandler dbHandler = new DBHandler(this,null,null,1);

        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        Bundle taskBundle = getIntent().getBundleExtra("Bundle");
        TaskObject currentTask = (TaskObject) taskBundle.getSerializable("TaskObject");

        originalTaskName = currentTask.getTaskName();

        EditText editCurrentTaskTitle = findViewById(R.id.editCurrentTaskTitle);
        editCurrentTaskTitle.setText(currentTask.getTaskName());

        EditText editCurrentTaskDescription = findViewById(R.id.editCurrentTaskDescription);
        editCurrentTaskDescription.setText(currentTask.getTaskDescription());

        EditText editCurrentTaskStartTime = findViewById(R.id.editCurrentTaskStartTime);
        editCurrentTaskStartTime.setText(currentTask.getTaskStartTime());

        EditText editCurrentTaskDeadLine= findViewById(R.id.editCurrentTaskDeadline);
        editCurrentTaskDeadLine.setText(currentTask.getTaskDeadLine());

        editCurrentTaskStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(editCurrentTaskStartTime);
            }
        });

        editCurrentTaskDeadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(editCurrentTaskDeadLine);
            }
        });

        Button createTaskButton = findViewById(R.id.saveEditTaskButton);
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newTaskTitle = findViewById(R.id.editCurrentTaskTitle);
                etTaskTitle = newTaskTitle.getText().toString();
                EditText newTaskDescription = findViewById(R.id.editCurrentTaskDescription);
                etTaskDescription = newTaskDescription.getText().toString();
                EditText newTaskStartTime = findViewById(R.id.editCurrentTaskStartTime);
                etTaskStartTIme = newTaskStartTime.getText().toString();
                EditText newTaskDeadLine = findViewById(R.id.editCurrentTaskDeadline);
                etTaskDeadLine = newTaskDeadLine.getText().toString();

                Log.v(TAG,"editTask:" + etTaskTitle);
                Log.v(TAG,"editTask::" + etTaskDescription);
                Log.v(TAG,"editTask:" + etTaskStartTIme);
                Log.v(TAG,"editTask:" + etTaskDeadLine);
                if(etTaskTitle.equals("") || etTaskDescription.equals("") || etTaskStartTIme.equals("") || etTaskDeadLine.equals("")){
                    Toast.makeText(ToDOListEditTaskDetails.this,"Please ensure all fields is filled.",Toast.LENGTH_SHORT).show();
                }
                else{
                    currentTask.setTaskName(etTaskTitle);
                    currentTask.setTaskDescription(etTaskDescription);
                    currentTask.setTaskStartTime(etTaskStartTIme);
                    currentTask.setTaskDeadLine(etTaskDeadLine);

                    dbHandler.updateTaskData(currentTask,originalTaskName);
                    Toast.makeText(ToDOListEditTaskDetails.this,"Task Edited Successfully", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(ToDOListEditTaskDetails.this,ToDoListTaskDetails.class);
                    Bundle args = new Bundle();
                    args.putSerializable("TaskObject", (Serializable) currentTask);
                    myIntent.putExtra("Bundle",args);
                    startActivity(myIntent);
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
                        date_time.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(ToDOListEditTaskDetails.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(ToDOListEditTaskDetails.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}