package com.meshwar.dashboard.ui.activities.orders.view;


import com.meshwar.dashboard.data.network.model.Order;
import com.meshwar.dashboard.ui.base.BaseView;

import java.util.List;

public interface OrdersView extends BaseView {
    void initOrders(List<Order> item);
}