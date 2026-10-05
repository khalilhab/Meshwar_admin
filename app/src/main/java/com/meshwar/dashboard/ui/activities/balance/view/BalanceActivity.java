package com.meshwar.dashboard.ui.activities.balance.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.AdminBalance;
import com.meshwar.dashboard.data.network.model.Balance;
import com.meshwar.dashboard.databinding.ActivityBalanceBinding;
import com.meshwar.dashboard.databinding.DialogOrderBalanceBinding;
import com.meshwar.dashboard.ui.adapter.AdapterBalance;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.ui.activities.balance.presenter.BalancePresenter;

import java.util.ArrayList;
import java.util.List;

public class BalanceActivity extends BaseActivity implements BalanceView, AdapterBalance.onItemClicked {

    private ActivityBalanceBinding binding;
    BalancePresenter presenter;
    AdapterBalance adapterBalance;


    List<Balance> balances = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBalanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new BalancePresenter(this, this);

        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {
        binding.rcOrderBalances.setAdapter(adapterBalance = new AdapterBalance(context, balances,this));
    }


    @Override
    public void initData() {

        presenter.getBalance();

    }


    @Override
    public void initListener() {

        binding.imvBack.setOnClickListener(v -> onBackPressed());
    }


    AdminBalance driverBalance;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void initBalance(AdminBalance driverBalance) {
        this.driverBalance=driverBalance;

        this.balances.clear();
        this.balances.addAll(driverBalance.getBalances());
        adapterBalance.notifyDataSetChanged();

        binding.tvTotal.setText(String.format("%s %s" , driverBalance.getTotal(),getString(R.string.riyal)));
    }

    @Override
    public void onItemClicked(int position) {
        showOrderBill(driverBalance.getBalances().get(position));
    }


    DialogOrderBalanceBinding dialogOrderBalanceBinding;
    Dialog orderBalanceDialog;

    private void showOrderBill(Balance balance) {

        if (orderBalanceDialog != null && orderBalanceDialog.isShowing())
            orderBalanceDialog.dismiss();

        orderBalanceDialog = new Dialog(context, R.style.StyleDialog);
        dialogOrderBalanceBinding = DialogOrderBalanceBinding.inflate(getLayoutInflater());
        orderBalanceDialog.setContentView(dialogOrderBalanceBinding.getRoot());
        orderBalanceDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        orderBalanceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        orderBalanceDialog.setCancelable(true);
        orderBalanceDialog.show();

        dialogOrderBalanceBinding.tvOrderId.setText(String.format("%s %s" ,getString(R.string.order_balance), balance.getOrderNum()));

        dialogOrderBalanceBinding.tvOrderBalance.setText(String.format("%s %s" , balance.getPrice(),getString(R.string.riyal)));
        dialogOrderBalanceBinding.tvAppCommission.setText(String.format("%s %s" , balance.getCommission(),getString(R.string.riyal)));
        dialogOrderBalanceBinding.tvRemaining.setText(String.format("%s %s" , balance.getRemain(),getString(R.string.riyal)));

    }

}