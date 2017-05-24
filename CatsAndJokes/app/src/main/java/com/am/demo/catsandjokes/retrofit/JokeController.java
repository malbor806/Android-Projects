package com.am.demo.catsandjokes.retrofit;

import com.am.demo.catsandjokes.model.jokes.ChuckNorrisJokesAPI;
import com.am.demo.catsandjokes.model.jokes.JokeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by malbor806 on 24.05.2017.
 */

public class JokeController implements Callback<JokeResponse> {
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
        Call<JokeResponse> joke = chuck.getJoke();
        joke.enqueue(this);
    }


    @Override
    public void onResponse(Call<JokeResponse> call, Response<JokeResponse> response) {
        if(response.isSuccessful()) {
            JokeResponse joke = response.body();
            System.out.println(joke.getJoke().getJoke());
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<JokeResponse> call, Throwable t) {
        t.printStackTrace();
    }
}
