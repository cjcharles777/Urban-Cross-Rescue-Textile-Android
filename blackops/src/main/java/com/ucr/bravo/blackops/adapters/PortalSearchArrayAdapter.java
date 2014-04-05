package com.ucr.bravo.blackops.adapters;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.google.gson.reflect.TypeToken;
import com.ucr.bravo.blackops.filters.PortalAjaxFilter;
import com.ucr.bravo.blackops.rest.object.beans.Portal;
import com.ucr.bravo.blackops.rest.object.response.BaseResponse;
import com.ucr.bravo.blackops.rest.utils.JsonResponseConversionUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cedric on 3/14/14.
 */
public class PortalSearchArrayAdapter extends ArrayAdapter<Portal>
{

    private List<Portal> listObj;
    private String requesterId;
    public PortalSearchArrayAdapter(Activity context, String requesterId)
    {
        super(context, android.R.layout.simple_dropdown_item_1line);
        listObj = new ArrayList<Portal>();
        this.requesterId = requesterId;
    }

    @Override
    public Portal getItem(int index)
    {
        return listObj.get(index);
    }
    @Override
    public int getCount() {
        return listObj.size();
    }
    @Override
    public Filter getFilter()
    {
        Filter myFilter =
            new PortalAjaxFilter(requesterId)
            {

                @Override
                public FilterResults convertJsonResultsToFilterResults(String json)
                {
                    FilterResults filterResults = new FilterResults();
                    listObj.clear();
                    BaseResponse<List> response = JsonResponseConversionUtil.convertToResponse(json);
                    if(response.getResult() != null && response.getResult().equals("SUCCESS"))
                    {
                        Type listType = new TypeToken<ArrayList<Portal>>() {}.getType();
                        List searchResults = (List<Portal>) JsonResponseConversionUtil.convertMessageToObject(response.getMessage(), listType);
                        listObj = searchResults;
                        filterResults.values = searchResults;
                        filterResults.count = searchResults.size();

                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults)
                {
                    if (filterResults != null && filterResults.count > 0)
                    {
                        notifyDataSetChanged();
                    }
                    else
                    {
                        notifyDataSetInvalidated();
                    }
                }
            };
        return myFilter;
    }

}
