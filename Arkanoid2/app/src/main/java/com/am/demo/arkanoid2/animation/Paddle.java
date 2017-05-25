package com.am.demo.arkanoid2.animation;

import android.graphics.RectF;

/**
 * Created by malbor806 on 17.05.2017.
 */

public class Paddle {
    public static final int STOPPED = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    private RectF rectF;
    private float screenX;
    private float height;
    private float length;
    private float xPositionOnScreen;
    private float yPositionOnScreen;
    private float paddleSpeed;
    private int paddleDirection;

    public Paddle(float screenX, float screenY) {
        height = 30;
        length = 180;
        paddleSpeed = 300;
        this.screenX = screenX;
        paddleDirection = STOPPED;
        xPositionOnScreen = (screenX - length) / 2;
        yPositionOnScreen = screenY - height;
        rectF = new RectF(xPositionOnScreen, yPositionOnScreen, xPositionOnScreen + length, yPositionOnScreen + height);
    }

    public Paddle(float screenX, float screenY, float paddleSpeed) {
        height = 30;
        length = 220;
        this.paddleSpeed = paddleSpeed;
        this.screenX = screenX;
        paddleDirection = STOPPED;
        xPositionOnScreen = (screenX - length) / 2;
        yPositionOnScreen = screenY - height;
        rectF = new RectF(xPositionOnScreen, yPositionOnScreen, xPositionOnScreen + length, yPositionOnScreen + height);
    }

    public RectF getRectF() {
        return rectF;
    }

    public float getLength() {
        return length;
    }

    public float getHeight() {
        return height;
    }

    public void setPaddleDirection(int paddleDirection) {
        this.paddleDirection = paddleDirection;
    }

    public void resetPaddlePosition() {
        rectF.left = (screenX - length) / 2;
        rectF.right = (screenX - length) / 2 + length;
    }

    public void updatePaddlePosition(float fps) {
        if (paddleDirection == LEFT) {
            xPositionOnScreen = isPaddleOutFromLeftSide(fps) ? 0 : (xPositionOnScreen - paddleSpeed / fps);
        }
        if (paddleDirection == RIGHT) {
            xPositionOnScreen = isPaddleOutFromRightSide(fps) ? (screenX - length) : (xPositionOnScreen + paddleSpeed / fps);
        }
        rectF.left = xPositionOnScreen;
        rectF.right = xPositionOnScreen + length;
    }

    private boolean isPaddleOutFromLeftSide(float fps) {
        return (xPositionOnScreen - paddleSpeed / fps) < 0;
    }

    private boolean isPaddleOutFromRightSide(float fps) {
        return (xPositionOnScreen + paddleSpeed / fps) > (screenX - length);
    }
}
