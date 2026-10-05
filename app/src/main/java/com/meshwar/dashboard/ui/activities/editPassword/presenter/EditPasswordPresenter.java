package com.meshwar.dashboard.ui.activities.editPassword.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.User;
import com.meshwar.dashboard.ui.activities.editPassword.view.EditPasswordView;
import com.meshwar.dashboard.ui.base.BasePresenter;

public class EditPasswordPresenter extends BasePresenter {
    EditPasswordView view;

    Context context;

    public EditPasswordPresenter(Context context, EditPasswordView view) {
        super(context,view);
        this.view = view;
        this.context = context;
    }

    public void changePassword(String oldPassword,String newPassword) {
        preference.getFcmToken((token, error) -> {
            requestObject(true, mService.changePassword(getUserBearerToken(), oldPassword,newPassword, newPassword), "changePassword", User.class,  (apiResponse, item) -> {
                preference.setUser(item);
                view.passwordChanged();
            });
        });
    }
}
