package com.meshwar.dashboard.ui.activities.orderDetails.view;


import com.meshwar.dashboard.data.network.model.Order;
import com.meshwar.dashboard.ui.base.BaseView;

public interface DriverOrderDetailsView extends BaseView {

    void initOrderDetails(Order item);
}
