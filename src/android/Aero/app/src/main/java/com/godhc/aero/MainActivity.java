package com.godhc.aero;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.godhc.aero.adapters.EventsAdapter;
import com.godhc.aero.models.NetworkStateInfo;
import com.godhc.aero.network.AppBroadCastReceiver;
import com.godhc.aero.utils.Utils;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity implements EventsAdapter.RemoveEventClickListener {
    private final static String TAG = "MainActivity";

    RecyclerView eventsRecyclerView;
    EventsAdapter eventsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (savedInstanceState == null)
            processCurrentNetworkStateInfo();

        // Setup the Recycler View
        eventsRecyclerView = (RecyclerView) findViewById(R.id.activity_main_rv_events);
        eventsAdapter = new EventsAdapter(this);
        eventsAdapter.setRemoveEventClickListener(this);
        eventsAdapter.loadData();
        eventsRecyclerView.setAdapter(eventsAdapter);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void processCurrentNetworkStateInfo() {
        Utils utils = new Utils(this);
        int notificationId = 1;

        NetworkStateInfo currentNetworkStateInfo = utils.getCurrentNetworkState();

        // if there is no existing data, then this is the first time launch
        // hence get the info and save
        if (NetworkStateInfo.first(NetworkStateInfo.class) == null) {
            currentNetworkStateInfo.save();

            Logger
                    .t(TAG)
                    .i("Saved network state info to db with id %d", currentNetworkStateInfo.getId());
        }

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

    @Override
    public void onRemoveEvent(long id) {
        NetworkStateInfo networkStateInfo = NetworkStateInfo.findById(NetworkStateInfo.class, id);
        if (networkStateInfo != null) {
            Logger
                    .t(TAG)
                    .i("Removing with id %d", id);

            networkStateInfo.delete();
            eventsAdapter.loadData();
        }
    }

}
