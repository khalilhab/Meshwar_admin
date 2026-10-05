package com.meshwar.dashboard.ui.activities.maxDistance.view;

import static com.meshwar.dashboard.utils.AppSharedConstants.SUCCESS_ALERT;

import android.os.Bundle;
import android.text.TextUtils;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.databinding.ActivityMaxDistanceBinding;
import com.meshwar.dashboard.ui.activities.maxDistance.presenter.MaxDistancePresenter;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.utils.AppSharedMethod;

import java.util.Objects;

public class MaxDistanceActivity extends BaseActivity implements MaxDistanceView {

    MaxDistancePresenter presenter;
    ActivityMaxDistanceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMaxDistanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new MaxDistancePresenter(context, this);

        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        presenter.getMaxDistance();
    }

    @Override
    public void initListener() {
        binding.imvBack.setOnClickListener(v -> onBackPressed());
        binding.btnChange.setOnClickListener(v -> {
            if (validate())
                presenter.updateMaxDistance(Objects.requireNonNull(binding.etMaxDistance.getText()).toString().trim());
        });
    }

    private boolean validate() {
        AppSharedMethod.clearError(binding.maxDistanceTF);

        if (binding.etMaxDistance.getText() != null && TextUtils.isEmpty(binding.etMaxDistance.getText().toString().trim())) {
            AppSharedMethod.showError(binding.maxDistanceTF, getString(R.string.this_field_is_required));
            return false;
        }

        return true;
    }

    @Override
    public void onMaxDistanceLoaded(String currentValue) {
        if (currentValue != null && !TextUtils.isEmpty(currentValue)) {
            binding.etMaxDistance.setText(currentValue);
        }
    }

    @Override
    public void onMaxDistanceUpdated() {
        showMessage(getString(R.string.updated_successfully), SUCCESS_ALERT);
        finish();
    }
}
