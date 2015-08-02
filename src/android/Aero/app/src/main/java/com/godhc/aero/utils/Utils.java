package com.godhc.aero.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.godhc.aero.MainActivity;
import com.godhc.aero.R;
import com.godhc.aero.models.NetworkStateInfo;

public class Utils {
    private Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public NetworkStateInfo getCurrentNetworkState(){
        NetworkStateInfo networkStateInfo = new NetworkStateInfo();

        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null){
            networkStateInfo.setIsActiveNetworkFound(true);

            if (networkInfo.isConnected()){
                networkStateInfo.setIsConnected(true);
                networkStateInfo.setConnectionType(networkInfo.getTypeName());
                networkStateInfo.setNetworkConnectionName((networkInfo.getExtraInfo() != null && !networkInfo.getExtraInfo().isEmpty()) ? networkInfo.getExtraInfo() : "-");
            }
            else {
                networkStateInfo.setIsConnected(false);
            }
        }
        else
        {
            // No Active network, may be in Airplane mode
            networkStateInfo.setIsConnected(false);
            networkStateInfo.setIsActiveNetworkFound(false);

        }
        return networkStateInfo;
    }


    public void showLocalNotification(int notificationId, int notificationIcon, String contentTitle, String contentText) {
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

        // notificationId allows you to update the notification later on.
        mNotificationManager.notify(notificationId, mBuilder.build());
    }
}
