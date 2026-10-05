package com.meshwar.dashboard.ui.activities.profile.view;

import static com.meshwar.dashboard.utils.AppSharedConstants.SUCCESS_ALERT;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.bumptech.glide.Glide;
import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.User;
import com.meshwar.dashboard.databinding.ActivityProfileBinding;
import com.meshwar.dashboard.ui.activities.profile.presenter.ProfilePresenter;
import com.meshwar.dashboard.ui.activities.login.view.LoginActivity;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.utils.AppSharedConstants;
import com.meshwar.dashboard.utils.AppSharedMethod;

import java.util.Objects;

import okhttp3.MultipartBody;


public class ProfileActivity extends BaseActivity implements ProfileView {

    private ActivityProfileBinding binding;

    private ProfilePresenter presenter;


    private MultipartBody.Part imgImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new ProfilePresenter(this, this);

        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        presenter.getProfile();
    }

    @Override
    public void initListener() {
        binding.imvBack.setOnClickListener(v -> onBackPressed());
        ActivityResultLauncher<Intent> launcherImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Uri uri = AppSharedMethod.getSelectedImageUri(context, result.getResultCode(), result.getData());
            if (uri != null) {
                // through Glide, so the profile image request started in initProfile can't overwrite the picked image
                Glide.with(context).load(uri).centerCrop().into(binding.imvUser);
                imgImage = AppSharedMethod.getPart(context, uri, "image");
            }
        });

        binding.imvUser.setOnClickListener(v -> {
            Intent intent = AppSharedMethod.checkPermissionStorage(context, AppSharedConstants.REQUEST_PERMISSIONS_CODE_IMAGE_IMAGE, AppSharedConstants.TYPE_IMAGE, AppSharedConstants.TYPE_ALL);
            if (intent != null) {
                launcherImage.launch(intent);
            }
        });
        binding.btnSave.setOnClickListener(v -> {
            if (validate()) {
                presenter.updateProfile(
                        AppSharedMethod.getPart(binding.name),
                        AppSharedMethod.getPart(binding.email),
                        AppSharedMethod.getPart(AppSharedMethod.checkMobileValidate(binding.phoneNumber.toString())),
                        imgImage
                        );
            }
        });
    }

    private boolean validate() {

        AppSharedMethod.clearError(binding.nameTF, binding.emailTF, binding.phoneTF, binding.passwordTF);

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

        return true;
    }

    @Override
    public void initProfile(User user) {
        if (user == null) {
            startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finishAffinity();
        }

        Glide.with(context).load(user.getImage()).placeholder(R.drawable.circle).centerCrop().into(binding.imvUser);
        binding.phoneNumber.setText(user.getMobile_without_prefix());
        binding.name.setText(user.getName());
        binding.email.setText(user.getEmail());

    }

    @Override
    public void updateProfile() {
        showMessage(getString(R.string.updated_successfully),SUCCESS_ALERT);
        finish();
    }
}