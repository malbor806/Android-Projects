package com.am.demo.arkanoid2.animation;

import android.graphics.RectF;

import java.util.Random;

/**
 * Created by malbor806 on 17.05.2017.
 */

public class Ball {
    private RectF rectF;
    private Random random;
    private float height;
    private float length;
    private float xBallPosition;
    private float yBallPosition;
    private float xVelocity;
    private float yVelocity;

    public Ball(float screenX, float screenY, float paddleLength, float paddleHeight) {
        height = 20;
        length = 20;
        xBallPosition = ((screenX - paddleLength) / 2) + paddleLength / 2;
        yBallPosition = (screenY - paddleHeight) - height;
        xVelocity = 650;
        yVelocity = -350;
        rectF = new RectF(xBallPosition, yBallPosition, xBallPosition + length, yBallPosition + height);
        random = new Random();
    }

    public RectF getRectF() {
        return rectF;
    }

    public float getHeight() {
        return height;
    }

    public float getLength() {
        return length;
    }

    public void resetBallPosition() {
        rectF.left = xBallPosition;
        rectF.top = yBallPosition;
        rectF.right = xBallPosition + length;
        rectF.bottom = yBallPosition + height;
    }

    public void updateBallPosition(float fps) {
        rectF.left = rectF.left + (xVelocity / fps);
        rectF.top = rectF.top + (yVelocity / fps);
        rectF.right = rectF.left + length;
        rectF.bottom = rectF.top + height;
    }

    public void reverseYVelocity() {
        yVelocity = -yVelocity;
    }

    public void reverseXVelocity() {
        xVelocity = -xVelocity;
    }

    public void setRandomXVelocity() {
        xVelocity = 50 + random.nextInt(800);
        int answer = random.nextInt(2);
        if (answer == 0) {
            reverseXVelocity();
        }
    }

    public void clearObstacleY(float y) {
        rectF.bottom = y;
        rectF.top = y - height;
    }

    public void clearObstacleX(float x) {
        rectF.left = x;
        rectF.right = x + length;
    }
}
