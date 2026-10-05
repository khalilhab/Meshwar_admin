package com.meshwar.dashboard.ui.base;

public interface BaseView {
    void showProgress();

    void showProgressTransparent();

    void hideProgress();

    void hideProgressTransparent();

    void showMessage(int resId,int alertType);

    void showMessage(String message,int alertType);
}
