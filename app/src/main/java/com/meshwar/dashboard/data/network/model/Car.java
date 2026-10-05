package com.meshwar.dashboard.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Car {


    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("uuid")
    private String uuid;
    @Expose
    @SerializedName("is_active")
    private boolean isActive = true;
    @Expose
    @SerializedName("carPrices")
    private CarPriceObject carPriceSide=new CarPriceObject();

    public Car() {
        this.image = "";
        this.name = "";
        this.uuid = "";
        this.isActive = true;
        this.carPriceSide = new CarPriceObject();
    }

    public CarPriceObject getCarPriceSide() {
        return carPriceSide;
    }

    public void setCarPriceSide(CarPriceObject carPriceSide) {
        this.carPriceSide = carPriceSide;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
