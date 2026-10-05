package com.meshwar.dashboard.ui.activities.orders.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.Order;
import com.meshwar.dashboard.data.network.model.OrderStatus;
import com.meshwar.dashboard.databinding.ActivityOrdersBinding;
import com.meshwar.dashboard.ui.activities.orders.presenter.OrdersPresenter;
import com.meshwar.dashboard.ui.adapter.AdapterOrders;
import com.meshwar.dashboard.ui.adapter.AdapterOrderStatus;
import com.meshwar.dashboard.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends BaseActivity implements OrdersView, AdapterOrderStatus.onItemClicked, AdapterOrders.onItemClicked {

    private ActivityOrdersBinding binding;
    OrdersPresenter presenter;


    AdapterOrders adapterOrders;
    AdapterOrderStatus adapterOrderStatus;

    List<Order> orders = new ArrayList<>();
    private List<OrderStatus> orderStatuses = new ArrayList<>();

    String status = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new OrdersPresenter(context, this);

        initView();
        initData();
        initListener();
    }


    @Override
    public void initView() {
        orderStatuses.add(new OrderStatus("1", getString(R.string.current)));
        orderStatuses.add(new OrderStatus("2", getString(R.string.in_progress)));
        orderStatuses.add(new OrderStatus("3", getString(R.string.ended)));


        binding.rcOrders.setAdapter(adapterOrders = new AdapterOrders(context, orders,status, this));

        binding.rcOrderStatus.setAdapter(adapterOrderStatus = new AdapterOrderStatus(context, orderStatuses, this));

    }

    @Override
    public void initData() {
        presenter.getOrders(status);
    }


    @Override
    public void initListener() {
        binding.imvBack.setOnClickListener(v -> onBackPressed());
    }


    @Override
    public void onOrderStatusClicked(String status) {
        this.status=status;
        presenter.getOrders(status);
        adapterOrders.updateStatus(status);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void initOrders(List<Order> item) {
        this.orders.clear();
        this.orders.addAll(item);
        adapterOrders.notifyDataSetChanged();

        binding.tvEmptyOrders.setVisibility(orders.isEmpty() ? View.VISIBLE : View.GONE);
        binding.rcOrders.setVisibility(orders.isEmpty() ? View.GONE : View.VISIBLE);
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.getOrders(status);
        adapterOrderStatus.setStatus(status);
    }

    @Override
    public void onPaidClicked(int position) {
        presenter.paidOrder(orders.get(position).getUuid());
    }
}