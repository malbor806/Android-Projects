package com.am.demo.catsandjokes.retrofit;

import com.am.demo.catsandjokes.model.ChuckNorrisJokesAPI;
import com.am.demo.catsandjokes.model.Joke;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by malbor806 on 24.05.2017.
 */

public class JokeController implements Callback<Joke> {
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


    @Override
    public void onResponse(Call<Joke> call, Response<Joke> response) {
        if(response.isSuccessful()) {
            Joke joke = response.body();
            System.out.println(joke.getJoke());
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<Joke> call, Throwable t) {
        t.printStackTrace();
    }
}
