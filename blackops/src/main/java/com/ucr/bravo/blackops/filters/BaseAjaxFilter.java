package com.ucr.bravo.blackops.filters;

import android.widget.Filter;

import com.ucr.bravo.blackops.rest.object.RestCallObject;

import java.io.IOException;

/**
 * Created by cedric on 3/14/14.
 */
public abstract class BaseAjaxFilter extends Filter
{



    public BaseAjaxFilter()
    {
        super();
    }
    @Override
    protected FilterResults performFiltering(CharSequence constraint)
    {

        if (constraint != null)
        {
            return  performAjaxFiltering(constraint);
        }
        return null;
    }



    protected abstract FilterResults performAjaxFiltering(CharSequence constraint);


}
