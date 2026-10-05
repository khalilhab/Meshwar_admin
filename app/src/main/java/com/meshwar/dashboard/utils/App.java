package com.meshwar.dashboard.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context instance;

    public static Location location;

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        AppSharedMethod.getToken();
    }

    public static Context getInstance() {
        return instance;
    }

    public static void setInstance(Context instance) {
        App.instance = instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        setInstance(base);
        super.attachBaseContext(LocaleManager.setNewLocale(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setNewLocale(this);
    }
}
