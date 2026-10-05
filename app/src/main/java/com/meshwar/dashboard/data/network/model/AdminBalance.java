package com.meshwar.dashboard.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminBalance {

    @Expose
    @SerializedName("total")
    private String total;
    @Expose
    @SerializedName("orders")
    private List<Balance> balances;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }
}
