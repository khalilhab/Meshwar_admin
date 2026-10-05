package com.meshwar.dashboard.ui.activities.addCar.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.meshwar.dashboard.data.network.model.Car;
import com.meshwar.dashboard.data.network.model.CarPrice;
import com.meshwar.dashboard.data.network.model.CarType;
import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.ui.activities.addCar.view.AddCarView;
import com.meshwar.dashboard.ui.base.BasePresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;


import com.meshwar.dashboard.data.network.model.Setting;

public class AddCarPresenter extends BasePresenter {

    private final AddCarView view;

    public AddCarPresenter(Context context, AddCarView view) {
        super(context, view);
        this.view = view;
    }

    public void getMaxDistanceCars() {
        requestObject(true, mService.getMaxDistanceCars(getUserBearerToken()), "getMaxDistanceCars", Setting.class, (apiResponse, item) -> {
            view.onMaxDistanceLoaded(item.getInsideMaxKm());
        });
    }


    public void getCarTypes() {
        requestArray(true, mService.getCarTypes(getUserBearerToken()), "getCarTypes", CarType.class, (apiResponse, item) -> {
            view.initCarTypes(item);

            getCities();
        });
    }


    public void getCarDetails(String driverUuid) {
        requestObject(true, mService.getCarDetails(getUserBearerToken(), driverUuid), "getCarDetails", Car.class, (apiResponse, item) -> {
            view.initCarDetails(item);
        });
    }


    public void getCities() {
        requestArray(true, mService.getCities(getUserBearerToken()), "getCars", City.class, (apiResponse, item) -> {
            view.getCities(item);
        });
    }

    public void addCar(MultipartBody.Part image, MultipartBody.Part carName, List<MultipartBody.Part> carPricesInSideList, List<MultipartBody.Part> carPricesOutSideList) {
        requestObject(true, mService.addCar(getUserBearerToken(), carName, carPricesInSideList, carPricesOutSideList, image), "addCar", (apiResponse, item) -> {
            view.carAdded();
        });
    }

    public void editCar(MultipartBody.Part uuid, MultipartBody.Part carName, String isActive, MultipartBody.Part image, List<MultipartBody.Part> carPricesInSideList, List<MultipartBody.Part> carPricesOutSideList, List<MultipartBody.Part> inside_cars_deleted, List<MultipartBody.Part> outside_cars_deleted) {
        requestObject(true, mService.editCar(getUserBearerToken(), uuid, carName, isActive, carPricesInSideList, carPricesOutSideList, inside_cars_deleted, outside_cars_deleted, image), "addCar", (apiResponse, item) -> {
            view.carEdited();
        });
    }

    public void deleteCar(String carUuid) {
        requestObject(true, mService.deleteCar(getUserBearerToken(), carUuid), "deleteCar", (apiResponse, item) -> {
            view.carDeleted();
        });
    }
}
