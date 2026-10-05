package com.meshwar.dashboard.ui.activities.orderDetails.view;

import static com.meshwar.dashboard.utils.AppSharedConstants.ORDER_ID;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.Order;
import com.meshwar.dashboard.databinding.ActivityDriverOrderDetailsBinding;
import com.meshwar.dashboard.ui.activities.orderDetails.presenter.DiverOrderDetailsPresenter;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.utils.AppSharedMethod;


public class DriverOrderDetailsActivity extends BaseActivity implements DriverOrderDetailsView {

    private ActivityDriverOrderDetailsBinding binding;
    private DiverOrderDetailsPresenter presenter;

    String orderId="";
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new DiverOrderDetailsPresenter(this, this);

        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            if (getIntent().hasExtra(ORDER_ID))
                orderId = getIntent().getStringExtra(ORDER_ID);
        }
    }

    @Override
    public void initData() {
        presenter.getOrderDetails(orderId);
    }

    @Override
    public void initListener() {
        binding.imvBack.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void initOrderDetails(Order item) {
        this.order=item;


        binding.tvOrderNumber.setText(order.getOrderNum());
        binding.tvSinceTime.setText(order.getAddTime());
        Glide.with(context).load(order.getUser().getImage()).centerCrop().placeholder(R.drawable.logo).into(binding.imvUser);
        binding.tvUserName.setText(order.getUser().getName());
        binding.tvUserPhone.setText(order.getUser().getMobile());
        binding.tvCarName.setText(order.getDriver().getCarName());
        binding.tvServicePlace.setText(order.getPlace_type_name());
        binding.tvPriceName.setText(order.getCar_price_name());
        binding.tvTime.setText(order.getTime());
        binding.tvDate.setText(order.getDate());
        binding.tvOrderPrice.setText(String.format("%s %s",order.getPrice(),context.getString(R.string.riyal)));
        binding.tvOrderDescription.setText(order.getDescription());
        binding.tvFromLocation.setText(order.getLocation_from());
        binding.tvToLocation.setText(order.getLocation_to());
        binding.tvTotalPrice.setText(String.format("%s : %s %s",context.getString(R.string.total),order.getPrice(),context.getString(R.string.riyal)));
        binding.tvCommission.setText(String.format("%s : %s %s",context.getString(R.string.commission),order.getCommission(),context.getString(R.string.riyal)));
        binding.tvDriverProfit.setText(String.format("%s : %s %s",context.getString(R.string.driver_fare),order.getDriver_profit(),context.getString(R.string.riyal)));


        binding.linearOrderDescription.setVisibility(TextUtils.isEmpty(order.getDescription())?View.GONE:View.VISIBLE);
        binding.view6.setVisibility(TextUtils.isEmpty(order.getDescription())?View.GONE:View.VISIBLE);


        binding.tvUserPhone.setOnClickListener(v -> AppSharedMethod.call(context,order.getUser().getMobile()));
        binding.tvUserCall.setOnClickListener(v -> AppSharedMethod.call(context,order.getUser().getMobile()));

        binding.tvFromLocation.setOnClickListener(v -> AppSharedMethod.dir(context,order.getLat_from(),order.getLng_from()));
        binding.tvToLocation.setOnClickListener(v -> AppSharedMethod.dir(context,order.getLat_to(),order.getLng_to()));

    }
}