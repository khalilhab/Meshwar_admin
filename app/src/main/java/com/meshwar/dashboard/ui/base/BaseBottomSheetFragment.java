package com.meshwar.dashboard.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.meshwar.dashboard.data.prefs.AppPreferencesHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public abstract class BaseBottomSheetFragment extends BottomSheetDialogFragment implements BaseView {

    public abstract void initView(View mView);

    public abstract void initData();

    public abstract void initListener();

    public BaseActivity baseActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = (BaseActivity) getActivity();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        baseActivity = (BaseActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        baseActivity = (BaseActivity) getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void showProgress() {
        baseActivity.showProgress();
    }

    @Override
    public void showProgressTransparent() {
        baseActivity.showProgressTransparent();
    }

    @Override
    public void hideProgressTransparent() {
        baseActivity.hideProgressTransparent();
    }

    @Override
    public void hideProgress() {
        baseActivity.hideProgress();
    }

    @Override
    public void showMessage(int resId,int alertType) {
        baseActivity.showMessage(resId,alertType);
    }

    @Override
    public void showMessage(String message,int alertType) {
        baseActivity.showMessage(message,alertType);
    }

    public AppPreferencesHelper getPreference() {
        return baseActivity.preference;
    }

}
