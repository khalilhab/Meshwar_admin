package com.meshwar.dashboard.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.meshwar.dashboard.ui.activities.noInternet.NoInternetActivity;
import com.meshwar.dashboard.utils.NetworkUtil;


public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = NetworkUtil.getConnectivityStatusString(context);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                openActivity(context);
            } else if (NoInternetActivity.getInstance() != null) {
                NoInternetActivity.getInstance().tryAgain();
            }
        }
    }

    private void openActivity(Context context) {
        context.startActivity(new Intent(context, NoInternetActivity.class));
    }

}
