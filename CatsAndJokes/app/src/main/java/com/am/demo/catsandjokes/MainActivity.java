package com.am.demo.catsandjokes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.am.demo.catsandjokes.retrofit.CatController;
import com.am.demo.catsandjokes.retrofit.JokeController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.b_getCats)
    Button downloadCatsButton;
    @BindView(R.id.b_getJoke)
    Button downloadJokeButton;
    @BindView(R.id.tv_showJoke)
    TextView showJokeTextView;
    @BindView(R.id.gv_catsList)
    GridView catGalleryGridView;
    private JokeController jokeController;
    private CatController catController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setListeners();
        jokeController = new JokeController();
        catController = new CatController();
    }

    private void setListeners() {
        downloadCatsButton.setOnClickListener(v -> downloadCats());
        downloadJokeButton.setOnClickListener(v -> downloadJoke());
    }

    private void downloadCats() {
        catController.getCats();
    }

    private void downloadJoke() {
        jokeController.getJokes();
    }
}
