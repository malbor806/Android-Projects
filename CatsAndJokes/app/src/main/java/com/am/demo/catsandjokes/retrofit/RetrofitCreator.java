package com.am.demo.catsandjokes.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by malbor806 on 24.05.2017.
 */

public class RetrofitCreator {
    private static final String JOKES_URL = "https://api.icndb.com/";
    private static final String CATS_URL = "http://thecatapi.com/api/";
    private static RetrofitCreator jokeInstance;
    private static RetrofitCreator catsInstance;
    private Retrofit retrofit;

    private RetrofitCreator(String url) {
        if (url.equals(JOKES_URL)) {
            createJokeRetrofit(url);
        } else {
            createCatsRetrofit(url);
        }
    }

    public static RetrofitCreator getInstance(String instanceName) {
        if (instanceName.equals("JOKES")) {
            return getJokesInstance(JOKES_URL);
        } else if (instanceName.equals("CATS")) {
            return getCatsInstance(CATS_URL);
        }
        return null;
    }

    private static RetrofitCreator getJokesInstance(String url) {
        if (jokeInstance == null) {
            jokeInstance = new RetrofitCreator(url);
        }
        return jokeInstance;
    }

    private static RetrofitCreator getCatsInstance(String url) {
        if (catsInstance == null) {
            catsInstance = new RetrofitCreator(url);
        }
        return catsInstance;
    }

    private void createJokeRetrofit(String url) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private void createCatsRetrofit(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
