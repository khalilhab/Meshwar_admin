package com.meshwar.dashboard.data.network.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarType {

    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("uuid")
    private String uuid;

    public CarType(String uuid,String name) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
