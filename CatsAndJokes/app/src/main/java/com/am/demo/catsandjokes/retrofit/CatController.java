package com.am.demo.catsandjokes.retrofit;

import com.am.demo.catsandjokes.model.cats.Cat;
import com.am.demo.catsandjokes.model.cats.CatsGalleryAPI;
import com.am.demo.catsandjokes.model.cats.CatsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by malbor806 on 24.05.2017.
 */

public class CatController implements Callback<CatsResponse> {
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
        Call<CatsResponse> catsList = cats.getCatsResponse(10);
        catsList.enqueue(this);
    }

    @Override
    public void onResponse(Call<CatsResponse> call, Response<CatsResponse> response) {
        if (response.isSuccessful()) {
            CatsResponse catsResponse = response.body();
            List<Cat> cats = catsResponse.getCatsDataImages().getCatsImages().getImagesList();
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<CatsResponse> call, Throwable t) {
        t.printStackTrace();
    }
}
