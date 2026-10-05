package com.meshwar.dashboard.utils;

import com.meshwar.dashboard.data.prefs.AppPreferencesHelper;

public class DatabaseManager {

    public static final DatabaseManager shared = new DatabaseManager();
    private final AppPreferencesHelper preference;

    private static final String DRIVERS = "Drivers";
    public static final String ONLINE_DRIVERS = "OnlineDrivers";
    private static final String ORDERS = "Orders";


    public DatabaseManager() {
        this.preference = new AppPreferencesHelper();
    }



    /*public void updateLocationOnlineDriver(Map<String, Object> map) {
        if (!preference.isDriver() || !preference.isAvailable()) {
            return;
        }
        FirebaseDatabase.getInstance().getReference().child(ONLINE_DRIVERS).child(preference.getDriver().getUuid()).updateChildren(map);
    }

    public void removeLocationOnlineDriver() {
        if (preference.isDriver()) {
            FirebaseDatabase.getInstance().getReference().child(ONLINE_DRIVERS).child(preference.getDriver().getUuid()).removeValue();
        }
    }*/

}
