package com.am.demo.arkanoid2.animation;

import android.graphics.RectF;

/**
 * Created by malbor806 on 17.05.2017.
 */

public class Brick {
    private RectF rect;
    private boolean isVisible;

    public Brick(int row, int column, int width, int height) {
        isVisible = true;
        int padding = 1;
        rect = new RectF(column * width + padding,
                row * height + padding,
                column * width + width - padding,
                row * height + height - padding);
    }

    public RectF getRect() {
        return this.rect;
    }

    public void setInvisible() {
        isVisible = false;
    }

    public boolean getVisibility() {
        return isVisible;
    }
}
