package com.meshwar.dashboard.ui.activities.maxDistance.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.Setting;
import com.meshwar.dashboard.ui.activities.maxDistance.view.MaxDistanceView;
import com.meshwar.dashboard.ui.base.BasePresenter;

public class MaxDistancePresenter extends BasePresenter {

    MaxDistanceView view;
    Context context;

    public MaxDistancePresenter(Context context, MaxDistanceView view) {
        super(context, view);
        this.view = view;
        this.context = context;
    }

    public void getMaxDistance() {
        requestObject(true, mService.getMaxDistanceCars(getUserBearerToken()), "getMaxDistanceCars", Setting.class, (apiResponse, item) -> {
            if (item != null) {
                view.onMaxDistanceLoaded(item.getInsideMaxKm());
            }
        });
    }

    public void updateMaxDistance(String insideMaxKm) {
        requestObject(true, mService.updateMaxDistanceCars(getUserBearerToken(), insideMaxKm), "updateMaxDistanceCars", Setting.class, (apiResponse, item) -> {
            view.onMaxDistanceUpdated();
        });
    }
}
