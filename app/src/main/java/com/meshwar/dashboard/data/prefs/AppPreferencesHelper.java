package com.meshwar.dashboard.data.prefs;

import static android.content.Context.MODE_PRIVATE;

import static com.meshwar.dashboard.utils.App.getInstance;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.meshwar.dashboard.data.network.model.User;
import com.meshwar.dashboard.interfaces.FCMToken;
import com.meshwar.dashboard.utils.App;
import com.meshwar.dashboard.utils.LocaleManager;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

public class AppPreferencesHelper {

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;
    private static final String USER = "USER";
    private static final String LOCAL = "LOCAL";
    private static final String IS_FIRST = "IS_FIRST";
    private static final String ALLOW_NOTIFICATIONS = "ALLOW_NOTIFICATIONS";
    private static final String READ_NOTIFICATIONS = "READ_NOTIFICATIONS";
    private static final String NOTIFICATIONS_COUNT = "NOTIFICATIONS_COUNT";
    private final String userTokenKey = "userTokenKey";
    private static final String FCM_TOKEN = "FCM_TOKEN";
    private static final String IS_AVAILABLE = "IS_AVAILABLE";
    private static final String IS_WELCOME_PAGE_SHOWN = "IS_WELCOME_PAGE_SHOWN";
    private static final String IS_INTRO_PAGE_SHOWN = "IS_INTRO_PAGE_SHOWN";
    private static final String UN_PAID_COMMISSION = "UN_PAID_COMMISSION";
    private static final String CURRENT_ORDER_UUID = "CURRENT_ORDER_UUID";
    private static final String CURRENT_ORDER_STEP = "CURRENT_ORDER_STEP";

    public static final String SHARED_APP_DATA = "app_data";
    private static final String SHARED_IS_APP_OPENED_BEFORE = "is_app_opened_before";


    public AppPreferencesHelper() {
        preferences = getInstance().getSharedPreferences("com.khalil.meshwar_dashboard", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();
    }

    public static boolean isOpenBeforeThat() {
        return App.getInstance().getSharedPreferences(SHARED_APP_DATA, MODE_PRIVATE)
                .getBoolean(SHARED_IS_APP_OPENED_BEFORE, false);
    }

    public static void setOpenBeforeThat(boolean open) {
        App.getInstance().getSharedPreferences(SHARED_APP_DATA, MODE_PRIVATE).edit()
                .putBoolean(SHARED_IS_APP_OPENED_BEFORE, open).apply();
    }

    public void setUser(User user) {
        if (user == null) setUserToken("");
        else if (!TextUtils.isEmpty(user.getToken())) setUserToken(user.getToken());


        String jsonUser = new Gson().toJson(user);
        editor.putString(USER, jsonUser).apply();
    }


    public void setUserToken(String token) {
        editor.putString(userTokenKey, token).apply();
    }

    public String getUserToken() {
        return preferences.getString(userTokenKey, null);
    }

    public User getUser() {
        return new Gson().fromJson(preferences.getString(USER, ""), User.class);
    }


    public void setLocal(String local) {
        editor.putString(LOCAL, local);
        editor.apply();
    }

    public String getLocal() {
        return preferences.getString(LOCAL, LocaleManager.ARABIC);
    }

    public void setIsFirst() {
        editor.putBoolean(IS_FIRST, false);
        editor.apply();
    }

    public boolean isFirst() {
        return preferences.getBoolean(IS_FIRST, true);
    }

    public void setIsIntroPageShown(boolean isShown) {
        editor.putBoolean(IS_INTRO_PAGE_SHOWN, isShown);
        editor.apply();
    }
    public boolean isIntroPageShown() {
        return preferences.getBoolean(IS_INTRO_PAGE_SHOWN, false);
    }

    public void setIsAvailable(boolean isAvailable) {
        editor.putBoolean(IS_WELCOME_PAGE_SHOWN, isAvailable);
        editor.apply();
    }

    public boolean isAvailable() {
        return preferences.getBoolean(IS_AVAILABLE, false);
    }

    public void setAllowNotifications(boolean isAllow) {
        editor.putBoolean(ALLOW_NOTIFICATIONS, isAllow);
        editor.apply();
    }

    public boolean isAllowNotifications() {
        return preferences.getBoolean(ALLOW_NOTIFICATIONS, true);
    }

    public void setReadNotifications(boolean readNotifications) {
        editor.putBoolean(READ_NOTIFICATIONS, readNotifications);
        editor.apply();
    }

    public boolean isReadNotifications() {
        return preferences.getBoolean(READ_NOTIFICATIONS, true);
    }

    public void setNotificationsCount(int notificationsCount) {
        editor.putInt(NOTIFICATIONS_COUNT, notificationsCount);
        editor.apply();
    }

    public int getNotificationsCount() {
        return preferences.getInt(NOTIFICATIONS_COUNT, 0);
    }


    public boolean isLogin() {
        return getUser() != null;
    }

    public void setFcmToken(String fcmToken) {
        editor.putString(FCM_TOKEN, fcmToken).apply();
    }

    public void getFcmToken(FCMToken fcmToken) {
//        String token = preferences.getString(FCM_TOKEN, "");
//        if (!TextUtils.isEmpty(token)) {
//            Log.e("ttt", "getFcmToken");
//            fcmToken.onGenerateToken(token, null);
//            return;
//        }
//
//        Log.e("ttt", "getFcmToken:updateToken");
        updateToken(getInstance(), fcmToken);
    }


    public void updateToken(Context context, FCMToken fcmToken) {
        Log.e("ttt", "updateToken");
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful() && !TextUtils.isEmpty(task.getResult())) {
                Log.e("ttt", "updateToken token : " + task.getResult());
                setFcmToken(task.getResult());
                if (fcmToken != null) {
                    fcmToken.onGenerateToken(task.getResult(), null);
                }
                return;
            }

            if (task.getException() != null) {
                Log.e("ttt", "updateToken Error", task.getException());
                if (TextUtils.isEmpty(task.getException().getMessage())) {
                    if (fcmToken != null) {
                        fcmToken.onGenerateToken(null, "Error");
                    }
                } else {
                    if (fcmToken != null) {
                        fcmToken.onGenerateToken(null, task.getException().getMessage());
                    }
                }
            }
        });
    }

}
