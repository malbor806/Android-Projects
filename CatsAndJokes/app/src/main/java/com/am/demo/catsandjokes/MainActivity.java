package com.am.demo.catsandjokes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.am.demo.catsandjokes.adapter.ImageGridViewAdapter;
import com.am.demo.catsandjokes.model.cats.Cat;
import com.am.demo.catsandjokes.model.jokes.Joke;
import com.am.demo.catsandjokes.retrofit.CatController;
import com.am.demo.catsandjokes.retrofit.JokeController;

import java.util.List;

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
    private ImageGridViewAdapter imageGridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        jokeController = new JokeController();
        catController = new CatController();
        setListeners();
    }

    private void setListeners() {
        jokeController.setOnJokeResponseListener(this::showJoke);
        catController.setOnCatResponseListener(this::showCatsPicture);
        downloadCatsButton.setOnClickListener(v -> downloadCats());
        downloadJokeButton.setOnClickListener(v -> downloadJoke());
    }

    private void showCatsPicture(List<Cat> cats) {
        if (cats != null) {
            imageGridViewAdapter = new ImageGridViewAdapter(this, R.layout.grid_view_layout, cats);
            catGalleryGridView.setAdapter(imageGridViewAdapter);
        } else {
            showErrorInformation(getString(R.string.error_something_wrong));
        }
    }

    private void showJoke(Joke joke) {
        if (joke != null) {
            showJokeTextView.setText(Html.fromHtml(joke.getJoke()));
        } else {
            showErrorInformation(getString(R.string.error_something_wrong));
        }
    }

    private void showErrorInformation(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void downloadCats() {
        if (isOnline()) {
            catController.getCats();
        } else {
            showErrorInformation(getString(R.string.error_no_internet));
        }
    }

    private void downloadJoke() {
        if (isOnline()) {
            jokeController.getJokes();
        } else {
            showErrorInformation(getString(R.string.error_no_internet));
        }
    }
}
