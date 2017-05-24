package com.am.demo.catsandjokes.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by malbor806 on 24.05.2017.
 */

public interface CatsGalleryAPI {
    @GET("images/get?format=xml&results_per_page={pictureNumber}")
    Call<List<Cat>> getCatsList(@Path("pictureNumber") String pictureNumber);
}
