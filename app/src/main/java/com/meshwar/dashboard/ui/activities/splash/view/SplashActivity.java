package com.meshwar.dashboard.ui.activities.splash.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.meshwar.dashboard.data.network.model.User;
import com.meshwar.dashboard.ui.activities.splash.presenter.SplashPresenter;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.databinding.ActivitySplashBinding;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity implements SplashView {

    private ActivitySplashBinding binding;

    private SplashPresenter presenter;

    private boolean readyCheckUser = false;
    private boolean readyTimer = false;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new SplashPresenter(this, this);

        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        if (preference.isFirst() || !preference.isLogin()) {
            preference.setIsFirst();
            readyCheckUser = true;
        } else {
            presenter.checkAuth();
        }
    }

    @Override
    public void initListener() {
        new Handler(Looper.myLooper()).postDelayed(() -> {
            readyTimer = true;
            next();
        }, 1000);
    }


    @Override
    public void showMessage(int resId,int alertType) {

    }

    @Override
    public void showMessage(String message,int alertType) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void login(User user) {
        readyCheckUser = true;
        this.user = user;
        next();
    }

    private void next() {
        if (!readyCheckUser || !readyTimer) {
            return;
        }

        checkUserState(user);
    }

}