package sg.edu.np.mad.dontslack;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReminderBroadcast extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        // Set the Activity to start in a new, empty task
        Intent newIntent = new Intent(context,NotificationDetails.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //Pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,newIntent,0);

        //Getting total to do task and calendar event
        DBHandler dbHandler = new DBHandler(context,null,null,1);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(System.currentTimeMillis());
        Log.v("Here", date);
        Cursor taskCursor = dbHandler.readTaskByDate(date);
        Cursor eventCursor = dbHandler.readCalendarTaskData(date);
        int totalTask = taskCursor.getCount();
        int totalEvent = eventCursor.getCount();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notifyDon'tSlack")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Hi user!")
                .setContentText("You have " +totalTask+" to-do task and "+totalEvent+" calendar event for today." )
                //Cancel Notification upon clicking
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.mipmap.ic_informationicon,"View Details",pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200, builder.build());
    }
}
