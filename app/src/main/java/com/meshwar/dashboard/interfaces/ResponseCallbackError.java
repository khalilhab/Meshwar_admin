package com.meshwar.dashboard.interfaces;


import com.meshwar.dashboard.data.network.model.ApiResponse;

public interface ResponseCallbackError {
    void onError(ApiResponse apiResponse);
}
