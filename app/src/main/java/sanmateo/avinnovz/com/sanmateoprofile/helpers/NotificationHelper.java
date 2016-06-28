package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class NotificationHelper {

    public static void displayNotification(final int id,final Context context, final String title,
                                           final String content, final Class cls) {
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.san_mateo_logo)
                        .setContentTitle(title)
                        .setContentText(content);
        if (cls != null) {
            final Intent intent = new Intent(context,cls);
            final PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
        }
        final NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(id, mBuilder.build());
    }
}
