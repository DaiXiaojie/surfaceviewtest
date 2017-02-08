package com.daixiaojie.surfaceviewtest2;

/**
 * Created by daixiaojie on 2017/2/8.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MyStockIndicatorView extends SurfaceView implements Callback {
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private float currentX;

    public MyStockIndicatorView(Context context) {
        super(context);
        // 初始化SurfaceHolder
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);

        // 让整个界面透明
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        setZOrderOnTop(true);

        //初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        //设置界面可以点击
        setFocusable(true);
    }

    //触屏事件，每次响应事件后改变坐标值，然后重新绘制

    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();
        int x = (int) event.getX();
        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                currentX = x;
                paintIndicator();
                break;
            case MotionEvent.ACTION_UP:
                clearCavas();
                break;
            case MotionEvent.ACTION_MOVE:
//                currentX;
                paintIndicator();
                break;
            case MotionEvent.ACTION_CANCEL:
                clearCavas();
                break;
        }

        return true;
    }

    //画直线

    private void paintIndicator() {
        Canvas canvas = surfaceHolder.lockCanvas();
        //下面两句用来改变原点、同时把默认的坐标系转换成笛卡尔坐标系
        //canvas.translate(chartLeft, getHeight());
        //canvas.scale(1, -1);
        canvas.drawLine(currentX, 0, currentX, getHeight(), paint);

        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    //清屏

    private void clearCavas() {
        //每次绘制前要锁定画布
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
        //绘制完成后解锁画布
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //在此处初始化数据
//        initData();
        currentX = 0;
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}