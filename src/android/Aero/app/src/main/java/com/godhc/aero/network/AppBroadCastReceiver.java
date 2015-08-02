package com.godhc.aero.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.godhc.aero.R;
import com.godhc.aero.models.NetworkStateInfo;
import com.godhc.aero.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class AppBroadCastReceiver extends BroadcastReceiver {
    private static final String TAG = "AppBroadCastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Utils utils = new Utils(context);

        Logger
                .t(TAG)
                .i("Network connectivity change");

        int notificationId = 1;
        if (intent.getExtras() != null) {
            NetworkStateInfo currentNetworkStateInfo = utils.getCurrentNetworkState();
            currentNetworkStateInfo.save();

            Logger
                    .t(TAG)
                    .i("Saved network state info to db with id %d", currentNetworkStateInfo.getId());

            if (currentNetworkStateInfo.isActiveNetworkFound()) {
                if (currentNetworkStateInfo.isConnected()) {

                    Logger
                            .t(TAG)
                            .i("Network connected to %s via %s",
                                    currentNetworkStateInfo.getNetworkConnectionName(),
                                    currentNetworkStateInfo.getConnectionType()
                            );

                    utils.showLocalNotification(notificationId, R.drawable.ic_notification_on_icon, "Online",
                            String.format("Connected to %s via %s",
                                    currentNetworkStateInfo.getNetworkConnectionName(),
                                    currentNetworkStateInfo.getConnectionType()
                            ));

                } else {
                    Logger
                            .t(TAG)
                            .i("You are offline");

                    utils.showLocalNotification(notificationId, R.drawable.ic_notification_off_icon,
                            "Offline",
                            "You are offline");
                }

            } else {
                Logger
                        .t(TAG)
                        .i("No active network found. Are you offline?");

                utils.showLocalNotification(notificationId, R.drawable.ic_notification_off_icon,
                        "Offline",
                        "You are offline");
            }

        }
    }
}
