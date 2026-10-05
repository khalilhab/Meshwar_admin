package com.meshwar.dashboard.ui.activities.profile.view;


import com.meshwar.dashboard.data.network.model.User;
import com.meshwar.dashboard.ui.base.BaseView;

public interface ProfileView extends BaseView {

    void initProfile(User user);

    void updateProfile();
}
