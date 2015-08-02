package com.godhc.aero;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.godhc.aero.models.NetworkStateInfo;
import com.godhc.aero.utils.Utils;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Utils utils = new Utils(this);
        int notificationId = 1;

        NetworkStateInfo currentNetworkStateInfo = utils.getCurrentNetworkState();

        if (currentNetworkStateInfo.isActiveNetworkFound()) {
            if (currentNetworkStateInfo.isConnected()) {

                Logger
                        .t(TAG)
                        .i("Network connected to %s via %s at %s",
                                currentNetworkStateInfo.getNetworkConnectionName(),
                                currentNetworkStateInfo.getConnectionType(),
                                currentNetworkStateInfo.getEventRaisedDate().toString()
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
