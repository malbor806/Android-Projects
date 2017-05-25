package com.am.demo.arkanoid2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.am.demo.arkanoid2.animation.DrawBoard;

public class PlayActivity extends AppCompatActivity {
    private DrawBoard drawBoard;
    public Handler handler;
    private boolean isOrientationLandscape;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isOrientationLandscape = true;
        } else {
            isOrientationLandscape = false;
        }
        Intent intent = getIntent();
        boolean playWithTap = intent.getBooleanExtra("playWithTap", true);
        drawBoard = new DrawBoard(this, playWithTap, isOrientationLandscape);
        handler = new Handler();
        setContentView(drawBoard);
    }

    private void setFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawBoard.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawBoard.pause();
    }
}
