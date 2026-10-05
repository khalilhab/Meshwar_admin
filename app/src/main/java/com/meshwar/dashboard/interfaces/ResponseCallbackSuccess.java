package com.meshwar.dashboard.interfaces;


import com.meshwar.dashboard.data.network.model.ApiResponse;

public interface ResponseCallbackSuccess<T> {
    void onResponse(ApiResponse apiResponse, T item);
}
