package com.meshwar.dashboard.ui.activities.driverDetails.presenter;

import static com.meshwar.dashboard.utils.AppSharedConstants.SUCCESS_ALERT;

import android.content.Context;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.data.network.model.Driver;
import com.meshwar.dashboard.ui.activities.driverDetails.view.DriverDetailsView;
import com.meshwar.dashboard.ui.base.BasePresenter;



public class DriverDetailsPresenter extends BasePresenter {

    private final DriverDetailsView view;

    public DriverDetailsPresenter(Context context, DriverDetailsView view) {
        super(context, view);
        this.view = view;
    }

    public void getDriverDetails(String driverUuid) {
        requestObject(true,mService.getDriverDetails(getUserBearerToken(),driverUuid), "getDriverDetails", Driver.class, (apiResponse, item) -> {
            view.initDriver(item);
        });
    }

    public void getCities() {
        requestArray(true, mService.getCities(getUserBearerToken()), "getCars", City.class, (apiResponse, item) -> {
            view.getCities(item);
        });
    }


    public void acceptDriver(String driverUuid) {
        requestArray(true,mService.acceptDriver(getUserBearerToken(),driverUuid), "acceptDriver", Driver.class, (apiResponse, item) -> {
            view.showMessage(context.getString(R.string.accepted),SUCCESS_ALERT);
            view.driverAccepted();
        });
    }
}
