package com.am.demo.catsandjokes.model.jokes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by malbor806 on 24.05.2017.
 */

public class JokeResponse {
    @SerializedName("value")
    private Joke joke;

    public Joke getJoke() {
        return joke;
    }

    public void setJoke(Joke joke) {
        this.joke = joke;
    }
}
