package com.meshwar.dashboard.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.meshwar.dashboard.data.prefs.AppPreferencesHelper;
import com.meshwar.dashboard.ui.activities.splash.view.SplashActivity;
import com.meshwar.dashboard.ui.activities.supportChat.view.ChatSupportActivity;
import com.meshwar.dashboard.utils.AppSharedConstants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.meshwar.dashboard.R;

import java.util.Objects;

public class MyFireBaseMessaging extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String fcmToken) {
        super.onNewToken(fcmToken);

        updateFCMTokenToServer(fcmToken);
    }

    private void updateFCMTokenToServer(String fcmToken) {
        new AppPreferencesHelper().setFcmToken(fcmToken);
        Log.d(AppSharedConstants.TAG, "fcmToken => updateFCMTokenToServer : " + fcmToken);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        AppPreferencesHelper preference = new AppPreferencesHelper();

//        if (MainActivity.view != null) {
//            MainActivity.view.getNotificationCount();
//        }

        Log.i(AppSharedConstants.TAG, "onMessageReceived : 0 " + new Gson().toJson(remoteMessage.getData()));

        if (remoteMessage.getData().size() > 0) {

            String uuid = remoteMessage.getData().get("uuid");
            String title = remoteMessage.getData().get("title");
            String message = remoteMessage.getData().get("body");
            String type = remoteMessage.getData().get("type");

            Log.i(AppSharedConstants.TAG, "onMessageReceived : isAllowNotifications " + preference.isAllowNotifications());

            if (TextUtils.isEmpty(type)) {
                return;
            }

            switch (Objects.requireNonNull(type)) {


                case AppSharedConstants.Notifications.SEND_MSG: {
                    if (ChatSupportActivity.updateStatus != null) {
                        ChatSupportActivity.updateStatus.onComplete(true);
                        return;
                    }
                }


            }

            if (!preference.isAllowNotifications()) {
                return;
            }


            Intent intent = new Intent(this, SplashActivity.class);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sendNotificationAPI26(MyFireBaseMessaging.this, title, message, intent);
            } else {
                sendNotifications(MyFireBaseMessaging.this, title, message, intent);
            }
        }
    }

    @SuppressLint("InlinedApi")
    public static void sendNotifications(Context context, String title, String message, Intent intent) {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(message).setAutoCancel(true).setSound(defaultSoundUri);


       /* Notification build = builder.build();
        build.sound = Uri.parse("android.resource://"
                + context.getPackageName() + "/" + R.raw.new_order);*/


        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent notification = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(notification);
        }

        builder.setPriority(NotificationManager.IMPORTANCE_HIGH | NotificationManager.IMPORTANCE_MAX);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());

    }

    @SuppressLint("UnspecifiedImmutableFlag")
    public static void sendNotificationAPI26(Context context, String title, String message, Intent intent) {
        NotificationHelper helper;
        Notification.Builder builder;
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        helper = new NotificationHelper(context);
        builder = helper.getNotification(title, message, defaultSoundUri);

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            } else {
                pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            }
            builder.setContentIntent(pendingIntent);
        }
        helper.getManager().notify(1, builder.build());
    }

}
