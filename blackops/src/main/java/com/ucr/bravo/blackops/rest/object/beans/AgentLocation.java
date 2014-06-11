package com.ucr.bravo.blackops.rest.object.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cedric on 6/11/14.
 */
public class AgentLocation implements Serializable
{
    private String id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Date updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
