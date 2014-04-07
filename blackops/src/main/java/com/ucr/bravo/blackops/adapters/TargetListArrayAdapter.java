package com.ucr.bravo.blackops.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.rest.object.beans.Agent;
import com.ucr.bravo.blackops.rest.object.beans.Job;
import com.ucr.bravo.blackops.rest.object.beans.Portal;

import java.util.List;

/**
 * Created by cedric on 3/5/14.
 */
public class TargetListArrayAdapter extends ArrayAdapter<Job>
{

    private final Context context;
   // private final String[] values;

    public TargetListArrayAdapter(Context context, int textViewResourceId, List<Job> objects)
    {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_job, parent, false);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.titleTextView);
        TextView portalNamesTextView = (TextView) rowView.findViewById(R.id.portalNamesTextView);
        TextView requesterNameTextView = (TextView) rowView.findViewById(R.id.requesterNameTextView);
        TextView distanceTextView = (TextView) rowView.findViewById(R.id.distanceTextView);


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


        distanceTextView.setText("0.0 mi");
        

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


