package com.meshwar.dashboard.data.network;

import android.content.Context;

import com.meshwar.dashboard.data.prefs.AppPreferencesHelper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    /*val pInfo =
            context.packageManager?.getPackageInfo(context.packageName, 0)
    val userAgent =
            "okhttp:" + " " + "version app: ${pInfo?.versionCode} (" + pInfo?.versionName + ")," + " language: $lng ," + "From: Android, device info: " + " ${
            System.getProperty(
            "http.agent"
            )
} "*/

    public static final String BASE_URL_MEDIA = "https://meshwarsv2.meshwars.net";
    private static final String BASE_URL = BASE_URL_MEDIA + "/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .addNetworkInterceptor(chain -> {
                                Request.Builder builder = chain.request().newBuilder();

                                builder.addHeader("Accept", "application/json");
                                builder.addHeader("Accept-Language", getLanguage());
                                Request request = builder.build();

                                return chain.proceed(request);
                            })
                            .readTimeout(60, TimeUnit.SECONDS)
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .build())
                    .build();

        }
        return retrofit;
    }

    private static String getLanguage() {
        return new AppPreferencesHelper().getLocal();
    }

    public static ApiHelper getAPI(Context context) {
        return RetrofitClient.getClient(context).create(ApiHelper.class);
    }

}
