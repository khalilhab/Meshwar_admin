package com.meshwar.dashboard.ui.activities.user.orderPlaced.view;

import android.content.Intent;
import android.os.Bundle;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.databinding.ActivityOrderPlacedBinding;
import com.meshwar.dashboard.ui.activities.main.view.MainActivity;
import com.meshwar.dashboard.ui.activities.user.orderPlaced.presenter.OrderPlacedPresenter;
import com.meshwar.dashboard.ui.base.BaseActivity;

public class OrderPlacedActivity extends BaseActivity implements OrderPlacedView {

    OrderPlacedPresenter presenter;
    ActivityOrderPlacedBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderPlacedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        presenter = new OrderPlacedPresenter(context, this);

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

        binding.tvBackToMain.setOnClickListener(v -> {
            startActivity(new Intent(context, MainActivity.class) .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finishAffinity();
        });

    }

}
