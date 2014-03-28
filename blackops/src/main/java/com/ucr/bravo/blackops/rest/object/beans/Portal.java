package com.ucr.bravo.blackops.rest.object.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

/**
 * Created by cedric on 3/5/14.
 */
public class Portal implements Parcelable
{
    private String id;
    private String name;
    private String url;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public Portal()
    {
    }

    public Portal(Parcel in) {
        id       = in.readString();
        name     = in.readString();
        url      = in.readString();
        latitude = new BigDecimal(in.readString());
        longitude = new BigDecimal(in.readString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i)
    {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(latitude.toString());
        dest.writeString(longitude.toString());

    }
}
