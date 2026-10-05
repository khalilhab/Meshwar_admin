package com.meshwar.dashboard.ui.activities.profile.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.User;
import com.meshwar.dashboard.ui.activities.profile.view.ProfileView;
import com.meshwar.dashboard.ui.base.BasePresenter;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class ProfilePresenter extends BasePresenter {

    private final ProfileView view;

    public ProfilePresenter(Context context, ProfileView view) {
        super(context, view);
        this.view = view;
    }

    public void getProfile() {
        requestObject(true,mService.getProfile(getUserBearerToken()), "getProfile", User.class, (apiResponse, item) -> {
            preference.setUser(item);
            view.initProfile(item);
        }, apiResponse -> logout(), apiResponse -> logout(), apiResponse -> view.initProfile(preference.getUser()));
    }

    private void logout() {
        preference.setUser(null);
        preference.setAllowNotifications(true);
        view.initProfile(null);
    }


    public void updateProfile(RequestBody name, RequestBody email,RequestBody mobile,  MultipartBody.Part imgImage) {
        requestObject(true,mService.editProfile(getUserBearerToken(), name, email, mobile, imgImage), "editProfile", User.class, (apiResponse, item) -> {
            preference.setUser(item);
            view.updateProfile();
        });
    }

}
