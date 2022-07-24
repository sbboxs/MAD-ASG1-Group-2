package sg.edu.np.mad.dontslack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notifyDon'tSlack")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Hi user!")
                .setContentText("You have " +8+" task to-do for today!" )
                .setPriority((NotificationCompat.PRIORITY_DEFAULT));

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, builder.build());
    }
}
