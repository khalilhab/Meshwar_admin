package com.meshwar.dashboard.ui.activities.orders.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.Order;
import com.meshwar.dashboard.ui.base.BasePresenter;
import com.meshwar.dashboard.ui.activities.orders.view.OrdersView;


public class OrdersPresenter extends BasePresenter {

    private final OrdersView view;

    public OrdersPresenter(Context context, OrdersView view) {
        super(context, view);
        this.view = view;
    }


    public void getOrders(String status) {
        requestArray(true, mService.getOrders(getUserBearerToken(),status), "getMyOrders", Order.class, (apiResponse, item) -> {
            view.initOrders(item);
        });
    }

    public void paidOrder(String uuid) {
        requestArray(true, mService.orderPayment(getUserBearerToken(),uuid), "orderPayment", Order.class, (apiResponse, item) -> {
            getOrders("3");
        });
    }
}
