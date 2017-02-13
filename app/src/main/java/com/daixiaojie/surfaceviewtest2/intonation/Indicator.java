package com.daixiaojie.surfaceviewtest2.intonation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by daixiaojie on 2017/2/9.
 */

public class Indicator {
    private static final float IND_POS_HEIGHT = 1 / 2f;
    private static final int IND_SIZE = 15;

    private int x;
    private int y;
    private int oldX;
    private int oldY;
    private int mWidth;
    private int mHeight;
    private Bitmap indBitMap;
    private RectF rect = new RectF();
    private int mIntonationHeight;

    public Indicator(Context context, int intonationX, int intonationyY, int intonationWidth, int intonationHeight, Bitmap bitmap) {
        this.indBitMap = bitmap;
        x = intonationX + intonationWidth / 8 - bitmap.getWidth();
//        y = (int) (intonationHeight * IND_POS_HEIGHT);
        y = intonationX + intonationHeight /2 - bitmap.getHeight()/2;
        mIntonationHeight = intonationHeight;
        mWidth = Util.dp2px(context, IND_SIZE);
        mHeight = (int) (mWidth * 1.0f / bitmap.getWidth() * bitmap.getHeight());
    }

    public void resetHeigt()
    {
        y = (int) (mIntonationHeight * IND_POS_HEIGHT);
    }

    public void draw(Canvas canvas) {
//        float duration = 30;
//        float deltaX = x - oldX;
//        float deltaY = y - oldY;
//        canvas.drawBitmap(indBitMap, x+deltaX*10/duration, y+deltaY*10/duration, null);
        rect.set(x, y, x + mWidth, y + mHeight);
        canvas.drawBitmap(indBitMap, null, rect, null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        oldX = this.x;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        oldY = this.y;
        this.y = y;
    }

    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public int getOldX() {
        return oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }
}
