package com.meshwar.dashboard.ui.activities.cities.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.ui.activities.cities.view.CitiesView;
import com.meshwar.dashboard.ui.base.BasePresenter;


public class CitiesPresenter extends BasePresenter {

    private final CitiesView view;

    public CitiesPresenter(Context context, CitiesView view) {
        super(context, view);
        this.view = view;
    }

    public void getCities() {
        requestArray(true,mService.getCities(getUserBearerToken()), "getCars", City.class, (apiResponse, item) -> {
            view.getCities(item);
        });
    }
    public void addCity(String name) {
        requestObject(true,mService.addCity(getUserBearerToken(),name), "addCity",  (apiResponse, item) -> {
            getCities();
        });
    }

    public void editCity(String uuid, String name) {
        requestObject(true,mService.editCity(getUserBearerToken(),uuid,name), "editCity",  (apiResponse, item) -> {
            getCities();
        });
    }
    public void deleteCity(String uuid) {
        requestObject(true,mService.deleteCity(getUserBearerToken(),uuid), "deleteCity",  (apiResponse, item) -> {
            getCities();
        });
    }
}
