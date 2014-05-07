package com.ucr.bravo.blackops.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.activities.LocationActivity;
import com.ucr.bravo.blackops.rest.object.beans.Agent;
import com.ucr.bravo.blackops.rest.object.beans.Job;

import java.util.List;

/**
 * Created by cedric on 5/7/14.
 */
public class UserSelectListArrayAdapter extends ArrayAdapter<Agent>
{
    private final Context context;


    public UserSelectListArrayAdapter(Context context, int textViewResourceId, List<Agent> objects)
    {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        this.context = context;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_agent, parent, false);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);
        Agent posAgent = getItem(position);
        checkBox.setText(posAgent.getIgn());
        return rowView;
    }
}
