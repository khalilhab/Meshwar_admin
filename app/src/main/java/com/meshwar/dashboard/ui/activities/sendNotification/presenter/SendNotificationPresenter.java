package com.meshwar.dashboard.ui.activities.sendNotification.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.User;
import com.meshwar.dashboard.ui.activities.sendNotification.view.SendNotificationView;
import com.meshwar.dashboard.ui.base.BasePresenter;
import com.meshwar.dashboard.utils.AppSharedConstants;


public class SendNotificationPresenter extends BasePresenter {

    private final SendNotificationView view;

    public SendNotificationPresenter(Context context, SendNotificationView view) {
        super(context, view);
        this.view = view;
    }

    public void sendNotification(String uuid, String message) {
            requestArray(true, mService.driverSendNotification(getUserBearerToken(), uuid,message ), "driverSendNotification", User.class,  (apiResponse, item) -> {
                view.notificationSent();
            });
    }
}
