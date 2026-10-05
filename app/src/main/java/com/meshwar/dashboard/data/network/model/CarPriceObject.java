package com.meshwar.dashboard.data.network.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.meshwar.dashboard.ui.adapter.AdapterCarPrice;

import java.util.ArrayList;
import java.util.List;

public class CarPriceObject {

    @Expose
    @SerializedName("inside")
    private List<CarPriceSide> carPriceInSideList = new ArrayList<>();
    @Expose
    @SerializedName("outside")
    private List<CarPriceSide> carPriceOutSideList = new ArrayList<>();


    public CarPriceObject() {
        carPriceInSideList = new ArrayList<>();
        carPriceOutSideList = new ArrayList<>();
    }


    public List<String> getSelectedCarTypes(boolean inside) {
        List<String> carTypes = new ArrayList<>();

        if (inside) {
            for (CarPriceSide carPriceSide : carPriceInSideList)
                carTypes.add(carPriceSide.getCarTypeUuid());
        } else {
            for (CarPriceSide carPriceSide : carPriceOutSideList)
                carTypes.add(carPriceSide.getCarTypeUuid());
        }

        Log.e("Ascaa", carTypes.size() + " ") ;
        return carTypes;
    }

    public List<CarPriceSide> getCarPriceInSideList(Context context, boolean inside, AdapterCarPrice.PriceDeleted priceDeleted) {
        if (carPriceInSideList.isEmpty())
            carPriceInSideList.add(new CarPriceSide(context, inside, priceDeleted));

        return carPriceInSideList;
    }

    public List<CarPriceSide> getCarPriceInSideList() {
        return carPriceInSideList;
    }

    public void setCarPriceInSideList(List<CarPriceSide> carPriceInSideList) {
        this.carPriceInSideList = carPriceInSideList;
    }

    public List<CarPriceSide> getCarPriceOutSideList(Context context, boolean inside, AdapterCarPrice.PriceDeleted priceDeleted) {
        if (carPriceOutSideList.isEmpty())
            carPriceOutSideList.add(new CarPriceSide(context, inside, priceDeleted));

        return carPriceOutSideList;
    }

    public List<CarPriceSide> getCarPriceOutSideList() {
        return carPriceOutSideList;
    }

    public void setCarPriceOutSideList(List<CarPriceSide> carPriceOutSideList) {
        this.carPriceOutSideList = carPriceOutSideList;
    }
}
