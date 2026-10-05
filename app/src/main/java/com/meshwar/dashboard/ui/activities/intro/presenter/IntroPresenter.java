package com.meshwar.dashboard.ui.activities.intro.presenter;

import static com.meshwar.dashboard.utils.AppSharedConstants.SUCCESS_ALERT;

import android.content.Context;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.Intro;
import com.meshwar.dashboard.ui.activities.intro.view.IntroView;
import com.meshwar.dashboard.ui.base.BasePresenter;
import com.meshwar.dashboard.utils.AppSharedMethod;

public class IntroPresenter extends BasePresenter {
    IntroView view;

    Context context;

    public IntroPresenter(Context context, IntroView view) {
        super(context, view);
        this.view = view;
        this.context = context;
    }


    public void getIntroduction(String introductionType ) {
        requestArray(true, mService.getIntroductions(getUserBearerToken(), introductionType), "getIntroductions", Intro.class, (apiResponse, item) -> {
            view.InitIntroductions(item);
        });


    }

    public void addIntroduction(String introductionType, Intro intro) {
        requestArray(true, mService.addIntroduction(getUserBearerToken(),
                AppSharedMethod.getPart(introductionType),
                AppSharedMethod.getPart(intro.getDescription())
                , AppSharedMethod.getPart(context, intro.getNewImageUri(), "image")), "addIntroduction", Intro.class, (apiResponse, item) -> {
            view.showMessage(context.getString(R.string.added_successfully), SUCCESS_ALERT);
            getIntroduction(introductionType);
        });
    }

    public void editIntroduction(String introductionType, Intro intro ) {
        requestObject(true, mService.editIntroduction(getUserBearerToken(),
                AppSharedMethod.getPart(intro.getUuid()),
                AppSharedMethod.getPart(intro.getDescription())
                , intro.getNewImageUri() == null ? null : AppSharedMethod.getPart(context, intro.getNewImageUri(), "image")), "editIntroduction", Intro.class, (apiResponse, item) -> {
            view.showMessage(context.getString(R.string.edited_successfully), SUCCESS_ALERT);
            getIntroduction(introductionType);
        });
    }

    public void deleteIntroduction(String introductionType, String uuid) {
        requestArray(true, mService.deleteIntroduction(getUserBearerToken(), uuid), "deleteIntroduction", Intro.class, (apiResponse, item) -> {
            view.showMessage(context.getString(R.string.deleted_successfully), SUCCESS_ALERT);
            getIntroduction(introductionType);
        });
    }
}
