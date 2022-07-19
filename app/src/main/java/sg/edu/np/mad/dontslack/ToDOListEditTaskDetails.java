package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

        //Get bundle from previous activity
        Bundle taskBundle = getIntent().getBundleExtra("Bundle");
        TaskObject currentTask = (TaskObject) taskBundle.getSerializable("TaskObject");

        //Store current task name
        originalTaskName = currentTask.getTaskName();

        //Back button
        ImageView backHomePage = findViewById(R.id.editDetailBackButton);
        backHomePage.setOnClickListener(v -> {
            Intent myIntent = new Intent(ToDOListEditTaskDetails.this,ToDoListTaskDetails.class);
            Bundle args = new Bundle();
            args.putSerializable("TaskObject", (Serializable) currentTask);
            myIntent.putExtra("Bundle",args);
            startActivity(myIntent);
        });

        //Setting current task details
        @SuppressLint("CutPasteId") EditText editCurrentTaskTitle = findViewById(R.id.editCurrentTaskTitle);
        editCurrentTaskTitle.setText(currentTask.getTaskName());

        @SuppressLint("CutPasteId") EditText editCurrentTaskDescription = findViewById(R.id.editCurrentTaskDescription);
        editCurrentTaskDescription.setText(currentTask.getTaskDescription());

        @SuppressLint("CutPasteId") EditText editCurrentTaskStartTime = findViewById(R.id.editCurrentTaskStartTime);
        editCurrentTaskStartTime.setText(currentTask.getTaskStartTime());

        @SuppressLint("CutPasteId") EditText editCurrentTaskDeadLine= findViewById(R.id.editCurrentTaskDeadline);
        editCurrentTaskDeadLine.setText(currentTask.getTaskDeadLine());

        //Edit task start time and deadline
        editCurrentTaskStartTime.setOnClickListener(v -> showDateTimeDialog(editCurrentTaskStartTime));

        editCurrentTaskDeadLine.setOnClickListener(v -> showDateTimeDialog(editCurrentTaskDeadLine));



        //Save task button
        Button saveTaskButton = findViewById(R.id.saveEditTaskButton);
        saveTaskButton.setOnClickListener(v -> {
            @SuppressLint("CutPasteId") EditText newTaskTitle = findViewById(R.id.editCurrentTaskTitle);
            etTaskTitle = newTaskTitle.getText().toString();
            @SuppressLint("CutPasteId") EditText newTaskDescription = findViewById(R.id.editCurrentTaskDescription);
            etTaskDescription = newTaskDescription.getText().toString();
            @SuppressLint("CutPasteId") EditText newTaskStartTime = findViewById(R.id.editCurrentTaskStartTime);
            etTaskStartTIme = newTaskStartTime.getText().toString();
            @SuppressLint("CutPasteId") EditText newTaskDeadLine = findViewById(R.id.editCurrentTaskDeadline);
            etTaskDeadLine = newTaskDeadLine.getText().toString();

            //Check if all fields are filled
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
            }
        });
    }

    public void showDateTimeDialog(EditText date_time){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

            TimePickerDialog.OnTimeSetListener timeSetListener = (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy h:mm aaa");
                date_time.setText(simpleDateFormat.format(calendar.getTime()));
            };
            new TimePickerDialog(ToDOListEditTaskDetails.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
        };

        new DatePickerDialog(ToDOListEditTaskDetails.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}