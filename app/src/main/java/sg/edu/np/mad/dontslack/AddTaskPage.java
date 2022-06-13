package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskPage extends AppCompatActivity {
    DBHandler dbHandler = new DBHandler(this,null,null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_page);
        Bundle categoryBundle = getIntent().getExtras();
        String taskCategory = categoryBundle.getString("category");
        Button createTaskButton = findViewById(R.id.createTaskButton);
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText taskTitle = findViewById(R.id.taskTitle);
                EditText taskDetails = findViewById(R.id.taskDetails);
                EditText taskStartTime = findViewById(R.id.taskStartTime);
                EditText taskDeadLine = findViewById(R.id.taskDeadline);

                String TaskTitle = taskTitle.getText().toString();
                String TaskDetails = taskDetails.getText().toString();
                String TaskStartTime = taskStartTime.getText().toString();
                String TaskDeadLine = taskDeadLine.getText().toString();

                TaskObject newTaskObject = new TaskObject();
                newTaskObject.setTaskName(TaskTitle);
                newTaskObject.setTaskCategory(taskCategory);
                newTaskObject.setTaskDetails(TaskDetails);
                newTaskObject.setTaskStartTime(TaskStartTime);
                newTaskObject.setTaskDeadLine(TaskDeadLine);
                newTaskObject.setTaskStatus(false);
                dbHandler.addTask(newTaskObject);
                Toast.makeText(AddTaskPage.this,"Task Added Successfully", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(AddTaskPage.this,ToDoList.class);
                startActivity(myIntent);

            }
        });
    }
}