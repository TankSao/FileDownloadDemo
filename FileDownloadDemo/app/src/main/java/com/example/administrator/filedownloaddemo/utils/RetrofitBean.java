package com.example.administrator.filedownloaddemo.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/10/16.
 */

public class RetrofitBean {
    private static DownloadService service;
    private static final String API_HOST = "http://api.u-launcher.com";
    private static final int DEFAULT_TIMEOUT = 6;
    public static DownloadService getDownloadApi() {
        service = null;
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit build = new Retrofit.Builder()
                .baseUrl(API_HOST)
                .client(client)
                .build();
        service  = build.create(DownloadService.class);
        return  service;
    }
}
