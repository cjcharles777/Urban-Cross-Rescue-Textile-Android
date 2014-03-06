package com.ucr.bravo.blackops.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.rest.object.Target;

import java.util.List;

/**
 * Created by cedric on 3/5/14.
 */
public class TargetListArrayAdapter extends ArrayAdapter<Target>
{

    private final Context context;
   // private final String[] values;

    public TargetListArrayAdapter(Context context, int textViewResourceId, List<Target> objects)
    {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_target, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        textView.setText(getItem(position).getTitle());

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


