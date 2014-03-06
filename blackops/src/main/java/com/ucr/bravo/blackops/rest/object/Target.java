package com.ucr.bravo.blackops.rest.object;

/**
 * Created by cedric on 3/5/14.
 */
public class Target
{
    private int id;
    private String title;
    private Agent requester;
    private Portal target;
    private int status;
    private Agent assignedTo;
    private String details;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Portal getTarget() {
        return target;
    }

    public void setTarget(Portal target) {
        this.target = target;
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
