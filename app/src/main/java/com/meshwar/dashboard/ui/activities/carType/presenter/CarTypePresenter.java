package com.meshwar.dashboard.ui.activities.carType.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.Car;
import com.meshwar.dashboard.data.network.model.CarType;
import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.ui.activities.carType.view.CarTypeView;
import com.meshwar.dashboard.ui.base.BasePresenter;


public class CarTypePresenter extends BasePresenter {

    private final CarTypeView view;

    public CarTypePresenter(Context context, CarTypeView view) {
        super(context, view);
        this.view = view;
    }

    public void getCarTypes() {
        requestArray(true,mService.getCarTypes(getUserBearerToken()), "getCarTypes", CarType.class, (apiResponse, item) -> {
            view.getCarTypes(item);
        });
    }

    public void addCarType(String name) {
        requestObject(true,mService.addCarType(getUserBearerToken(),name), "addCarType",  (apiResponse, item) -> {
            getCarTypes();
        });
    }

    public void editCarType(String uuid, String name) {
        requestObject(true,mService.editCarType(getUserBearerToken(),uuid,name), "editCarType",  (apiResponse, item) -> {
            getCarTypes();
        });
    }
    public void deleteCarType(String uuid) {
        requestObject(true,mService.deleteCarType(getUserBearerToken(),uuid), "deleteCarType",  (apiResponse, item) -> {
            getCarTypes();
        });
    }
}
