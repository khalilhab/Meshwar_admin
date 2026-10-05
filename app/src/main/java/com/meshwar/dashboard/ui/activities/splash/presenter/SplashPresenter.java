package com.meshwar.dashboard.ui.activities.splash.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.User;
import com.meshwar.dashboard.ui.activities.splash.view.SplashView;
import com.meshwar.dashboard.ui.base.BasePresenter;


public class SplashPresenter extends BasePresenter {

    private final SplashView view;

    public SplashPresenter(Context context, SplashView view) {
        super(context, view);
        this.view = view;
    }

    public void checkAuth() {
        requestObject(false,mService.getProfile(getUserBearerToken()), "getProfile", User.class, (apiResponse, item) -> {
            preference.setUser(item);
            view.login(item);
        }, apiResponse -> logout(), apiResponse -> logout(), apiResponse -> view.login(preference.getUser()));
    }

    private void logout() {
        preference.setUser(null);
        preference.setAllowNotifications(true);
        view.login(null);
    }

}
