package sg.edu.np.mad.dontslack;

import java.time.LocalDate;
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



    private String calendar_name;
    private LocalDate date;
    private String time;

    public CalendarEvent(String name, LocalDate date, String time)
    {
        this.calendar_name = name;
        this.date = date;
        this.time = time;
    }

    public String getName()
    {
        return calendar_name;
    }

    public void setName(String name)
    {
        this.calendar_name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public String getTime() { return time; }

    public void setTime(String time)
    {
        this.time = time;
    }

}

