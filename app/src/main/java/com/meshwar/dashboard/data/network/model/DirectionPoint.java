package com.meshwar.dashboard.data.network.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DirectionPoint {

    @Expose
    @SerializedName("lng")
    private String lng;
    @Expose
    @SerializedName("lat")
    private String lat;

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public LatLng toLatLng() {
        return new LatLng(Double.parseDouble(getLat()), Double.parseDouble(getLng()));
    }
}
