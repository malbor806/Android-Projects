package com.am.demo.catsandjokes.retrofit;


import retrofit2.Retrofit;

/**
 * Created by malbor806 on 24.05.2017.
 */

public class RetrofitBuilder {
    private static final String JOKES_URL = "https://api.icndb.com";
    private static final String CATS_URL = "http://thecatapi.com";
    private static RetrofitBuilder jokeInstance;
    private static RetrofitBuilder catsInstance;


    private RetrofitBuilder(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();

    }

    public static RetrofitBuilder getInstance(String instanceName) {
        if (instanceName.equals("JOKES")) {
            return getJokesInstance(JOKES_URL);
        } else if (instanceName.equals("CATS")) {
            return getCatsInstance(CATS_URL);
        }
        return null;
    }

    private static RetrofitBuilder getJokesInstance(String url) {
        if (jokeInstance == null) {
            jokeInstance = new RetrofitBuilder(url);
        }
        return jokeInstance;
    }

    private static RetrofitBuilder getCatsInstance(String url) {
        if (catsInstance == null) {
            catsInstance = new RetrofitBuilder(url);
        }
        return catsInstance;
    }


}
