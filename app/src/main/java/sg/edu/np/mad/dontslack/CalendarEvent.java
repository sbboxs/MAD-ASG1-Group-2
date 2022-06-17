package sg.edu.np.mad.dontslack;

import android.app.TimePickerDialog;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class CalendarEvent
{
    public static ArrayList<CalendarEvent> eventsList = new ArrayList<>();

    public static ArrayList<CalendarEvent> eventsForDate(LocalDate date)
    {
        ArrayList<CalendarEvent> events = new ArrayList<>();

        for(CalendarEvent event : eventsList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }


    private String name;
    private LocalDate date;
    private TimePickerDialog time;

    public CalendarEvent(String name, LocalDate date, TimePickerDialog time)
    {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public TimePickerDialog getTime() { return time; }

    public void setTime(TimePickerDialog time)
    {
        this.time = time;
    }


}

