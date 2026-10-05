package com.meshwar.dashboard.ui.activities.editPassword.view;

import static com.meshwar.dashboard.utils.AppSharedConstants.SUCCESS_ALERT;

import android.os.Bundle;
import android.text.TextUtils;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.databinding.ActivityEditPasswordBinding;
import com.meshwar.dashboard.ui.activities.editPassword.presenter.EditPasswordPresenter;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.utils.AppSharedMethod;

import java.util.Objects;

public class EditPasswordActivity extends BaseActivity implements EditPasswordView {


    EditPasswordPresenter presenter;
    ActivityEditPasswordBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        presenter = new EditPasswordPresenter(context, this);

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
        binding.imvBack.setOnClickListener(v -> onBackPressed());
        binding.btnEditPassword.setOnClickListener(v -> {
            if (validate())
                presenter.changePassword(Objects.requireNonNull(binding.oldPassword.getText()).toString(), Objects.requireNonNull(binding.newPassword.getText()).toString());
        });
    }

    private boolean validate() {

        AppSharedMethod.clearError(binding.oldPasswordTF, binding.newPasswordTF, binding.confirmNewPasswordTF);

        if (binding.oldPassword.getText() != null && TextUtils.isEmpty(binding.oldPassword.getText().toString().trim())) {
            AppSharedMethod.showError(binding.oldPasswordTF, getString(R.string.enter_old_password));
            return false;
        }
        if (binding.newPassword.getText() != null && TextUtils.isEmpty(binding.newPassword.getText().toString().trim())) {
            AppSharedMethod.showError(binding.newPasswordTF, getString(R.string.enter_new_password));
            return false;
        }
        if (binding.confirmNewPassword.getText() != null && TextUtils.isEmpty(binding.confirmNewPassword.getText().toString().trim())) {
            AppSharedMethod.showError(binding.confirmNewPasswordTF, getString(R.string.enter_confirm_new_password));
            return false;
        }

        if (!binding.confirmNewPassword.getText().toString().equals(binding.newPassword.getText().toString())) {
            AppSharedMethod.showError(binding.confirmNewPasswordTF, getString(R.string.new_password_dosnt_match));
            return false;
        }

        return true;
    }

    @Override
    public void passwordChanged() {
        showMessage(getString(R.string.updated_successfully),SUCCESS_ALERT);
        finish();
    }
}
