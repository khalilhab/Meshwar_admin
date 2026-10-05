package com.meshwar.dashboard.ui.activities.drivers.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.meshwar.dashboard.data.network.model.Driver;
import com.meshwar.dashboard.ui.activities.drivers.view.DriversView;
import com.meshwar.dashboard.ui.base.BasePresenter;

public class DriversPresenter extends BasePresenter {
    DriversView view;

    Context context;

    public DriversPresenter(Context context, DriversView view) {
        super(context,view);
        this.view = view;
        this.context = context;
    }


    public void getDrivers(boolean useProgress , String name) {
        requestArray(useProgress,mService.getDrivers(getUserBearerToken(),name), "getDrivers", Driver.class, (apiResponse, item) -> {
            view.initDrivers(item);
        });
    }

}
