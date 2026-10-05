package com.meshwar.dashboard.ui.activities.user.orderPlaced.presenter;

import android.content.Context;

import com.meshwar.dashboard.ui.activities.user.orderPlaced.view.OrderPlacedView;
import com.meshwar.dashboard.ui.base.BasePresenter;

public class OrderPlacedPresenter extends BasePresenter {
    OrderPlacedView view;

    Context context;

    public OrderPlacedPresenter(Context context, OrderPlacedView view) {
        super(context,view);
        this.view = view;
        this.context = context;
    }


}
