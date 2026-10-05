package com.meshwar.dashboard.ui.activities.sendNotification.view;

import static com.meshwar.dashboard.utils.AppSharedConstants.CAR_ID;
import static com.meshwar.dashboard.utils.AppSharedConstants.DRIVER_ID;
import static com.meshwar.dashboard.utils.AppSharedConstants.SUCCESS_ALERT;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.databinding.ActivityLoginBinding;
import com.meshwar.dashboard.databinding.ActivitySendNotificationBinding;
import com.meshwar.dashboard.ui.activities.main.view.MainActivity;
import com.meshwar.dashboard.ui.activities.register.view.RegisterActivity;
import com.meshwar.dashboard.ui.activities.sendNotification.presenter.SendNotificationPresenter;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.utils.AppSharedMethod;

import java.util.Objects;


public class SendNotificationActivity extends BaseActivity implements SendNotificationView {

    private ActivitySendNotificationBinding binding;
    private SendNotificationPresenter presenter;

    String driverId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new SendNotificationPresenter(this, this);

        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {

        if (getIntent()!=null)
            if (getIntent().hasExtra(DRIVER_ID))
                driverId = getIntent().getStringExtra(DRIVER_ID);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

        binding.imvBack.setOnClickListener(v -> onBackPressed());
        binding.btnSend.setOnClickListener(v -> {
            AppSharedMethod.clearError(binding.descriptionTF);
            if (binding.edDescription.getText() != null && TextUtils.isEmpty(binding.edDescription.getText().toString().trim())) {
                AppSharedMethod.showError(binding.descriptionTF, getString(R.string.write_the_notice_text));
            }else {
                presenter.sendNotification(driverId,binding.edDescription.getText().toString());
            }
        });
    }

    @Override
    public void notificationSent() {
        showMessage(getString(R.string.sent_successfully),SUCCESS_ALERT);
        finish();
    }
}