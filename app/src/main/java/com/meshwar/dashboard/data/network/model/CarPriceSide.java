package com.meshwar.dashboard.data.network.model;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.meshwar.dashboard.ui.adapter.AdapterCarPrice;

import java.util.ArrayList;
import java.util.List;

public class CarPriceSide {

    @Expose
    @SerializedName("city_to_uuid")
    private String cityToUuid;
    @Expose
    @SerializedName("city_from_uuid")
    private String cityFromUuid;
    @Expose
    @SerializedName("car_type_uuid")
    private String carTypeUuid;
    @Expose
    @SerializedName("prices")
    private List<CarPrice> carPrice;

    transient AdapterCarPrice adapterCarPrice;

    public CarPriceSide(Context context, boolean inside, AdapterCarPrice.PriceDeleted priceDeleted) {
        this.cityToUuid = "";
        this.cityFromUuid = "";
        this.carTypeUuid = "";
        this.carPrice = new ArrayList<>();

        CarPrice price = new CarPrice();
        if (context instanceof com.meshwar.dashboard.ui.activities.addCar.view.AddCarActivity) {
            price.setMaxDistance(((com.meshwar.dashboard.ui.activities.addCar.view.AddCarActivity) context).getGlobalMaxDistance());
        }
        price.setDistanceScale(inside ? "less_or_equal" : "bigger");
        this.carPrice.add(price);

        setAdapterCarPrice(context,inside,priceDeleted);
    }




    public int getFromCitySelectedPosition(List<City> cities) {
        for (City city:cities){
            if (!TextUtils.isEmpty(getCityFromUuid()) && getCityFromUuid().equals(city.getUuid())){
                return cities.indexOf(city);
            }
        }
        return 0;
    }
    public int getToCiTySelectedPosition(List<City> cities) {
        for (City city:cities){
            if (!TextUtils.isEmpty(getCityToUuid()) && getCityToUuid().equals(city.getUuid())){
                return cities.indexOf(city);
            }
        }
        return 0;
    }

    public int getCarTypeSelectedPosition(List<CarType> carTypes) {
        for (CarType carType:carTypes){
            if (!TextUtils.isEmpty(getCarTypeUuid()) && getCarTypeUuid().equals(carType.getUuid())){
                return carTypes.indexOf(carType);
            }
        }
        return 0;
    }


    public AdapterCarPrice getAdapterCarPrice() {
        return adapterCarPrice;
    }

    public void setAdapterCarPrice(Context context, boolean inside, AdapterCarPrice.PriceDeleted priceDeleted) {
        if (this.adapterCarPrice == null)
            this.adapterCarPrice = new AdapterCarPrice(context,getCarPrice(),inside,priceDeleted);
    }

    public List<CarPrice> getCarPrice() {
        if (carPrice.isEmpty()) {
            carPrice.add(new CarPrice());
        }

        return carPrice;
    }

    public void setCarPrice(List<CarPrice> carPrice) {
        this.carPrice = carPrice;
    }

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

    public String getCarTypeUuid() {
        return TextUtils.isEmpty(carTypeUuid)?"":carTypeUuid;
    }

    public void setCarTypeUuid(String carTypeUuid) {
        this.carTypeUuid = carTypeUuid;
    }

}
