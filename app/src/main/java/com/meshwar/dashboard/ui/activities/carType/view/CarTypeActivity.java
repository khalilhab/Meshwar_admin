package com.meshwar.dashboard.ui.activities.carType.view;

import static com.meshwar.dashboard.utils.AppSharedConstants.CAR_TYPES;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.CarType;
import com.meshwar.dashboard.databinding.ActivityCitiesBinding;
import com.meshwar.dashboard.databinding.DialogCityBinding;
import com.meshwar.dashboard.ui.activities.carType.presenter.CarTypePresenter;
import com.meshwar.dashboard.ui.adapter.AdapterCarType;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.utils.AppSharedMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CarTypeActivity extends BaseActivity implements CarTypeView, AdapterCarType.onDeleteClicked, AdapterCarType.onItemClicked {

    private ActivityCitiesBinding binding;
    private CarTypePresenter presenter;
    List<CarType> carTypes = new ArrayList<>();
    AdapterCarType adapterCarType;

    DialogCityBinding dialogCarTypeBinding;
    Dialog cityDialog;

    boolean hasEdited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCitiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new CarTypePresenter(this, this);

        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {
        binding.tvTitle.setText(getString(R.string.car_types));

        binding.rcCities.setAdapter(adapterCarType = new AdapterCarType(context, carTypes, this, this));
        binding.imvAddCity.setOnClickListener(v -> showAddCityDialog("add", 0));
    }

    @Override
    public void initData() {
        presenter.getCarTypes();
    }

    @Override
    public void initListener() {

        binding.imvBack.setOnClickListener(v -> onBackPressed());
    }

    private void showAddCityDialog(String actionType, int position) {
        if (cityDialog != null && cityDialog.isShowing())
            cityDialog.dismiss();

        cityDialog = new Dialog(context, R.style.StyleDialog);
        dialogCarTypeBinding = DialogCityBinding.inflate(getLayoutInflater());
        cityDialog.setContentView(dialogCarTypeBinding.getRoot());
        cityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cityDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cityDialog.setCancelable(true);
        cityDialog.show();

        if (actionType.equals("edit")) {
            dialogCarTypeBinding.edName.setText(carTypes.get(position).getName());
        }

        dialogCarTypeBinding.edName.setHint(getString(R.string.enter_car_type));
        dialogCarTypeBinding.tvAdd.setText(actionType.equals("add") ? getString(R.string.add) : getString(R.string.edit));
        dialogCarTypeBinding.tvAdd.setOnClickListener(v -> {
            if (TextUtils.isEmpty(Objects.requireNonNull(dialogCarTypeBinding.edName.getText()).toString())) {
                AppSharedMethod.showError(dialogCarTypeBinding.nameTF, getString(R.string.enter_car_type));
            } else {
                AppSharedMethod.clearError(dialogCarTypeBinding.nameTF);
                cityDialog.dismiss();
                if (actionType.equals("add")) {
                    hasEdited = true;
                    presenter.addCarType(dialogCarTypeBinding.edName.getText().toString());
                } else{
                    hasEdited = true;
                    presenter.editCarType(carTypes.get(position).getUuid(), dialogCarTypeBinding.edName.getText().toString());
                }
            }
        });

        dialogCarTypeBinding.tvDelete.setOnClickListener(v -> {
            cityDialog.dismiss();
            if (actionType.equals("edit")){
                hasEdited = true;
                presenter.deleteCarType(carTypes.get(position).getUuid());
            }
        });

    }


    @Override
    public void onDeleteClicked(int position) {
        presenter.deleteCarType(carTypes.get(position).getUuid());
    }

    @Override
    public void onItemClicked(int position) {
        showAddCityDialog("edit", position);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void getCarTypes(List<CarType> carTypes) {
        this.carTypes.clear();
        this.carTypes.addAll(carTypes);
        adapterCarType.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (hasEdited) {
            Intent intent = new Intent();
            intent.putExtra(CAR_TYPES,new Gson().toJson(carTypes));
            setResult(RESULT_OK,intent);
        }
        super.onBackPressed();
    }
}