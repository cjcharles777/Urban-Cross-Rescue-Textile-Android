package com.ucr.bravo.blackops.rest.object.beans;

import java.math.BigDecimal;

/**
 * Created by cedric on 6/11/14.
 */
public class LocationBounds
{
    BigDecimal north;
    BigDecimal south;
    BigDecimal east;
    BigDecimal west;

    public LocationBounds(BigDecimal north, BigDecimal south, BigDecimal east, BigDecimal west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    public BigDecimal getNorth() {
        return north;
    }

    public void setNorth(BigDecimal north) {
        this.north = north;
    }

    public BigDecimal getSouth() {
        return south;
    }

    public void setSouth(BigDecimal south) {
        this.south = south;
    }

    public BigDecimal getWest() {
        return west;
    }

    public void setWest(BigDecimal west) {
        this.west = west;
    }

    public BigDecimal getEast() {
        return east;
    }

    public void setEast(BigDecimal east) {
        this.east = east;
    }
}
