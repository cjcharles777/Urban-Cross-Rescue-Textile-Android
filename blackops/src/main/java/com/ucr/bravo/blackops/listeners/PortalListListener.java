package com.ucr.bravo.blackops.listeners;

import com.ucr.bravo.blackops.rest.object.beans.Portal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cedric on 6/13/14.
 */
public interface PortalListListener
{
    // TODO: Update argument type and name
    public ArrayList<Portal> retrieveCurrentPortalList();
    public void onSubmitPortalsListButtonPressed(List<Portal> pList);
}
