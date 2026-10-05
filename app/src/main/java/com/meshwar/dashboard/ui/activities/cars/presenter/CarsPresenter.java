package com.meshwar.dashboard.ui.activities.cars.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.Car;
import com.meshwar.dashboard.ui.activities.cars.view.CarsView;
import com.meshwar.dashboard.ui.base.BasePresenter;


public class CarsPresenter extends BasePresenter {

    private final CarsView view;

    public CarsPresenter(Context context, CarsView view) {
        super(context, view);
        this.view = view;
    }


    public void getCars(int page) {
        requestArray(page==1,mService.getCars(getUserBearerToken(),"0",page), "getCars", Car.class, (apiResponse, item) -> {
            view.initCars(item,apiResponse.getPages().getCurrentPage(),apiResponse.getPages().getLastPage());
        });
    }
}
