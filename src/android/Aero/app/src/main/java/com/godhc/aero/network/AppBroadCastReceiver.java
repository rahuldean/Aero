package com.godhc.aero.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class AppBroadCastReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkStateReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Network connectivity change");
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

            } else {
                Log.d(TAG, "There's no network connectivity");
                Toast
                        .makeText(context, "There's no network connectivity", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}
