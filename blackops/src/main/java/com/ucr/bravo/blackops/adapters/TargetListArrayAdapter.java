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
import com.ucr.bravo.blackops.rest.object.beans.Agent;
import com.ucr.bravo.blackops.rest.object.beans.Job;
import com.ucr.bravo.blackops.rest.object.beans.Portal;
import com.ucr.bravo.blackops.utils.LocationUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by cedric on 3/5/14.
 */
public class TargetListArrayAdapter extends ArrayAdapter<Job>
{

    private final Context context;
    private final LocationActivity activity;
   // private final String[] values;

    public TargetListArrayAdapter(Context context, int textViewResourceId, List<Job> objects, LocationActivity activity)
    {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_job, parent, false);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.titleTextView);
        TextView portalNamesTextView = (TextView) rowView.findViewById(R.id.portalNamesTextView);
        TextView requesterNameTextView = (TextView) rowView.findViewById(R.id.requesterNameTextView);
        TextView distanceTextView = (TextView) rowView.findViewById(R.id.distanceTextView);
        Location location = activity.getLocation();

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        Job posJob = getItem(position);
        titleTextView.setText(posJob.getTitle());
        List<Portal> portals = posJob.getTargets();
        if(portals!= null)
        {
            portalNamesTextView.setText(portals.size()+" portals");
        }
        Agent requester = posJob.getRequester();
        if(requester != null)
        {
            requesterNameTextView.setText(requester.getIgn());
        }
        else
        {
            requesterNameTextView.setText("_ADA_");
        }
        BigDecimal minDistance = new BigDecimal(Float.MAX_VALUE);
        if (portals.size() > 0)
        {

            for (Portal p : portals) {
                Location portalLocation = new Location(location);
                portalLocation.setLatitude(p.getLatitude().doubleValue());
                portalLocation.setLongitude(p.getLongitude().doubleValue());
                BigDecimal portalDistance = new BigDecimal(location.distanceTo(portalLocation));
                Log.e(LocationUtils.APPTAG, "Distance to " + p.getName() + " is " + portalDistance.toString());
                if (portalDistance.compareTo(minDistance) < 0) {
                    minDistance = portalDistance;
                    Log.e(LocationUtils.APPTAG, "New Min Distance : " + minDistance.toString());
                }
            }
            minDistance = minDistance.divide(new BigDecimal(1000));
        }
        else
        {
            minDistance = new BigDecimal(0.0);
        }
        minDistance = minDistance.setScale(2, BigDecimal.ROUND_HALF_UP);

        DecimalFormat df = new DecimalFormat();

        df.setMaximumFractionDigits(2);

        df.setMinimumFractionDigits(2);

        df.setGroupingUsed(false);
        distanceTextView.setText(df.format(minDistance) + " km");
        

        // Change icon based on name
/**
        String s = values[position];

        System.out.println(s);

        if (s.equals("WindowsMobile")) {
            imageView.setImageResource(R.drawable.windowsmobile_logo);
        } else if (s.equals("iOS")) {
            imageView.setImageResource(R.drawable.ios_logo);
        } else if (s.equals("Blackberry")) {
            imageView.setImageResource(R.drawable.blackberry_logo);
        } else {
            imageView.setImageResource(R.drawable.android_logo);
        }
**/
        return rowView;
    }

}


