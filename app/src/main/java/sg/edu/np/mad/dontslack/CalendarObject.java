package sg.edu.np.mad.dontslack;

import java.io.Serializable;

public class CalendarObject implements Serializable {
    private static String calendar_name;
    private static String date;
    private static String time;

    public CalendarObject(){

    }
    public static String getCalendarName()
    {
        return calendar_name;
    }

    public void setCalendarName(String name)
    {
        this.calendar_name = name;
    }

    public static String getCalendarDate()
    {
        return date;
    }

    public void setCalendarDate(String date)
    {
        this.date = date;
    }

    public static String getCalendarTime() { return time; }

    public void setCalendarTime(String time)
    {
        this.time = time;
    }
}


