package com.ucr.bravo.blackops;

import android.app.Application;

import com.ucr.bravo.blackops.rest.object.beans.Agent;

/**
 * Created by cedric on 3/17/14.
 */
public class BlackOpsApplication extends Application
{
    private Agent sessionAgent;

    public Agent getSessionAgent() {
        return sessionAgent;
    }

    public void setSessionAgent(Agent sessionAgent) {
        this.sessionAgent = sessionAgent;
    }

    public String getRequesterId()
    {
        if(sessionAgent != null)
        {
            return sessionAgent.getId();
        }
        return null;
    }
}
