package com.meshwar.dashboard.ui.activities.balance.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.AdminBalance;
import com.meshwar.dashboard.ui.activities.balance.view.BalanceView;
import com.meshwar.dashboard.ui.base.BasePresenter;


public class BalancePresenter extends BasePresenter {

    private final BalanceView view;

    public BalancePresenter(Context context, BalanceView view) {
        super(context, view);
        this.view = view;
    }

    public void getBalance() {
        requestObject(true, mService.getBalance(getUserBearerToken()), "getBalance", AdminBalance.class, (apiResponse, item) -> {
            view.initBalance(item);
        });
    }

}
