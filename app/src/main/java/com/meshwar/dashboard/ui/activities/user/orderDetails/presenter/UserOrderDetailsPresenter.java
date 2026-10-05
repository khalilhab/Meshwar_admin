package com.meshwar.dashboard.ui.activities.user.orderDetails.presenter;

import android.content.Context;

import com.meshwar.dashboard.ui.activities.user.orderDetails.view.UserOrderDetailsView;
import com.meshwar.dashboard.ui.base.BasePresenter;


public class UserOrderDetailsPresenter extends BasePresenter {

    private final UserOrderDetailsView view;

    public UserOrderDetailsPresenter(Context context, UserOrderDetailsView view) {
        super(context, view);
        this.view = view;
    }

}
