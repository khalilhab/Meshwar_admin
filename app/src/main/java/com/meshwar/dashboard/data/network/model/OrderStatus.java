package com.meshwar.dashboard.data.network.model;

public class OrderStatus {
    String status_txt;
    String status;// [1=>current , 2=>in_progress ,3=>completed]

    public OrderStatus(String status, String status_txt) {
        this.status = status;
        this.status_txt = status_txt;
    }

    public String getStatus_txt() {
        return status_txt;
    }

    public void setStatus_txt(String status_txt) {
        this.status_txt = status_txt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getStatus_txt();
    }
}
