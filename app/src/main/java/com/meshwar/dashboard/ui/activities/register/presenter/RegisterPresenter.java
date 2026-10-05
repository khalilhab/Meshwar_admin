package com.meshwar.dashboard.ui.activities.register.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.User;
import com.meshwar.dashboard.ui.activities.register.view.RegisterView;
import com.meshwar.dashboard.ui.base.BasePresenter;
import com.meshwar.dashboard.utils.AppSharedConstants;


public class RegisterPresenter extends BasePresenter {

    private final RegisterView view;

    public RegisterPresenter(Context context, RegisterView view) {
        super(context, view);
        this.view = view;
    }

    public void registerAdmin(String name, String email, String phoneNumber, String password) {
        preference.getFcmToken((token, error) -> {
            requestObject(true, mService.registerAdmin(name, email,phoneNumber,password, token , AppSharedConstants.DEVICE ), "register", User.class, (apiResponse, item) -> {
                preference.setUser(item);
                view.registeredSuccessfully();
            });
        });
    }
}
