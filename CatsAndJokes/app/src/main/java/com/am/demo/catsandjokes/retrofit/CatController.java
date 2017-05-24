package com.am.demo.catsandjokes.retrofit;

import com.am.demo.catsandjokes.model.Cat;
import com.am.demo.catsandjokes.model.CatsGalleryAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by malbor806 on 24.05.2017.
 */

public class CatController {
    private Retrofit retrofit;
    private CatsGalleryAPI cats;

    public CatController() {
        RetrofitBuilder retrofitBuilder = RetrofitBuilder.getInstance("CATS");
        if (retrofitBuilder != null) {
            retrofit = retrofitBuilder.getRetrofit();
            cats = retrofit.create(CatsGalleryAPI.class);
        }
    }

    public void getCats() {
        Call<List<Cat>> catsList = cats.getCatsList("10");
    }
}
