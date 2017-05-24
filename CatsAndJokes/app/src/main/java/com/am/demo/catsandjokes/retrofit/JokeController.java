package com.am.demo.catsandjokes.retrofit;

import com.am.demo.catsandjokes.model.ChuckNorrisJokesAPI;
import com.am.demo.catsandjokes.model.Joke;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by malbor806 on 24.05.2017.
 */

public class JokeController {
    private Retrofit retrofit;
    private ChuckNorrisJokesAPI chuck;

    public JokeController() {
        RetrofitBuilder retrofitBuilder = RetrofitBuilder.getInstance("JOKES");
        if (retrofitBuilder != null) {
            retrofit = retrofitBuilder.getRetrofit();
            chuck = retrofit.create(ChuckNorrisJokesAPI.class);
        }
    }

    public void getJokes() {
        Call<Joke> joke = chuck.getJoke();
    }


}
