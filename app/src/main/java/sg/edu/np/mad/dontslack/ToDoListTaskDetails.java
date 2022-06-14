package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class ToDoListTaskDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_task_details);
        Bundle taskBundle = getIntent().getBundleExtra("Bundle");
        TaskObject currentTask = (TaskObject) taskBundle.getSerializable("TaskObject");

        TextView taskTitle = findViewById(R.id.taskTitle);
        taskTitle.setText(currentTask.getTaskName());

        TextView taskDescription = findViewById(R.id.taskDiscription);
        taskDescription.setText(currentTask.getTaskDescription());

        TextView taskStartTime = findViewById(R.id.taskStartTIme);
        taskStartTime.setText(currentTask.getTaskStartTime());

        TextView taskDeadLine = findViewById(R.id.tasktDeadLine);
        taskDeadLine.setText(currentTask.getTaskDeadLine());

        Button editTaskButton = findViewById(R.id.editTaskButton);
        editTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent (ToDoListTaskDetails.this, ToDOListEditTaskDetails.class);
                Bundle args = new Bundle();
                args.putSerializable("TaskObject", (Serializable) currentTask);
                myIntent.putExtra("Bundle",args);
                startActivity(myIntent);
            }
        });
    }
}