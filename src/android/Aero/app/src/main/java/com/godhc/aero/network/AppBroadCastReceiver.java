package com.godhc.aero.network;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.godhc.aero.MainActivity;
import com.godhc.aero.R;

public class AppBroadCastReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkStateReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Network connectivity change");
        int notificationId = 1;
        Toast
                .makeText(context, "Network connectivity change", Toast.LENGTH_LONG)
                .show();
        if (intent.getExtras() != null) {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo ni = connectivityManager.getActiveNetworkInfo();

            if (ni != null && ni.isConnected()) {
                Log.i(TAG, "Network " + ni.getTypeName() + " connected");
                Toast
                        .makeText(context, "Network " + ni.getTypeName() + " connected", Toast.LENGTH_LONG)
                        .show();
                showLocalNotification(context, notificationId, R.drawable.ic_notification_on_icon, "Connected", "Network " + ni.getTypeName() + " connected");
            } else {
                Log.d(TAG, "There's no network connectivity");
                Toast
                        .makeText(context, "There's no network connectivity", Toast.LENGTH_LONG)
                        .show();

                showLocalNotification(context, notificationId, R.drawable.ic_notification_off_icon, "Not Connected", "There's no network connectivity");
            }
        }
    }

    private void showLocalNotification(Context context, int notificationId, int notificationIcon, String contentTitle, String contentText) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(notificationIcon)
                        .setColor(context.getResources()
                                .getColor(R.color.colorPrimary))
                        .setContentTitle(context.getResources()
                                .getString(R.string.app_name)
                                + " - " + contentTitle)
                        .setContentText(contentText)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setLights(context.getResources().getColor(R.color.colorPrimary), 300, 700);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);


// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(notificationId, mBuilder.build());
    }
}
