package sg.edu.np.mad.dontslack;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        // Set the Activity to start in a new, empty task
        Intent newIntent = new Intent(context,NotificationDetails.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //Pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,newIntent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notifyDon'tSlack")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Hi user!")
                .setContentText("You have " +8+" task to-do for today!" )
                //Cancel Notification upon clicking
                .setAutoCancel(false)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
                //.addAction(R.drawable.dont_slcak,"View Details",pendingIntent);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, builder.build());
    }
}
