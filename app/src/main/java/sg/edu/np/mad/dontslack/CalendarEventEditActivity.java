package sg.edu.np.mad.dontslack;

import static android.app.AlertDialog.THEME_HOLO_DARK;
import static androidx.appcompat.app.AlertDialog.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CalendarEventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView eventDateTV;
    private static String EventTImeET;
    TimePickerDialog time;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_event_edit);
        initWidgets();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        @SuppressLint("CutPasteId") EditText EventTimeET = findViewById(R.id.EventTimeET);
        EventTimeET.setOnClickListener(v -> setTime(EventTimeET));
    }

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
    }

    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        //String eventTime = EventTImeET.getText().toString();
        CalendarEvent newEvent = new CalendarEvent(eventName, CalendarUtils.selectedDate, time);
        CalendarEvent.eventsList.add(newEvent);
        finish();
    }

    public void setTime(EditText eventTimeET) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = (view1, hourOfDay, minute) -> {
            calendar.set(java.util.Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(java.util.Calendar.MINUTE,minute);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm aaa");
            eventTimeET.setText(simpleDateFormat.format(calendar.getTime()));
        };
        int style = THEME_HOLO_DARK;
        new TimePickerDialog(CalendarEventEditActivity.this, style, timeSetListener,calendar.get(java.util.Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
    }
}
