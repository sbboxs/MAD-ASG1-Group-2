package sg.edu.np.mad.dontslack;

import static sg.edu.np.mad.dontslack.CalendarUtils.daysInMonthArray;
import static sg.edu.np.mad.dontslack.CalendarUtils.monthYearFromDate;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.time.LocalDate;
import java.util.ArrayList;

public class Calendar extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    DBHandler dbHandler = new DBHandler(this, null,null,1);
    ArrayList<CalendarEvent> dailyEvents = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        //back button
        ImageView backHomePage = findViewById(R.id.backHome);
        backHomePage.setOnClickListener(v -> {
            Intent myIntent = new Intent(Calendar.this, HomePage.class);
            startActivity(myIntent);
        });
        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();


        eventListView.setOnItemLongClickListener((parent, view, position, id) -> {
            CalendarEvent calendarEvent = dailyEvents.get(position);
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Delete Event");
            alertDialog.setMessage("Are you sure you want to delete this event?");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", (dialog, which) -> dialog.dismiss());
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes, I'm sure.", (dialog, which) -> {
                dbHandler.deleteCalendarTask(calendarEvent);
                Toast.makeText(getApplicationContext(),"Event deleted successfully!",Toast.LENGTH_SHORT).show();
                this.recreate();
            });
            alertDialog.show();
            return false;
        });
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

        setCalendarEventAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume()
    {
        super.onResume();
        setCalendarEventAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setCalendarEventAdapter() {
        storeCalDataToArray(String.valueOf(CalendarUtils.selectedDate));
        CalendarEventAdapter eventAdapter = new CalendarEventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(this, CalendarEventEditActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void storeCalDataToArray(String date) {
        dailyEvents.clear();
        Cursor cursor = dbHandler.readCalendarTaskData(date);
        if (cursor.getCount() == 0) {
            Toast.makeText(this,"No Event Yet", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                CalendarEvent task = new CalendarEvent();
                task.setName((cursor.getString(0)));
                task.setDate(LocalDate.parse(cursor.getString(1)));
                task.setTime(cursor.getString(2));
                dailyEvents.add(task);
            }
        }
    }
}

