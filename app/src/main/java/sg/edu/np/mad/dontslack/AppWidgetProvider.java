package sg.edu.np.mad.dontslack;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.TextView;

import org.w3c.dom.Text;


public class AppWidgetProvider extends android.appwidget.AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for(int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context,HomePage.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget);
            views.setOnClickPendingIntent(R.id.widget_button,pendingIntent);

            //TextView view = findIdByView
            //int noOfTask = db.getProfilesCount();
            appWidgetManager.updateAppWidget(appWidgetId,views);

        }
    }


}
