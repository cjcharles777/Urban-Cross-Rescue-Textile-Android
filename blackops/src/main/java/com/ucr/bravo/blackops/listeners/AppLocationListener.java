package com.ucr.bravo.blackops.listeners;

import android.location.Location;

import com.google.android.gms.location.LocationListener;

/**
 * Created by cedric on 6/9/14.
 */
public interface AppLocationListener extends LocationListener
{
    public Location getCurrentLocation();
    public void startPeriodicUpdates();
    public void stopPeriodicUpdates();
}
