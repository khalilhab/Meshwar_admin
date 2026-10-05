package com.meshwar.dashboard.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Setting {

    @Expose
    @SerializedName("inside_max_km")
    private String insideMaxKm;

    public String getInsideMaxKm() {
        return insideMaxKm;
    }

    public void setInsideMaxKm(String insideMaxKm) {
        this.insideMaxKm = insideMaxKm;
    }
}
