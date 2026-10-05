package com.meshwar.dashboard.data.network.model;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarPrice {

    @Expose
    @SerializedName("city_to_uuid")
    private String cityToUuid;
    @Expose
    @SerializedName("city_from_uuid")
    private String cityFromUuid;
    @Expose
    @SerializedName("commission")
    private String commission = "";
    @Expose
    @SerializedName("price")
    private String price = "";
    @Expose
    @SerializedName("name")
    private String name = "";
    @Expose
    @SerializedName("place_type")
    private String placeType;  //"inside" or "outside"
    @Expose
    @SerializedName("car_type_uuid")
    private String carTypeUuid;
    @Expose
    @SerializedName("car_uuid")
    private String carUuid;
    @Expose
    @SerializedName("uuid")
    private String uuid ="";
    
    @Expose
    @SerializedName("id")
    private int id;
    

    @Expose
    @SerializedName("distance_scale")
    private String distanceScale = "less_or_equal";
    
    @Expose
    @SerializedName("max_distance")
    private String maxDistance = "";


    public String getCityToUuid() {
        return cityToUuid;
    }

    public void setCityToUuid(String cityToUuid) {
        this.cityToUuid = cityToUuid;
    }

    public String getCityFromUuid() {
        return cityFromUuid;
    }

    public void setCityFromUuid(String cityFromUuid) {
        this.cityFromUuid = cityFromUuid;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getCarTypeUuid() {
        return carTypeUuid;
    }

    public void setCarTypeUuid(String carTypeUuid) {
        this.carTypeUuid = carTypeUuid;
    }

    public String getCarUuid() {
        return carUuid;
    }

    public void setCarUuid(String carUuid) {
        this.carUuid = carUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getDistanceScale() {
        return distanceScale;
    }

    public void setDistanceScale(String distanceScale) {
        this.distanceScale = distanceScale;
    }

    public String getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(String maxDistance) {
        this.maxDistance = maxDistance;
    }

    public String getJson() {
        com.google.gson.JsonObject jsonObject = new com.google.gson.JsonObject();
        jsonObject.addProperty("uuid", uuid == null ? "" : uuid);
        jsonObject.addProperty("name", name == null ? "" : name);
        jsonObject.addProperty("price", price == null ? "" : price);
        jsonObject.addProperty("commission", commission == null ? "" : commission);
        if (carTypeUuid != null) {
            jsonObject.addProperty("car_type_uuid", carTypeUuid);
        }
        jsonObject.addProperty("distance_scale", distanceScale == null ? "" : distanceScale);
        jsonObject.addProperty("max_distance", maxDistance == null ? "" : maxDistance);
        
        if (cityFromUuid != null) {
            jsonObject.addProperty("city_from_uuid", cityFromUuid);
        }
        if (cityToUuid != null) {
            jsonObject.addProperty("city_to_uuid", cityToUuid);
        }

        return jsonObject.toString();
    }
}
