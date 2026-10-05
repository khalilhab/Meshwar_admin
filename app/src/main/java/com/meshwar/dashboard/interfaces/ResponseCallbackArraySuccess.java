package com.meshwar.dashboard.interfaces;


import com.meshwar.dashboard.data.network.model.ApiResponse;

import java.util.List;

public interface ResponseCallbackArraySuccess<T> {
    void onResponse(ApiResponse apiResponse, List<T> item);
}
