package com.meshwar.dashboard.ui.activities.user.orderDetails.view;

import android.os.Bundle;

import com.meshwar.dashboard.databinding.ActivityUserOrderDetailsBinding;
import com.meshwar.dashboard.ui.activities.user.orderDetails.presenter.UserOrderDetailsPresenter;
import com.meshwar.dashboard.ui.base.BaseActivity;


public class UserOrderDetailsActivity extends BaseActivity implements UserOrderDetailsView {

    private ActivityUserOrderDetailsBinding binding;

    private UserOrderDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        presenter = new UserOrderDetailsPresenter(this, this);

        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

}