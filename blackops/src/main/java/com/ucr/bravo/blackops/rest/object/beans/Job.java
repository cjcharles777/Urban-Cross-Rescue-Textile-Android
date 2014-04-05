package com.ucr.bravo.blackops.rest.object.beans;

import java.util.ArrayList;

/**
 * Created by cedric on 3/5/14.
 */
public class Job
{
    private String id;
    private String title;
    private Agent requester;
    private ArrayList<Portal> targets;
    private int status;
    private Agent assignedTo;
    private String details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Agent getRequester() {
        return requester;
    }

    public void setRequester(Agent requester) {
        this.requester = requester;
    }

    public ArrayList<Portal> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<Portal> targets) {
        this.targets = targets;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Agent getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Agent assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
