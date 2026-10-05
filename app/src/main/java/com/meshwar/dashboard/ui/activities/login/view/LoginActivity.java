package com.meshwar.dashboard.ui.activities.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;


import com.meshwar.dashboard.R;
import com.meshwar.dashboard.databinding.ActivityLoginBinding;
import com.meshwar.dashboard.ui.activities.login.presenter.LoginPresenter;
import com.meshwar.dashboard.ui.activities.main.view.MainActivity;
import com.meshwar.dashboard.ui.activities.register.view.RegisterActivity;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.utils.AppSharedMethod;

import java.util.Objects;


public class LoginActivity extends BaseActivity implements LoginView {

    private ActivityLoginBinding binding;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new LoginPresenter(this, this);

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
        binding.btnCreateAccount.setOnClickListener(v -> {
            startActivity(new Intent(context, RegisterActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.btnLogin.setOnClickListener(v -> {
            if (validate()) {
                presenter.loginAdmin(Objects.requireNonNull(binding.email.getText()).toString(), Objects.requireNonNull(binding.password.getText()).toString());
            }
        });
    }

    private boolean validate() {

        AppSharedMethod.clearError(binding.emailTF, binding.passwordTF);


        if (binding.email.getText() != null && TextUtils.isEmpty(binding.email.getText().toString().trim())) {
            AppSharedMethod.showError(binding.emailTF, getString(R.string.enter_email));
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Objects.requireNonNull(binding.email.getText()).toString()).matches()) {
            AppSharedMethod.showError(binding.emailTF, getString(R.string.email_is_invalid));
            return false;
        }

        if (binding.password.getText() != null && TextUtils.isEmpty(binding.password.getText().toString().trim())) {
            AppSharedMethod.showError(binding.passwordTF, getString(R.string.enter_password));
            return false;
        }

        return true;
    }


    @Override
    public void loginSuccessfully() {
        startActivity(new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finishAffinity();
    }
}