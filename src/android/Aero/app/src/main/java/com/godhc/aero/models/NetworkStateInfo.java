package com.godhc.aero.models;

import java.util.Calendar;
import java.util.Date;

public class NetworkStateInfo {
    boolean isActiveNetworkFound;
    boolean isConnected;
    String connectionType;
    String networkConnectionName;
    Date eventRaisedDate;

    public boolean isConnected() {
        return isConnected;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public String getNetworkConnectionName() {
        return networkConnectionName;
    }

    public Date getEventRaisedDate() {
        return eventRaisedDate;
    }

    public boolean isActiveNetworkFound() {
        return isActiveNetworkFound;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public void setNetworkConnectionName(String networkConnectionName) {
        this.networkConnectionName = networkConnectionName;
    }

    public void setEventRaisedDate(Date eventRaisedDate) {
        this.eventRaisedDate = eventRaisedDate;
    }

    public void setIsActiveNetworkFound(boolean isActiveNetworkFound) {
        this.isActiveNetworkFound = isActiveNetworkFound;
    }

    public NetworkStateInfo() {
        super();
        this.eventRaisedDate = Calendar.getInstance().getTime();
    }

    public NetworkStateInfo(boolean isConnected, String connectionType, String networkConnectionName, boolean isActiveNetworkFound) {
        this.isConnected = isConnected;
        this.connectionType = connectionType;
        this.networkConnectionName = networkConnectionName;
        this.eventRaisedDate = Calendar.getInstance().getTime();
        this.isActiveNetworkFound = isActiveNetworkFound;
    }
}
