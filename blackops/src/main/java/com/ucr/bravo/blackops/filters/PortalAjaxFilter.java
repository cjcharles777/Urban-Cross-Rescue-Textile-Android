package com.ucr.bravo.blackops.filters;

import android.widget.Filter;

import com.google.gson.Gson;
import com.ucr.bravo.blackops.rest.RestCallAction;
import com.ucr.bravo.blackops.rest.object.RestCallObject;
import com.ucr.bravo.blackops.rest.object.beans.Portal;
import com.ucr.bravo.blackops.rest.object.beans.Target;
import com.ucr.bravo.blackops.rest.object.request.BaseRequest;

import java.io.IOException;

/**
 * Created by cedric on 3/14/14.
 */
public abstract class PortalAjaxFilter extends BaseAjaxFilter
{
    private final static String PORTAL_SEARCH_EXTENSION = "/portal/search/name";

    private String requesterId;

    public PortalAjaxFilter(String requesterId)
    {
        super();
        this.requesterId = requesterId;
    }

    @Override
    protected FilterResults performAjaxFiltering(CharSequence constraint)
    {
        if (constraint != null)
        {
            try
            {

                Portal portal = new Portal();
                portal.setName(constraint.toString());
                Gson gson = new Gson();
                String json = gson.toJson(new BaseRequest<Portal>(requesterId, portal));
                RestCallObject restCall = new RestCallObject(PORTAL_SEARCH_EXTENSION,"POST",json);

                String jsonResults = RestCallAction.executeRestCall(restCall);
                return  convertJsonResultsToFilterResults(jsonResults);
            }
            catch (IOException e)
            {
                return null;
            }
        }
        return null;
    }

    public abstract FilterResults convertJsonResultsToFilterResults(String json);


}
