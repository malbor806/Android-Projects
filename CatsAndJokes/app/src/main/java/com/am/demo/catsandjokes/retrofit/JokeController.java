package com.am.demo.catsandjokes.retrofit;

import com.am.demo.catsandjokes.model.jokes.ChuckNorrisJokesAPI;
import com.am.demo.catsandjokes.model.jokes.JokeResponse;
import com.am.demo.catsandjokes.model.jokes.OnJokeResponseListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by malbor806 on 24.05.2017.
 */

public class JokeController implements Callback<JokeResponse> {
    private static final String KEY_INSTANCE = "JOKES";
    private ChuckNorrisJokesAPI chuckNorrisJokesAPI;
    private OnJokeResponseListener onJokeResponseListener;

    public JokeController() {
        RetrofitCreator retrofitCreator = RetrofitCreator.getInstance(KEY_INSTANCE);
        if (retrofitCreator != null) {
            Retrofit retrofit = retrofitCreator.getRetrofit();
            chuckNorrisJokesAPI = retrofit.create(ChuckNorrisJokesAPI.class);
        }
    }

    public void getJokes() {
        Call<JokeResponse> joke = chuckNorrisJokesAPI.getJoke();
        joke.enqueue(this);
    }

    @Override
    public void onResponse(Call<JokeResponse> call, Response<JokeResponse> response) {
        if (response.isSuccessful()) {
            JokeResponse joke = response.body();
            onJokeResponseListener.onJokeResponse(joke.getJoke());
            System.out.println(joke.getJoke().getJoke());
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<JokeResponse> call, Throwable t) {
        t.printStackTrace();
    }

    public void setOnJokeResponseListener(OnJokeResponseListener onJokeResponseListener) {
        this.onJokeResponseListener = onJokeResponseListener;
    }
}
