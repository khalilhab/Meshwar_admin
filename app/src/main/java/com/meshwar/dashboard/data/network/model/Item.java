package com.meshwar.dashboard.data.network.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private String name;
    private String uuid;

    public Item(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
