package sg.edu.np.mad.dontslack;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.List;

public class CalendarEventAdapter extends ArrayAdapter<CalendarEvent>
{
    public CalendarEventAdapter(@NonNull Context context, List<CalendarEvent> events)
    {
        super(context, 0, events);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        CalendarEvent event = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.calendar_event_cell, parent, false);
        }

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);
        String eventTitle = event.getName() +" "+ event.getTime();
        eventCellTV.setText(eventTitle);
        return convertView;
    }
}

