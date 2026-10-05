package com.meshwar.dashboard.ui.activities.login.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.User;
import com.meshwar.dashboard.ui.activities.login.view.LoginView;
import com.meshwar.dashboard.ui.base.BasePresenter;
import com.meshwar.dashboard.utils.AppSharedConstants;


public class LoginPresenter extends BasePresenter {

    private final LoginView view;

    public LoginPresenter(Context context, LoginView view) {
        super(context, view);
        this.view = view;
    }

    public void loginAdmin(String email, String password) {
        preference.getFcmToken((token, error) -> {
            requestObject(true, mService.loginAdmin( email,password, token , AppSharedConstants.DEVICE ), "loginAdmin", User.class,  (apiResponse, item) -> {
                preference.setUser(item);
                view.loginSuccessfully();
            });
        });
    }
}
