package com.meshwar.dashboard.ui.activities.main.view;

import static com.meshwar.dashboard.utils.AppSharedConstants.DRIVER;
import static com.meshwar.dashboard.utils.AppSharedConstants.INFO_ALERT;
import static com.meshwar.dashboard.utils.AppSharedConstants.INTRODUCTION_TYPE;
import static com.meshwar.dashboard.utils.AppSharedConstants.TYPE_INTRODUCTIONS;
import static com.meshwar.dashboard.utils.AppSharedConstants.USER;
import static com.meshwar.dashboard.utils.AppSharedConstants.USER_INTRODUCTIONS;
import static com.meshwar.dashboard.utils.AppSharedConstants.USER_TYPE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.core.view.GravityCompat;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.databinding.ActivityMainBinding;
import com.meshwar.dashboard.ui.activities.balance.view.BalanceActivity;
import com.meshwar.dashboard.ui.activities.cars.view.CarsActivity;
import com.meshwar.dashboard.ui.activities.chatList.view.ChatListActivity;
import com.meshwar.dashboard.ui.activities.cities.view.CitiesActivity;
import com.meshwar.dashboard.ui.activities.drivers.view.DriversActivity;
import com.meshwar.dashboard.ui.activities.editPassword.view.EditPasswordActivity;
import com.meshwar.dashboard.ui.activities.maxDistance.view.MaxDistanceActivity;
import com.meshwar.dashboard.ui.activities.intro.view.IntroActivity;
import com.meshwar.dashboard.ui.activities.login.view.LoginActivity;
import com.meshwar.dashboard.ui.activities.main.presenter.MainActivityPresenter;
import com.meshwar.dashboard.ui.activities.orders.view.OrdersActivity;
import com.meshwar.dashboard.ui.activities.profile.view.ProfileActivity;
import com.meshwar.dashboard.ui.activities.terms.view.TermsActivity;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.utils.AppSharedMethod;


public class MainActivity extends BaseActivity implements MainActivityView {

    private ActivityMainBinding binding;

    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new MainActivityPresenter(this, this);

        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {
        AppSharedMethod.requestNotificationPermission(this);
        binding.linearBalance.setVisibility(preference.getUser().getSuperAdmin()? View.VISIBLE:View.INVISIBLE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.imvMenu.setOnClickListener(v -> binding.drawerLayout.openDrawer(GravityCompat.START));

        binding.linearOrders.setOnClickListener(v -> {
            startActivity(new Intent(context, OrdersActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.linearDriversMsg.setOnClickListener(v -> {
            startActivity(new Intent(context, ChatListActivity.class).putExtra(USER_TYPE,DRIVER));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.linearUsersMsg.setOnClickListener(v -> {
            startActivity(new Intent(context, ChatListActivity.class).putExtra(USER_TYPE,USER));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.linearUserPages.setOnClickListener(v -> {
            startActivity(new Intent(context, IntroActivity.class).putExtra(INTRODUCTION_TYPE,USER_INTRODUCTIONS));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.linearDriverPages.setOnClickListener(v -> {
            startActivity(new Intent(context, IntroActivity.class).putExtra(INTRODUCTION_TYPE,TYPE_INTRODUCTIONS));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.linearTermsAndConditions.setOnClickListener(v -> {
            startActivity(new Intent(context, TermsActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.linearDrivers.setOnClickListener(v -> {
            startActivity(new Intent(context, DriversActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.linearLocations.setOnClickListener(v -> {
            startActivity(new Intent(context, CitiesActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.linearCars.setOnClickListener(v -> {
            startActivity(new Intent(context, CarsActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.linearBalance.setOnClickListener(v -> {
            startActivity(new Intent(context, BalanceActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        binding.sideMenu.imvProfile.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(context, ProfileActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        });
        binding.sideMenu.tvProfile.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(context, ProfileActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        binding.sideMenu.imvPassword.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(context, EditPasswordActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        });
        binding.sideMenu.tvPassword.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(context, EditPasswordActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        binding.sideMenu.imvMax.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(context, MaxDistanceActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.sideMenu.tvMax.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(context, MaxDistanceActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });


        binding.sideMenu.imvLogout.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            presenter.logout();

        });
        binding.sideMenu.tvLogout.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            presenter.logout();
        });

    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        } else {

            this.doubleBackToExitPressedOnce = true;
            showMessage(R.string.press_back_again, INFO_ALERT);

            new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

        }
    }


    @Override
    public void logout() {
        startActivity(new Intent(context, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finishAffinity();
    }
}