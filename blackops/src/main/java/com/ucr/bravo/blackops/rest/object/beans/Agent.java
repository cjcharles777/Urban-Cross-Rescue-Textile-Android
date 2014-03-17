package com.ucr.bravo.blackops.rest.object.beans;

import java.io.Serializable;

/**
 * Created by cedric on 2/12/14.
 */
public class Agent implements Serializable
{
    private String ign;
    private String email;
    private String id;
    private Boolean authorized;
    private Integer clearance;

    public String getIgn() {
        return ign;
    }

    public void setIgn(String ign) {
        this.ign = ign;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }

    public Integer getClearance() {
        return clearance;
    }

    public void setClearance(Integer clearance) {
        this.clearance = clearance;
    }
}
