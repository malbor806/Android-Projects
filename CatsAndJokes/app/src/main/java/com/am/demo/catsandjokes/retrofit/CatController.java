package com.am.demo.catsandjokes.retrofit;

import com.am.demo.catsandjokes.model.cats.Cat;
import com.am.demo.catsandjokes.model.cats.CatsGalleryAPI;
import com.am.demo.catsandjokes.model.cats.CatsResponse;
import com.am.demo.catsandjokes.model.cats.OnCatResponseListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by malbor806 on 24.05.2017.
 */

public class CatController implements Callback<CatsResponse> {
    private static final String KEY_INSTANCE = "CATS";
    private CatsGalleryAPI cats;
    private OnCatResponseListener onCatResponseListener;

    public CatController() {
        RetrofitCreator retrofitCreator = RetrofitCreator.getInstance(KEY_INSTANCE);
        if (retrofitCreator != null) {
            Retrofit retrofit = retrofitCreator.getRetrofit();
            cats = retrofit.create(CatsGalleryAPI.class);
        }
    }

    public void getCats() {
        Call<CatsResponse> catsList = cats.getCatsResponse();
        catsList.enqueue(this);
    }

    @Override
    public void onResponse(Call<CatsResponse> call, Response<CatsResponse> response) {
        if (response.isSuccessful()) {
            CatsResponse catsResponse = response.body();
            List<Cat> cats = catsResponse.getCatsDataImages().getCatsImages().getImagesList();
            onCatResponseListener.onCatResponse(cats);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<CatsResponse> call, Throwable t) {
        t.printStackTrace();
    }

    public void setOnCatResponseListener(OnCatResponseListener onCatResponseListener) {
        this.onCatResponseListener = onCatResponseListener;
    }
}
