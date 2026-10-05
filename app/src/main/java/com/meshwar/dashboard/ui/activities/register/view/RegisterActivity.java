package com.meshwar.dashboard.ui.activities.register.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.databinding.ActivityRegisterBinding;
import com.meshwar.dashboard.ui.activities.main.view.MainActivity;
import com.meshwar.dashboard.ui.activities.register.presenter.RegisterPresenter;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.utils.AppSharedMethod;

import java.util.Objects;


public class RegisterActivity extends BaseActivity implements RegisterView {

    private ActivityRegisterBinding binding;

    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new RegisterPresenter(this, this);

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
        binding.tvLogin.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.tvHaveAccount.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        binding.btnCreateAccount.setOnClickListener(v -> {

            if (validate()) {
                presenter.registerAdmin(Objects.requireNonNull(binding.name.getText()).toString(), Objects.requireNonNull(binding.email.getText()).toString()
                        , AppSharedMethod.checkMobileValidate(Objects.requireNonNull(binding.phoneNumber.getText()).toString()), Objects.requireNonNull(binding.password.getText()).toString());
            }
        });

    }

    private boolean validate() {

        AppSharedMethod.clearError(binding.nameTF, binding.emailTF, binding.phoneTF, binding.passwordTF, binding.confirmPasswordTF);

        if (binding.name.getText() != null && TextUtils.isEmpty(binding.name.getText().toString().trim())) {
            AppSharedMethod.showError(binding.nameTF, getString(R.string.enter_name));
            return false;
        }
        if (binding.email.getText() != null && TextUtils.isEmpty(binding.email.getText().toString().trim())) {
            AppSharedMethod.showError(binding.emailTF, getString(R.string.enter_email));
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Objects.requireNonNull(binding.email.getText()).toString()).matches()) {
            AppSharedMethod.showError(binding.emailTF, getString(R.string.email_is_invalid));
            return false;
        }
        if (binding.phoneNumber.getText() != null && TextUtils.isEmpty(AppSharedMethod.checkMobileValidate(binding.phoneNumber.getText().toString().trim()))) {
            AppSharedMethod.showError(binding.phoneTF, getString(R.string.mobile_number_is_invalid));
            return false;
        }
        if (binding.password.getText() != null && TextUtils.isEmpty(binding.password.getText().toString().trim())) {
            AppSharedMethod.showError(binding.passwordTF, getString(R.string.enter_password));
            return false;
        }
        if (binding.confirmPassword.getText() != null && TextUtils.isEmpty(binding.confirmPassword.getText().toString().trim())) {
            AppSharedMethod.showError(binding.confirmPasswordTF, getString(R.string.enter_confirm_password));
            return false;
        }
        if (!binding.confirmPassword.getText().toString().equals(binding.password.getText().toString())) {
            AppSharedMethod.showError(binding.confirmPasswordTF, getString(R.string.password_dosnt_match));
            return false;
        }

        return true;
    }


    @Override
    public void registeredSuccessfully() {
        startActivity(new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finishAffinity();
    }
}