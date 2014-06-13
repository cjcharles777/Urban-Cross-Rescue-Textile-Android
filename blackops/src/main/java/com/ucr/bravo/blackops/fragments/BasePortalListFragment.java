package com.ucr.bravo.blackops.fragments;

import android.support.v4.app.Fragment;

import com.ucr.bravo.blackops.rest.object.beans.Portal;

import java.util.ArrayList;

/**
 * Created by cedric on 6/13/14.
 */
public abstract class BasePortalListFragment extends Fragment {
    protected ArrayList<Portal> listPortal;

    public ArrayList<Portal> getListPortal() {
        return listPortal;
    }

    public void setListPortal(ArrayList<Portal> listPortal) {
        this.listPortal = listPortal;
    }
}
