package com.meshwar.dashboard.ui.activities.orderDetails.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.Order;
import com.meshwar.dashboard.ui.activities.orderDetails.view.DriverOrderDetailsView;
import com.meshwar.dashboard.ui.base.BasePresenter;


public class DiverOrderDetailsPresenter extends BasePresenter {

    private final DriverOrderDetailsView view;

    public DiverOrderDetailsPresenter(Context context, DriverOrderDetailsView view) {
        super(context, view);
        this.view = view;
    }

    public void getOrderDetails(String orderUuid) {
        requestObject(true,mService.getOrderDetails(getUserBearerToken(),orderUuid), "getOrderDetails", Order.class, (apiResponse, item) -> {
            view.initOrderDetails(item);
        });
    }
}
