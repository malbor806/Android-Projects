package com.am.demo.catsandjokes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.b_getCats)
    Button getCatsButton;
    @BindView(R.id.b_getJoke)
    Button getJokeButton;
    @BindView(R.id.tv_showJoke)
    TextView showJokeTextView;
    @BindView(R.id.gv_catsList)
    GridView catGalleryGridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setListners();
    }
}
