package com.am.demo.catsandjokes.model;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by malbor806 on 24.05.2017.
 */

public interface ChuckNorrisJokesAPI {
    @GET("http://www.api.icndb.com/jokes/random")
    Call<Joke> getJoke();
}
