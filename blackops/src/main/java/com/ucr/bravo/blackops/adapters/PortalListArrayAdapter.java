package com.ucr.bravo.blackops.adapters;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.activities.LocationActivity;
import com.ucr.bravo.blackops.rest.object.beans.Job;
import com.ucr.bravo.blackops.rest.object.beans.Portal;
import com.ucr.bravo.blackops.utils.LocationUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by cedric on 3/19/14.
 */
public class PortalListArrayAdapter extends ArrayAdapter<Portal>
{


    private final Context context;
    private final LocationActivity activity;

    public PortalListArrayAdapter(Context context, int textViewResourceId, List<Portal> objects, LocationActivity activity)
    {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.activity = activity;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_portal, parent, false);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.titleTextView);
        final TextView portalAddressTextView = (TextView) rowView.findViewById(R.id.portalAddressTextView);
        TextView distanceTextView = (TextView) rowView.findViewById(R.id.distanceTextView);
        Location location = activity.getLocation();
        Portal posPortal = getItem(position);
        titleTextView.setText(posPortal.getName());
        Location posPortalLocation = new Location(location);
        posPortalLocation.setLongitude(posPortal.getLongitude().doubleValue());
        posPortalLocation.setLatitude(posPortal.getLatitude().doubleValue());
        LocationActivity.GetAddressTask addressTask = new LocationActivity.GetAddressTask(context) {
            @Override
            protected void onReceivedAddress(String address) {
                portalAddressTextView.setText(address);
            }
        };

        activity.getAddress(rowView, posPortalLocation, addressTask);

        BigDecimal portalDistance = new BigDecimal(location.distanceTo(posPortalLocation));
        portalDistance = portalDistance.divide(new BigDecimal(1000));
        Log.e(LocationUtils.APPTAG, "Distance to " + posPortal.getName() + " is " + portalDistance.toString());
        DecimalFormat df = new DecimalFormat();

        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        df.setGroupingUsed(false);
        distanceTextView.setText(df.format(portalDistance) + " km");

        return rowView;
    }
}
