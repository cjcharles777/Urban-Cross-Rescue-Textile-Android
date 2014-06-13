package com.ucr.bravo.blackops.utils;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by cedric on 6/11/14.
 */
public class MapUtils
{
    public static void getScreenRange( GoogleMap googleMap)
    {
        LatLngBounds latLngBounds = googleMap.getProjection().getVisibleRegion().latLngBounds; //might not need

    }
}
