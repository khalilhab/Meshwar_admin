package com.meshwar.dashboard.ui.activities.cars.view;

import static com.meshwar.dashboard.utils.AppSharedConstants.CAR_ID;
import static com.meshwar.dashboard.utils.AppSharedConstants.IS_UPDATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.Car;
import com.meshwar.dashboard.databinding.ActivityCarBinding;
import com.meshwar.dashboard.ui.adapter.AdapterCar;
import com.meshwar.dashboard.ui.activities.addCar.view.AddCarActivity;
import com.meshwar.dashboard.ui.activities.cars.presenter.CarsPresenter;
import com.meshwar.dashboard.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class CarsActivity extends BaseActivity implements CarsView, AdapterCar.onItemClicked, AdapterCar.OnBottomReached {

    private ActivityCarBinding binding;
    CarsPresenter presenter;
    AdapterCar adapterCar;
    List<Car> carList= new ArrayList<>();

    ActivityResultLauncher<Intent> launcher;


    private Integer page = 1;
    private Integer lastPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new CarsPresenter(this, this);

        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {
        binding.rcCars.setAdapter(adapterCar = new AdapterCar(context, carList,this,this));
    }


    @Override
    public void initData() {
        presenter.getCars(page);
    }

    @Override
    public void initListener() {
         launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode()==RESULT_OK) {
                presenter.getCars(page=1);
            }
        });

        binding.linearAddCar.setOnClickListener(v -> {
            launcher.launch(new Intent(context, AddCarActivity.class).putExtra(IS_UPDATE,false));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        binding.imvBack.setOnClickListener(v -> onBackPressed());
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void initCars(List<Car> carList, int currentPage, int lastPage) {
        if (page == 1) {
            this.carList.clear();
        }

        this.carList.addAll(carList);

        binding.pagerProgress.setVisibility(View.GONE);
        adapterCar.notifyDataSetChanged();


        this.page=currentPage;
        this.lastPage=lastPage;
    }

    @Override
    public void onBottomReached(int position) {
        if (page!=lastPage) {
            binding.pagerProgress.setVisibility(View.VISIBLE);
            page++;
            presenter.getCars(page);
        }
    }


    @Override
    public void onItemClicked(int position) {
        launcher.launch(new Intent(context, AddCarActivity.class).putExtra(CAR_ID,carList.get(position).getUuid()).putExtra(IS_UPDATE,true));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}