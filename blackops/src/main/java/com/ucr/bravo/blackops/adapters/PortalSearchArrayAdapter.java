package com.ucr.bravo.blackops.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.google.gson.Gson;
import com.ucr.bravo.blackops.filters.PortalAjaxFilter;
import com.ucr.bravo.blackops.rest.object.beans.Portal;
import com.ucr.bravo.blackops.rest.object.response.BaseResponse;
import com.ucr.bravo.blackops.rest.utils.JsonResponseConversionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cedric on 3/14/14.
 */
public class PortalSearchArrayAdapter extends ArrayAdapter<Portal>
{

    private List<Portal> listObj;
    private String requesterId;
    public PortalSearchArrayAdapter(Context context, int resource, String requesterId)
    {
        super(context, resource);
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
                    listObj.clear();
                    BaseResponse<List> response = JsonResponseConversionUtil.convertToResponse(json);
                    if(response.getResult() != null)
                    {
                        //TODO: Finish this here!!!!
                    }
                    return null;
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
