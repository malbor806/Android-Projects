package com.am.demo.catsandjokes.retrofit;

import okhttp3.OkHttpClient;

/**
 * Created by malbor806 on 24.05.2017.
 */

public class ApiManager {
    private OkHttpClient okHttpClient;

    public ApiManager() {
        okHttpClient = new OkHttpClient();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
