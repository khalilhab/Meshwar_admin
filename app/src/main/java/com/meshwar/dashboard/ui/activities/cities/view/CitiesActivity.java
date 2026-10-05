package com.meshwar.dashboard.ui.activities.cities.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.databinding.ActivityCitiesBinding;
import com.meshwar.dashboard.databinding.DialogCityBinding;
import com.meshwar.dashboard.ui.adapter.AdapterCity;
import com.meshwar.dashboard.ui.activities.cities.presenter.CitiesPresenter;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.utils.AppSharedMethod;

import java.util.ArrayList;
import java.util.List;


public class CitiesActivity extends BaseActivity implements CitiesView, AdapterCity.onDeleteClicked, AdapterCity.onItemClicked {

    private ActivityCitiesBinding binding;
    private CitiesPresenter presenter;
    List<City> cities = new ArrayList<>();
    AdapterCity adapterCity;

    DialogCityBinding dialogCityBinding;
    Dialog cityDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCitiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new CitiesPresenter(this, this);

        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {
        binding.tvTitle.setText(getString(R.string.areas));
        binding.rcCities.setAdapter(adapterCity = new AdapterCity(context, cities, this, this));
        binding.imvAddCity.setOnClickListener(v -> {
            showAddCityDialog("add", 0);
        });

    }


    @Override
    public void initData() {
        presenter.getCities();
    }

    @Override
    public void initListener() {
        binding.imvBack.setOnClickListener(v -> onBackPressed());

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void getCities(List<City> cities) {
        this.cities.clear();
        this.cities.addAll(cities);
        adapterCity.notifyDataSetChanged();
    }


    private void showAddCityDialog(String actionType, int position) {
        if (cityDialog != null && cityDialog.isShowing())
            cityDialog.dismiss();

        cityDialog = new Dialog(context, R.style.StyleDialog);
        dialogCityBinding = DialogCityBinding.inflate(getLayoutInflater());
        cityDialog.setContentView(dialogCityBinding.getRoot());
        cityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cityDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cityDialog.setCancelable(true);
        cityDialog.show();

        if (actionType.equals("edit")){
            dialogCityBinding.edName.setText(cities.get(position).getName());
        }

        dialogCityBinding.tvAdd.setText(actionType.equals("add") ? getString(R.string.add) : getString(R.string.edit));
        dialogCityBinding.tvAdd.setOnClickListener(v -> {
            if (TextUtils.isEmpty(dialogCityBinding.edName.getText().toString())) {
                AppSharedMethod.showError(dialogCityBinding.nameTF, getString(R.string.enter_city_name));
            } else {
                AppSharedMethod.clearError(dialogCityBinding.nameTF);
                cityDialog.dismiss();
                if (actionType.equals("add"))
                    presenter.addCity(dialogCityBinding.edName.getText().toString());
                else
                    presenter.editCity(cities.get(position).getUuid(),dialogCityBinding.edName.getText().toString());
            }
        });

        dialogCityBinding.tvDelete.setOnClickListener(v -> {
                cityDialog.dismiss();
            if (actionType.equals("edit"))
                presenter.deleteCity(cities.get(position).getUuid());
        });

    }


    @Override
    public void onDeleteClicked(int position) {
        presenter.deleteCity(cities.get(position).getUuid());
    }

    @Override
    public void onItemClicked(int position) {
        showAddCityDialog("edit",position);
    }
}