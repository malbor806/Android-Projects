package com.am.demo.arkanoid2.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * Created by malbor806 on 17.05.2017.
 */

public class DrawBoard extends View implements Runnable, SensorEventListener {
    private static int screenHeight;
    private static int screenWidth;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float previousMove;
    private boolean isGamePaused;
    private Thread thread;
    private boolean isPlaying;
    private long timeThisFrame;
    private float fps;
    private Paddle paddle;
    private Ball ball;
    private ArrayList<Brick> bricks;
    private Paint paint;
    private int score;
    private int lives;
    private boolean looseLife;
    private boolean playWithTap;
    private boolean isOrientationLandscape;
    private float whenStopPaddle;
    private float whenMovePaddle;


    public DrawBoard(Context context, boolean playWithTap, boolean isOrientationLandscape) {
        super(context);
        getScreenSize();
        paint = new Paint();
        createAnimationObjects();
        this.playWithTap = playWithTap;
        this.isOrientationLandscape = isOrientationLandscape;
        score = 0;
        lives = 3;
        fps = 100;
        previousMove = 0;
        looseLife = false;
        isGamePaused = true;
        setAccelerometer();
    }

    private void getScreenSize() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

    private void createAnimationObjects() {
        if (playWithTap) {
            paddle = new Paddle(screenWidth, screenHeight);
        } else {
            paddle = new Paddle(screenWidth, screenHeight, 600);
        }
        ball = new Ball(screenWidth, screenHeight, paddle.getLength(), paddle.getHeight());
        createBricks();
    }

    private void createBricks() {
        bricks = new ArrayList<>();
        int brickWidth = screenWidth / 8;
        int brickHeight = screenHeight / 12;
        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 5; row++) {
                bricks.add(new Brick(row, column, brickWidth, brickHeight));
            }
        }
    }

    private void setAccelerometer() {
        if (!playWithTap) {
            sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (isOrientationLandscape) {
                whenStopPaddle = (float) 0.05;
                whenMovePaddle = (float) 0.1;
                lives = 5;
            } else {
                whenStopPaddle = (float) 0.1;
                whenMovePaddle = (float) 0.4;

            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(255, 55, 45, 75);
        drawPaddle(canvas);
        drawBall(canvas);
        drawBricks(canvas);
        drawScoreAndLivesText(canvas);
        checkIsGameEnd(canvas);
    }

    private void drawPaddle(Canvas canvas) {
        paint.setARGB(255, 200, 200, 220);
        canvas.drawRect(paddle.getRectF(), paint);
    }

    private void drawBall(Canvas canvas) {
        paint.setARGB(255, 230, 230, 220);
        canvas.drawRoundRect(ball.getRectF(), 20, 20, paint);
    }

    private void drawBricks(Canvas canvas) {
        paint.setARGB(255, 10, 120, 0);
        for (int i = 0; i < bricks.size(); i++) {
            changeColor(i);
            if (bricks.get(i).getVisibility()) {
                canvas.drawRect(bricks.get(i).getRect(), paint);
            }
        }
    }

    private void changeColor(int i) {
        switch (i % 5) {
            case 0:
                paint.setARGB(255, 50, 85, 135);
                break;
            case 1:
                paint.setARGB(255, 170, 0, 0);
                break;
            case 2:
                paint.setARGB(255, 130, 165, 0);
                break;
            case 3:
                paint.setARGB(255, 50, 225, 145);
                break;
            case 4:
                paint.setARGB(255, 240, 250, 45);
                break;
        }
    }


    private void drawScoreAndLivesText(Canvas canvas) {
        paint.setARGB(255, 255, 255, 255);
        paint.setTextSize(50);
        paint.setFakeBoldText(true);
        canvas.drawText("Score: " + score + "   Lives: " + lives, 10, 50, paint);
    }

    private void checkIsGameEnd(Canvas canvas) {
        if (score == bricks.size() * 10) {
            setEndGameInformation("YOU WIN! :)", canvas);
        }
        if (lives <= 0) {
            setEndGameInformation("YOU LOSE! :(", canvas);
        }
    }

    private void setEndGameInformation(String text, Canvas canvas) {
        paint.setTextSize(80);
        canvas.drawText(text, screenWidth / 4, screenHeight / 2, paint);
    }

    @Override
    public void run() {
        while (isPlaying) {
            while (!looseLife) {
                long startFrameTime = System.currentTimeMillis();
                if (!isGamePaused) {
                    update();
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Handler(Looper.getMainLooper()).post(this::invalidate);

                timeThisFrame = System.currentTimeMillis() - startFrameTime;

                if (timeThisFrame >= 1) {
                    fps = 1000 / (int) timeThisFrame;
                }

            }
        }
    }

    public void update() {
        paddle.updatePaddlePosition(fps);
        ball.updateBallPosition(fps);
        updateBricksState();
        updateBallIfCollideWithPaddle();
        updateIfBallHitBounce();
        endGameIfScreenIsClear();
    }

    private void updateIfBallHitBounce() {
        updateBallIfHitBottomOfScreen();
        updateBallIfHitTopOfScreen();
        updateBallIfHitLeftBounce();
        updateBallIfHitRightBounce();
    }

    private void updateBricksState() {
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).getVisibility()) {
                if (RectF.intersects(bricks.get(i).getRect(), ball.getRectF())) {
                    bricks.get(i).setInvisible();
                    ball.reverseYVelocity();
                    score = score + 10;
                }
            }
        }
    }

    private void updateBallIfCollideWithPaddle() {
        if (RectF.intersects(paddle.getRectF(), ball.getRectF())) {
            ball.setRandomXVelocity();
            ball.reverseYVelocity();
            ball.clearObstacleY(paddle.getRectF().top - 2);
        }
    }

    private void updateBallIfHitBottomOfScreen() {
        if (ball.getRectF().bottom > screenHeight) {
            ball.reverseYVelocity();
            ball.clearObstacleY(screenHeight - 2);
            looseLive();
            if (lives == 0) {
                isGamePaused = true;
            }
        }
    }

    private void looseLive() {
        lives--;
        stopCurrentGame();
    }

    private void stopCurrentGame() {
        looseLife = true;
        ball.resetBallPosition();
        paddle.resetPaddlePosition();
    }

    private void updateBallIfHitTopOfScreen() {
        if (ball.getRectF().top < 0) {
            ball.reverseYVelocity();
            ball.clearObstacleY(ball.getHeight() + 2);
        }
    }

    private void updateBallIfHitLeftBounce() {
        if (ball.getRectF().left < 0) {
            ball.reverseXVelocity();
            ball.clearObstacleX(2);
        }
    }

    private void updateBallIfHitRightBounce() {
        if (ball.getRectF().right > screenWidth - ball.getLength()) {
            ball.reverseXVelocity();
            ball.clearObstacleX((screenWidth - ball.getLength()) - ball.getLength());
        }
    }

    private void endGameIfScreenIsClear() {
        if (score == bricks.size() * 10) {
            isGamePaused = true;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                isGamePaused = false;
                move(event);
                startNewGame();
                startWhenLostLife();
                break;
            case MotionEvent.ACTION_UP:
                if (playWithTap) {
                    paddle.setPaddleDirection(Paddle.STOPPED);
                }
                break;
        }
        return true;
    }

    private void move(MotionEvent event) {
        if (playWithTap) {
            movePaddle(event);
        }
    }

    private void startNewGame() {
        if (lives == 0 || score == bricks.size() * 10) {
            restartGame();
        }
    }

    private void startWhenLostLife() {
        if (looseLife) {
            looseLife = false;
        }
    }

    private void movePaddle(MotionEvent event) {
        if (event.getX() > screenWidth / 2) {
            paddle.setPaddleDirection(Paddle.RIGHT);
        } else {
            paddle.setPaddleDirection(Paddle.LEFT);
        }
    }

    private void restartGame() {
        ball.resetBallPosition();
        paddle.resetPaddlePosition();
        score = 0;
        lives = playWithTap ? 3 : 5;
        createBricks();
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
        if (sensorManager != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void pause() {
        isPlaying = false;
        thread.interrupt();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (!isOrientationLandscape) {
            movePaddle(event.values[0]);
        } else {
            movePaddle(event.values[1]);
        }
    }

    private void movePaddle(float value) {
        float x = (previousMove - value);
        previousMove = value;
        if (x < -whenMovePaddle) {
            paddle.setPaddleDirection(Paddle.LEFT);
        } else if (x > whenMovePaddle) {
            paddle.setPaddleDirection(Paddle.RIGHT);
        } else if (-whenStopPaddle < x && x < whenStopPaddle) {
            paddle.setPaddleDirection(Paddle.STOPPED);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
