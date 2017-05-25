package com.am.demo.arkanoid2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.b_playWithTap)
    Button playWithTapButton;
    @BindView(R.id.b_playWithAccelerometer)
    Button playWithAccelerometer;
    private static boolean playWithTap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        playWithTapButton.setOnClickListener(v -> playWithTap());
        playWithAccelerometer.setOnClickListener(v -> {
            playWithAccelerometer();
        });
    }

    private void playWithAccelerometer() {
        playWithTap = false;
        start(this);
    }

    private void playWithTap() {
        playWithTap = true;
        start(this);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, PlayActivity.class);
        starter.putExtra("playWithTap", playWithTap);
        context.startActivity(starter);
    }
}
