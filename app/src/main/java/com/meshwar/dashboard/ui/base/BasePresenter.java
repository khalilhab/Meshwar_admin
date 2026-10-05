package com.meshwar.dashboard.ui.base;


import static com.meshwar.dashboard.utils.AppSharedConstants.FAIL_ALERT;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.meshwar.dashboard.data.network.ApiHelper;
import com.meshwar.dashboard.data.network.RetrofitClient;
import com.meshwar.dashboard.data.network.model.ApiResponse;
import com.meshwar.dashboard.data.prefs.AppPreferencesHelper;
import com.meshwar.dashboard.interfaces.ResponseCallbackError;
import com.meshwar.dashboard.interfaces.ResponseCallbackSuccess;
import com.meshwar.dashboard.ui.activities.noInternet.NoInternetActivity;
import com.meshwar.dashboard.utils.AppSharedConstants;
import com.meshwar.dashboard.utils.AppSharedMethod;
import com.google.gson.Gson;
import com.meshwar.dashboard.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class BasePresenter {

    public final Context context;
    public final BaseView view;

    public AppPreferencesHelper preference;

    public ApiHelper mService;


    public BasePresenter(Context context, BaseView view) {
        this.context = context;
        this.view = view;
        mService = RetrofitClient.getAPI(context);
        preference = new AppPreferencesHelper();
    }

    public void LogE(String msg) {
        Log.e("ttt", (context != null ? context.getClass().getSimpleName() : "") + " : " + msg);
    }

    public void LogE(String msg, Throwable throwable) {
        Log.e("ttt", (context != null ? context.getClass().getSimpleName() : "") + " : " + msg, throwable);
    }

    public void LogD(String msg) {
        Log.d("ttt", (context != null ? context.getClass().getSimpleName() : "") + " : " + msg);
    }

    public void LogW(String name, Throwable msg) {
        Log.w("ttt", (context != null ? context.getClass().getSimpleName() : "") + " : " + name + msg);
    }

    public final <T> void requestObject(boolean useProgress, Call<ApiResponse> call, final String name, final ResponseCallbackSuccess<T> responseCallbackSuccess, final ResponseCallbackError... responseCallbackErrors) {
        request(useProgress, call, name, null, responseCallbackSuccess, null, responseCallbackErrors);
    }

    public final void requestObject(boolean useProgress, Call<ApiResponse> call, final String name) {
        request(useProgress, call, name, null, null, null);
    }

    public final <T> void requestObject(Call<ApiResponse> call, final String name, final ResponseCallbackSuccess<T> responseCallbackSuccess, final ResponseCallbackError... responseCallbackErrors) {
        request(true, call, name, null, responseCallbackSuccess, null, responseCallbackErrors);
    }

    public final <T> void requestObject(Call<ApiResponse> call, final String name, Class<T> valueType, final ResponseCallbackSuccess<T> responseCallbackSuccess, final ResponseCallbackError... responseCallbackErrors) {
        request(true, call, name, valueType, responseCallbackSuccess, null, responseCallbackErrors);
    }

    public final <T> void requestObject(boolean useProgress, Call<ApiResponse> call, final String name, Class<T> valueType, final ResponseCallbackSuccess<T> responseCallbackSuccess, final ResponseCallbackError... responseCallbackErrors) {
        request(useProgress, call, name, valueType, responseCallbackSuccess, null, responseCallbackErrors);
    }

    public final <T> void requestArray(boolean useProgress, Call<ApiResponse> call, final String name, Class<T> valueType, final ResponseCallbackSuccess<List<T>> responseCallbackArraySuccess, final ResponseCallbackError... responseCallbackErrors) {
        request(useProgress, call, name, valueType, null, responseCallbackArraySuccess, responseCallbackErrors);
    }

    public final <T> void requestArray(Call<ApiResponse> call, final String name, Class<T> valueType, final ResponseCallbackSuccess<List<T>> responseCallbackArraySuccess, final ResponseCallbackError... responseCallbackErrors) {
        request(true, call, name, valueType, null, responseCallbackArraySuccess, responseCallbackErrors);
    }

    public final <T> void request(boolean useProgress, Call<ApiResponse> call, final String name, Class<T> valueType, final ResponseCallbackSuccess<T> responseCallbackSuccess, final ResponseCallbackSuccess<List<T>> responseCallbackArraySuccess, final ResponseCallbackError... responseCallbackErrors) {
//        Log.i(Constants.TAG, "request 0");
        if (!AppSharedMethod.isConnectedToInternet(context)) {
            context.startActivity(new Intent(context, NoInternetActivity.class));
            return;
        }
//        Log.i(Constants.TAG, "request 1");

        if (useProgress)
            view.showProgress();
        else
            view.showProgressTransparent();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull retrofit2.Response<ApiResponse> response) {
//                Log.i(Constants.TAG, "request 2");

                LogD(name + " : onResponse " + response);
                if (useProgress)
                    view.hideProgress();
                else
                    view.hideProgressTransparent();
                ApiResponse res = response.body();

                if (response.isSuccessful()) {


                    if (res == null) {
                        if (responseCallbackErrors.length > 1)
                            responseCallbackErrors[1].onError(null);
                        view.showMessage(R.string.try_again,FAIL_ALERT);
                        LogE(name + " : onResponse " + context.getString(R.string.try_again));
                        return;
                    }


                    if (res.isStatus() && res.getErrors().isEmpty()) {
//                        LogD(name + " : onResponse success => " + new Gson().toJson(res));

                        try {
                            if (responseCallbackArraySuccess != null) {
                                responseCallbackArraySuccess.onResponse(res, res.toObjects(valueType));
                            } else {
                                if (responseCallbackSuccess != null) {
                                    if (valueType == null) {
                                        responseCallbackSuccess.onResponse(res, null);
                                    } else {
                                        responseCallbackSuccess.onResponse(res, res.toObject(valueType));
                                    }
                                }
                            }
                        } catch (Exception e) {
                            LogE(name + " : onResponse success catch", e);
                        }


                    } else {
                        if (responseCallbackErrors.length > 0)
                            responseCallbackErrors[0].onError(res);
                        view.showMessage(res.getMessage(),FAIL_ALERT);
                        LogE(name + " : onResponse " + res.getMessage());
                        LogE(name + " : onResponse:error => " + new Gson().toJson(res.getErrors()));
                    }
                } else {
                    if (responseCallbackErrors.length > 2)
                        responseCallbackErrors[2].onError(null);
                    LogE(name + " : onResponse  response is not Successful : " + response.message());
                    LogE(name + " : onResponse  response is not Successful :response => " + res);
                    view.showMessage(response.message(),FAIL_ALERT);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
//                Log.i(Constants.TAG, "request 3");

                if (useProgress)
                    view.hideProgress();
                else
                    view.hideProgressTransparent();

                if (responseCallbackErrors.length > 2)
                    responseCallbackErrors[2].onError(null);
                if (!TextUtils.isEmpty(t.getMessage())) {
                    view.showMessage(t.getMessage(),FAIL_ALERT);
                    LogE(name + " : onFailure " + t.getMessage());
                    return;
                }
                view.showMessage(R.string.try_again,FAIL_ALERT);
                LogW("getCourses : onFailure ", t);
            }
        });

    }


    public static String getUserBearerToken() {
        AppPreferencesHelper preference = new AppPreferencesHelper();
        if (preference.getUser() == null) return "";
        if (TextUtils.isEmpty(preference.getUser().getToken())) {
            return "";
        }
        String token = AppSharedConstants.BEARER + preference.getUser().getToken().replace(AppSharedConstants.BEARER, "");
        Log.e(AppSharedConstants.TAG, "getUserBearerToken: " + token );
        return token;
    }




}
