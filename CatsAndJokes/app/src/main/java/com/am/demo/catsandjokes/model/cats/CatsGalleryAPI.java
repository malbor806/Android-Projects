package com.am.demo.catsandjokes.model.cats;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by malbor806 on 24.05.2017.
 */

public interface CatsGalleryAPI {
    @GET("images/get?format=xml&results_per_page=10")
    Call<CatsResponse> getCatsResponse();
}
