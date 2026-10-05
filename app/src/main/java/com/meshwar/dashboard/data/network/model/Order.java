package com.meshwar.dashboard.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {

    @Expose
    @SerializedName("points")
    private List<DirectionPoint> points;
    @Expose
    @SerializedName("user")
    private User user;
    @Expose
    @SerializedName("is_paid")
    private boolean is_paid;
    @Expose
    @SerializedName("driver")
    private Driver driver;
    @Expose
    @SerializedName("lng_to")
    private String lng_to;
    @Expose
    @SerializedName("lat_to")
    private String lat_to;
    @Expose
    @SerializedName("lng_from")
    private String lng_from;
    @Expose
    @SerializedName("lat_from")
    private String lat_from;
    @Expose
    @SerializedName("driver_mobile")
    private String driver_mobile;
    @Expose
    @SerializedName("return_url")
    private String return_url;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("time")
    private String time;
    @Expose
    @SerializedName("car_price_name")
    private String car_price_name;
    @Expose
    @SerializedName("place_type_name")
    private String place_type_name;
    @Expose
    @SerializedName("location_from")
    private String location_from;
    @Expose
    @SerializedName("location_to")
    private String location_to;
    @Expose
    @SerializedName("add_time")
    private String addTime;
    @Expose
    @SerializedName("status_name")
    private String statusName;
    @Expose
    @SerializedName("order_num")
    private String orderNum;
    @Expose
    @SerializedName("status")
    private String status;//[1=>current , 2=>in_progress ,3=>completed]
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("driver_profit")
    private String driver_profit;
    @Expose
    @SerializedName("commission")
    private String commission;
    @Expose
    @SerializedName("price")
    private String price;
    @Expose
    @SerializedName("uuid")
    private String uuid;

    public String getDriver_profit() {
        return driver_profit;
    }

    public void setDriver_profit(String driver_profit) {
        this.driver_profit = driver_profit;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public boolean isPaid() {
        return is_paid;
    }

    public void setIs_paid(boolean is_paid) {
        this.is_paid = is_paid;
    }

    public List<DirectionPoint> getPoints() {
        return points;
    }

    public void setPoints(List<DirectionPoint> points) {
        this.points = points;
    }

    public String getLng_to() {
        return lng_to;
    }

    public void setLng_to(String lng_to) {
        this.lng_to = lng_to;
    }

    public String getLat_to() {
        return lat_to;
    }

    public void setLat_to(String lat_to) {
        this.lat_to = lat_to;
    }

    public String getLng_from() {
        return lng_from;
    }

    public void setLng_from(String lng_from) {
        this.lng_from = lng_from;
    }

    public String getLat_from() {
        return lat_from;
    }

    public void setLat_from(String lat_from) {
        this.lat_from = lat_from;
    }

    public String getCar_price_name() {
        return car_price_name;
    }

    public void setCar_price_name(String car_price_name) {
        this.car_price_name = car_price_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace_type_name() {
        return place_type_name;
    }

    public void setPlace_type_name(String place_type_name) {
        this.place_type_name = place_type_name;
    }

    public String getLocation_from() {
        return location_from;
    }

    public void setLocation_from(String location_from) {
        this.location_from = location_from;
    }

    public String getLocation_to() {
        return location_to;
    }

    public void setLocation_to(String location_to) {
        this.location_to = location_to;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getDriver_mobile() {
        return driver_mobile;
    }

    public void setDriver_mobile(String driver_mobile) {
        this.driver_mobile = driver_mobile;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
