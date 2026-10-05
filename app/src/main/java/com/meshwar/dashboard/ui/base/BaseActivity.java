package com.meshwar.dashboard.ui.base;

import static com.meshwar.dashboard.utils.AppSharedConstants.FAIL_ALERT;
import static com.meshwar.dashboard.utils.AppSharedConstants.INFO_ALERT;
import static com.meshwar.dashboard.utils.AppSharedConstants.SUCCESS_ALERT;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.meshwar.dashboard.data.network.model.User;
import com.meshwar.dashboard.data.prefs.AppPreferencesHelper;
import com.meshwar.dashboard.service.NetworkChangeReceiver;
import com.meshwar.dashboard.ui.activities.login.view.LoginActivity;
import com.meshwar.dashboard.ui.activities.main.view.MainActivity;
import com.meshwar.dashboard.ui.activities.splash.view.SplashActivity;
import com.meshwar.dashboard.ui.custom.ProgressBarDialog;
import com.meshwar.dashboard.utils.LocaleManager;
import com.meshwar.dashboard.R;

import java.util.Objects;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    public TextView txtTitle;

    public abstract void initView();

    public abstract void initData();

    public abstract void initListener();

    public int screenHeight;
    public int screenWidth;


    public Context context;
    public Toolbar toolbar;
    public AppPreferencesHelper preference;

    public ProgressBarDialog dialog;

    protected BasePresenter presenter;

    private NetworkChangeReceiver networkChangeReceiver;
    public ActivityResultLauncher<Intent> baseLauncher;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        context = BaseActivity.this;
        preference = new AppPreferencesHelper();
        LocaleManager.setNewLocale(context);
        super.onCreate(savedInstanceState);
        baseLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;


//        Thread.setDefaultUncaughtExceptionHandler((paramThread, paramThrowable) -> {
//            Intent intent = new Intent(context, ExceptionActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//
//
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(10);
//        });


        dialog = new ProgressBarDialog(context);

        presenter = new BasePresenter(context, this);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        applySystemBarsPadding();
    }

    // Android 15+ lays the app out edge-to-edge (with no opt-out when targeting 36),
    // so keep the content clear of the status/navigation bars and the keyboard as before
    private void applySystemBarsPadding() {
        int softInputMode = getWindow().getAttributes().softInputMode;
        int adjust = softInputMode & WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST;
        if (adjust == WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED) {
            adjust = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
            getWindow().setSoftInputMode(softInputMode | adjust);
        }

        int insetTypes = WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout();
        if (adjust == WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE) {
            insetTypes |= WindowInsetsCompat.Type.ime();
        }
        int types = insetTypes;

        View content = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(content, (v, insets) -> {
            Insets bars = insets.getInsets(types);
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        ViewCompat.requestApplyInsets(content);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        LocaleManager.setNewLocale(context);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.setNewLocale(newBase));
    }

    public void initToolbarBack(int title) {
        initToolbarBack(getString(title), false);
    }

    public void initToolbarBack(int title, boolean hide) {
        initToolbarBack(getString(title), hide);
    }

    public void initToolbarTitle(int title) {
        initToolbarTitle(getString(title));
    }

    public void initToolbarTitle(String title) {
        if (txtTitle != null)
            txtTitle.setText(title);
    }

    public void initToolbarBack(String title) {
        initToolbarBack(title, false);
    }

    public void initToolbarBack(String title, boolean hide) {
        txtTitle = findViewById(R.id.tv_toolbarTitle);
        toolbar = findViewById(R.id.toolbar_back);
        toolbar.setTitle("");
        if (txtTitle != null)
            txtTitle.setText(title);
        if (!hide) {
            toolbar.setNavigationIcon(R.drawable.ic_left_arrow);
        }
        setSupportActionBar(null);
        setSupportActionBar(toolbar);
        if (!hide) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
    }

    public String getStringExtra(String key) {
        if (getIntent() != null && getIntent().hasExtra(key))
            return getIntent().getStringExtra(key);
        return "";
    }

//    public void loginRequired() {
//        loginRequired(true);
//    }
//
//    public void loginRequired(boolean finish) {
//        startActivity(new Intent(context, LoginRequiredActivity.class));
//        if (finish)
//            finish();
//    }

    @Override
    public void showProgress() {
        runOnUiThread(() -> {
            dialog.dismiss();
            dialog.show();
        });
    }

    @Override
    public void showProgressTransparent() {
//        runOnUiThread(() -> {
//            dialog.dismiss();
//            dialog.show();
//            dialog.setDimAmount(0.0f);
//        });
    }

    @Override
    public void hideProgressTransparent() {
//        runOnUiThread(() -> {
//            dialog.dismiss();
//            dialog.setDimAmount(0.5f);
//        });
    }

    @Override
    public void hideProgress() {
        runOnUiThread(() -> dialog.dismiss());
    }

    @Override
    public void showMessage(int resId,int alertType) {
        showMessage(getString(resId),alertType);
    }

    @Override
    public void showMessage(String message,int alertType) {

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL , 0, 50);

        toast.setMargin(20,0);
        View view = LayoutInflater.from(context)
                .inflate(R.layout.custom_toast, null);

        LottieAnimationView animationView = view.findViewById(R.id.animationView);
        CardView cardView = view.findViewById(R.id.toast_cardView);
        TextView tvMessage = view.findViewById(R.id.tvMessage);
        tvMessage.setText(message);

        if (alertType==SUCCESS_ALERT){
            animationView.setAnimation(R.raw.success);
            cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.green2));
        }else if (alertType==FAIL_ALERT){
            animationView.setAnimation(R.raw.error);
            cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.red));
        }else if (alertType==INFO_ALERT){
            animationView.setAnimation(R.raw.info);
            cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.orange));
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();


//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onDestroy() {

        hideProgress();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkChangeReceiver == null ? networkChangeReceiver = new NetworkChangeReceiver() : networkChangeReceiver, intentFilter);
    }


    @Override
    protected void onStop() {
        if (networkChangeReceiver != null)
            unregisterReceiver(networkChangeReceiver);
        super.onStop();
    }



    private void updateUserStatus() {
        runOnUiThread(() -> {
            startActivity(new Intent(context, SplashActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finishAffinity();
        });
    }


    public void checkUserState(User user) {
        if (preference.isFirst()) {
            startActivity(new Intent(context, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finishAffinity();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            return;
        }

        if (user == null) {
            startActivity(new Intent(context, LoginActivity.class));
            finishAffinity();
            return;
        }


        if (preference.getUser() == null) {
            preference.setUser(user);
        }

        if (TextUtils.isEmpty(user.getToken())) {
            User user1 = preference.getUser();
            user.setToken(user1.getToken());
        }
        preference.setUser(user);

        startActivity(new Intent(context, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finishAffinity();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /*@Override
    public void logout() {
        LogoutDialogFragment dialog = new LogoutDialogFragment();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "LogoutDialogFragment");
    }*/

    public boolean checkMyPermission(String permission)
    {
        int res = checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

}
