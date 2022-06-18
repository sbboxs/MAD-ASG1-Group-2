package sg.edu.np.mad.dontslack;

import static android.app.AlertDialog.THEME_HOLO_DARK;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CalendarEventEditActivity extends AppCompatActivity
{
    private TextView eventDateTV;
    private final String TAG = "Add Task";
    DBHandler dbHandler = new DBHandler(this,null,null,1);
    private static String TaskName;
    private static String TaskDate;
    private static String TaskTime;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_event_edit);
        initWidgets();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        @SuppressLint("CutPasteId") EditText EventTimeET = findViewById(R.id.calEventTimeET);
        EventTimeET.setOnClickListener(v -> setTime(EventTimeET));
    }

    private void initWidgets()
    {
        eventDateTV = findViewById(R.id.eventDateTV);
    }

    public void saveEventAction(View view)
    {
        Button createTaskButton = findViewById(R.id.calAddEventBtn);
        createTaskButton.setOnClickListener(v -> {
            EditText newCalTaskName = findViewById(R.id.eventNameET);
            TaskName = newCalTaskName.getText().toString();
            TextView newCalTaskDate = findViewById(R.id.eventDateTV);
            TaskDate = newCalTaskDate.toString();
            @SuppressLint("CutPasteId") EditText newCalTaskTime = findViewById(R.id.calEventTimeET);
            TaskTime = newCalTaskTime.getText().toString();
            TaskObject ifTaskExist = dbHandler.findTask(TaskName);
            Log.v(TAG,"newTask1:" + ifTaskExist);
            if(ifTaskExist == null){
                Log.v(TAG,"newTask:" + TaskName);
                Log.v(TAG,"newTask:" + TaskDate);
                Log.v(TAG,"newTask:" + TaskTime);
                if(TaskName.equals("") || TaskDate.equals("") || TaskTime.equals("")){
                    Toast.makeText(CalendarEventEditActivity.this,"Please ensure all fields is filled.",Toast.LENGTH_SHORT).show();
                }
                else{
                    CalendarObject newCalTaskObject = new CalendarObject();
                    newCalTaskObject.setCalendarName(TaskName);
                    newCalTaskObject.setCalendarDate(TaskDate);
                    newCalTaskObject.setCalendarTime(TaskTime);
                    dbHandler.addCalendarTask(newCalTaskObject);
                    Toast.makeText(CalendarEventEditActivity.this,"Task Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(CalendarEventEditActivity.this, sg.edu.np.mad.dontslack.Calendar.class);
                    startActivity(myIntent);
                }
            }
            else{
                Toast.makeText(CalendarEventEditActivity.this,"Task already exist!",Toast.LENGTH_SHORT).show();
            }
        });
    }
        /**String eventName = eventNameET.getText().toString();
        //String eventTime = EventTImeET.getText().toString();
        CalendarEvent newEvent = new CalendarEvent(eventName, CalendarUtils.selectedDate, TaskObject.getCalendarTime());
        CalendarEvent.eventsList.add(newEvent);
        finish();*/



    public void setTime(EditText eventTimeET) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = (view1, hourOfDay, minute) -> {
            calendar.set(java.util.Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(java.util.Calendar.MINUTE,minute);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm aaa");
            eventTimeET.setText(simpleDateFormat.format(calendar.getTime()));
        };
        new TimePickerDialog(CalendarEventEditActivity.this, THEME_HOLO_DARK, timeSetListener,calendar.get(java.util.Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
    }
}
