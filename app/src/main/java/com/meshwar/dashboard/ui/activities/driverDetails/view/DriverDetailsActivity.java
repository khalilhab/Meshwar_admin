package com.meshwar.dashboard.ui.activities.driverDetails.view;

import static com.meshwar.dashboard.utils.AppSharedConstants.DRIVER_ID;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.data.network.model.Driver;
import com.meshwar.dashboard.databinding.ActivityDriverDetailsBinding;
import com.meshwar.dashboard.ui.activities.driverDetails.presenter.DriverDetailsPresenter;
import com.meshwar.dashboard.ui.activities.drivers.view.DriversActivity;
import com.meshwar.dashboard.ui.activities.sendNotification.view.SendNotificationActivity;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;


public class DriverDetailsActivity extends BaseActivity implements DriverDetailsView {

    private ActivityDriverDetailsBinding binding;
    private DriverDetailsPresenter presenter;

    String driverUuid="";

    Driver driver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new DriverDetailsPresenter(this, this);

        initView();
        initData();
        initListener();
        setupSaudiPlateFields();
    }

    @Override
    public void initView() {
        if (getIntent()!=null)
            if (getIntent().hasExtra(DRIVER_ID))
            driverUuid = getIntent().getStringExtra(DRIVER_ID);
    }

    @Override
    public void initData() {
        presenter.getDriverDetails(driverUuid);
    }

    @Override
    public void initListener() {
        binding.imvBack.setOnClickListener(v -> onBackPressed());
        binding.myInfoTab.setOnClickListener(v -> {
            binding.view1.setBackgroundColor(ContextCompat.getColor(context,R.color.black));
            binding.view2.setBackgroundColor(ContextCompat.getColor(context,R.color.colorHint2));
            binding.scrollView.setVisibility(View.VISIBLE);
            binding.briefTF.setVisibility(View.GONE);
        });
        binding.myBriefTab.setOnClickListener(v -> {
            binding.view1.setBackgroundColor(ContextCompat.getColor(context,R.color.colorHint2));
            binding.view2.setBackgroundColor(ContextCompat.getColor(context,R.color.black));
            binding.scrollView.setVisibility(View.GONE);
            binding.briefTF.setVisibility(View.VISIBLE);
        });
        binding.btnSendNotification.setOnClickListener(v -> {
            startActivity(new Intent(context, SendNotificationActivity.class).putExtra(DRIVER_ID,driverUuid));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.btnAccept.setOnClickListener(v -> {
            presenter.acceptDriver(driverUuid);
        });
    }


    @Override
    public void initDriver(Driver driver) {
        this.driver=driver;

        Glide.with(context).load(driver.getImage()).placeholder(R.drawable.circle_avatar).centerCrop().into(binding.imvDriver);
        Glide.with(context).load(driver.getIdentityImage1()).centerCrop().placeholder(R.drawable.id_placeholder).into(binding.imvIdCardFront);
        Glide.with(context).load(driver.getIdentityImage2()).centerCrop().placeholder(R.drawable.id_placeholder).into(binding.imvIdCardBack);
        Glide.with(context).load(driver.getLicenseImage1()).centerCrop().placeholder(R.drawable.id_placeholder).into(binding.imvDrivingLicenseFront);
        Glide.with(context).load(driver.getLicenseImage2()).centerCrop().placeholder(R.drawable.id_placeholder).into(binding.imvDrivingLicenseBack);
        Glide.with(context).load(driver.getImage()).centerCrop().placeholder(R.drawable.avatar).into(binding.imvDriverPhoto);

        binding.name.setText(driver.getName());
        binding.email.setText(driver.getEmail());
        binding.phoneNumber.setText(driver.getMobile());

        binding.tvDob.setText(driver.getDateOfBirth());

        binding.carType.setText(driver.getCarName());

        populateSaudiPlate(driver.getLicense());
        binding.brief.setText(driver.getBio());

        binding.ratingBar.setRating(Float.parseFloat(driver.getRatingAvg()));

        binding.btnAccept.setVisibility(driver.getIsAccepted()?View.GONE:View.VISIBLE);



        binding.imvDriver.setOnClickListener(v -> showImageDialog(driver.getImage()));
        binding.imvIdCardFront.setOnClickListener(v -> showImageDialog(driver.getIdentityImage1()));
        binding.imvIdCardBack.setOnClickListener(v -> showImageDialog(driver.getIdentityImage2()));
        binding.imvDrivingLicenseFront.setOnClickListener(v -> showImageDialog(driver.getLicenseImage1()));
        binding.imvDrivingLicenseBack.setOnClickListener(v -> showImageDialog(driver.getLicenseImage2()));
        binding.imvDriverPhoto.setOnClickListener(v -> showImageDialog(driver.getImage()));


    }

    @Override
    public void driverAccepted() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void getCities(List<City> cities) {
    }


    private void showImageDialog(String media) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        @SuppressLint("InflateParams") View mView = LayoutInflater.from(context).inflate(R.layout.dialog_image, null);
        dialog.getWindow().setContentView(mView);
        ImageView imv_full = mView.findViewById(R.id.imv_full);
        ImageView imv_close = mView.findViewById(R.id.imv_close);
        Picasso.get().load(media).into(imv_full);
        imv_close.setOnClickListener(view -> dialog.dismiss());
    }

    private void setupSaudiPlateFields() {
        final android.widget.EditText[] allFields = new android.widget.EditText[]{
                binding.etPlateNum1, binding.etPlateNum2, binding.etPlateNum3, binding.etPlateNum4,
                binding.etPlateLet1, binding.etPlateLet2, binding.etPlateLet3
        };
        android.widget.EditText[] letFields = new android.widget.EditText[]{
                binding.etPlateLet1, binding.etPlateLet2, binding.etPlateLet3
        };

        android.text.InputFilter letterFilter = new android.text.InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, android.text.Spanned dest, int dstart, int dend) {
                StringBuilder sb = new StringBuilder();
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (Character.isLetter(c)) {
                        sb.append(Character.toUpperCase(c));
                    }
                }
                if (sb.length() == source.length() - start) {
                    return null;
                }
                return sb.toString();
            }
        };

        for (android.widget.EditText letField : letFields) {
            letField.setFilters(new android.text.InputFilter[]{letterFilter, new android.text.InputFilter.LengthFilter(1)});
        }

        for (int i = 0; i < allFields.length; i++) {
            final int index = i;
            allFields[i].addTextChangedListener(new android.text.TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(android.text.Editable s) {
                    if (s != null && s.length() == 1) {
                        if (index < allFields.length - 1) {
                            allFields[index + 1].requestFocus();
                        }
                    }
                }
            });

            allFields[i].setOnKeyListener(new android.view.View.OnKeyListener() {
                @Override
                public boolean onKey(android.view.View v, int keyCode, android.view.KeyEvent event) {
                    if (keyCode == android.view.KeyEvent.KEYCODE_DEL && event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                        if (allFields[index].getText().toString().isEmpty()) {
                            if (index > 0) {
                                allFields[index - 1].setText("");
                                allFields[index - 1].requestFocus();
                                return true;
                            }
                        }
                    }
                    return false;
                }
            });

            allFields[i].setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(android.view.View v, boolean hasFocus) {
                    if (hasFocus) {
                        android.widget.EditText editText = (android.widget.EditText) v;
                        editText.setSelection(editText.getText().length());
                    }
                }
            });

            allFields[i].setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    android.widget.EditText editText = (android.widget.EditText) v;
                    editText.setSelection(editText.getText().length());
                }
            });
        }
    }

    private void populateSaudiPlate(String license) {
        if (license == null) return;
        String cleanLicense = license.replaceAll("[^a-zA-Z0-9\u0600-\u06FF]", "");
        StringBuilder digits = new StringBuilder();
        StringBuilder letters = new StringBuilder();
        for (int i = 0; i < cleanLicense.length(); i++) {
            char c = cleanLicense.charAt(i);
            if (Character.isDigit(c)) {
                digits.append(c);
            } else if (Character.isLetter(c)) {
                letters.append(c);
            }
        }

        android.widget.EditText[] numFields = new android.widget.EditText[]{
                binding.etPlateNum1, binding.etPlateNum2, binding.etPlateNum3, binding.etPlateNum4
        };
        android.widget.EditText[] letFields = new android.widget.EditText[]{
                binding.etPlateLet1, binding.etPlateLet2, binding.etPlateLet3
        };

        for (int i = 0; i < 4; i++) {
            if (i < digits.length()) {
                numFields[i].setText(String.valueOf(digits.charAt(i)));
            } else {
                numFields[i].setText("");
            }
        }

        for (int i = 0; i < 3; i++) {
            if (i < letters.length()) {
                letFields[i].setText(String.valueOf(letters.charAt(i)));
            } else {
                letFields[i].setText("");
            }
        }
    }
}