package com.meshwar.dashboard.ui.activities.noInternet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.meshwar.dashboard.ui.activities.splash.view.SplashActivity;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.ui.base.BaseView;
import com.meshwar.dashboard.utils.AppSharedMethod;
import com.meshwar.dashboard.R;
import com.meshwar.dashboard.databinding.ActivityNoInternetBinding;


public class NoInternetActivity extends BaseActivity implements BaseView {

    private ActivityNoInternetBinding binding;

    @SuppressLint("StaticFieldLeak")
    public static NoInternetActivity instance;

    public static NoInternetActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoInternetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (NoInternetActivity.getInstance() != null)
            NoInternetActivity.getInstance().finish();

        instance = this;


        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {
        initToolbarBack(getString(R.string.no_internet_connection), true);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.btnTryAgain.setOnClickListener(v -> tryAgain());
    }

    public void tryAgain() {
        runOnUiThread(() -> {
            showProgress();
            new Handler(Looper.myLooper()).postDelayed(() -> {
                if (AppSharedMethod.isConnectedToInternet(context)) {
                    startActivity(new Intent(context, SplashActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finishAffinity();
                } else {
                    hideProgress();
                }
            }, 2000);
        });
    }

    @Override
    protected void onDestroy() {
        instance = null;
        super.onDestroy();
    }
}