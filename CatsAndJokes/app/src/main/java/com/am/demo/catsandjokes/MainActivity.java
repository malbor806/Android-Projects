package com.am.demo.catsandjokes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.am.demo.catsandjokes.model.ChuckNorrisJokesAPI;
import com.am.demo.catsandjokes.model.Joke;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.b_getCats)
    Button downloadCatsButton;
    @BindView(R.id.b_getJoke)
    Button downloadJokeButton;
    @BindView(R.id.tv_showJoke)
    TextView showJokeTextView;
    @BindView(R.id.gv_catsList)
    GridView catGalleryGridView;
    private Retrofit retrofit;
    private ChuckNorrisJokesAPI chuck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        retrofit = new Retrofit.Builder().build();
        setListeners();
    }

    private void setListeners() {
        downloadCatsButton.setOnClickListener(v -> downloadCats());
        downloadJokeButton.setOnClickListener(v -> downloadJoke());
    }

    private void downloadCats() {
    }

    private void downloadJoke() {
        chuck = retrofit.create(ChuckNorrisJokesAPI.class);
        Call<Joke> joke = chuck.getJoke();
    }
}
