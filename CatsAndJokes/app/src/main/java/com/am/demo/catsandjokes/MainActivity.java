package com.am.demo.catsandjokes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.am.demo.catsandjokes.model.cats.CatsGalleryAPI;
import com.am.demo.catsandjokes.model.ChuckNorrisJokesAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private Retrofit retrofit2;
    private ChuckNorrisJokesAPI chuck;
    private CatsGalleryAPI cats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setListeners();
    }

    private void setListeners() {
        downloadCatsButton.setOnClickListener(v -> downloadCats());
        downloadJokeButton.setOnClickListener(v -> downloadJoke());
    }

    private void downloadCats() {
    }

    private void downloadJoke() {
    }
}
