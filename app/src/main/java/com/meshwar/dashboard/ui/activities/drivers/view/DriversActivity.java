package com.meshwar.dashboard.ui.activities.drivers.view;

import static com.meshwar.dashboard.utils.AppSharedConstants.DRIVER_ID;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.Driver;
import com.meshwar.dashboard.databinding.ActivityDriversBinding;
import com.meshwar.dashboard.ui.adapter.AdapterDriver;
import com.meshwar.dashboard.ui.activities.driverDetails.view.DriverDetailsActivity;
import com.meshwar.dashboard.ui.activities.drivers.presenter.DriversPresenter;
import com.meshwar.dashboard.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class DriversActivity extends BaseActivity implements DriversView, AdapterDriver.onItemClicked {

    AdapterDriver adapterDriver;
    DriversPresenter presenter;
    ActivityDriversBinding binding;

    List<Driver> drivers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriversBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        presenter = new DriversPresenter(context, this);

        initView();
        initData();
        initListener();
    }


    @Override
    public void initView() {

        binding.rcDrivers.setAdapter(adapterDriver = new AdapterDriver(context,drivers,this));


    }

    @Override
    public void initData() {
        presenter.getDrivers(false,null);
    }

    @Override
    public void initListener() {
        binding.imvBack.setOnClickListener(v -> onBackPressed());

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            private Timer timer = new Timer();
            private final long DELAY = 1000; // Milliseconds

            @Override
            public void afterTextChanged(Editable s) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                presenter.getDrivers(true,Objects.requireNonNull(binding.search.getText()).toString());
                            }
                        },
                        DELAY
                );
            }
        });

    }

    @Override
    public void onItemClicked(int position) {
        startActivity(new Intent(context, DriverDetailsActivity.class).putExtra(DRIVER_ID,drivers.get(position).getUuid()));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void initDrivers(List<Driver> drivers) {
        this.drivers.clear();
        this.drivers.addAll(drivers);
        adapterDriver.notifyDataSetChanged();
    }
}
