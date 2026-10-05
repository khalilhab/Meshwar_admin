package com.meshwar.dashboard.ui.activities.main.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.User;
import com.meshwar.dashboard.ui.activities.main.view.MainActivityView;
import com.meshwar.dashboard.ui.base.BasePresenter;


public class MainActivityPresenter extends BasePresenter {

    private final MainActivityView view;

    public MainActivityPresenter(Context context, MainActivityView view) {
        super(context, view);
        this.view = view;
    }

    public void logout() {
        preference.getFcmToken((token, error) -> {
            requestObject(true, mService.logoutAdmin(getUserBearerToken(),  token  ), "loginAdmin", User.class,  (apiResponse, item) -> {
                preference.setUser(null);
                view.logout();
            });
        });
    }
}
