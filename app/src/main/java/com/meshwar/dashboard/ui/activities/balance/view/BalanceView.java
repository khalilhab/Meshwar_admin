package com.meshwar.dashboard.ui.activities.balance.view;


import com.meshwar.dashboard.data.network.model.AdminBalance;
import com.meshwar.dashboard.ui.base.BaseView;

public interface BalanceView extends BaseView {
    void initBalance(AdminBalance item);
}