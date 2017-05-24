package com.am.demo.catsandjokes.model.jokes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by malbor806 on 24.05.2017.
 */

public class Joke {
    @SerializedName("joke")
    private String joke;

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }
}
