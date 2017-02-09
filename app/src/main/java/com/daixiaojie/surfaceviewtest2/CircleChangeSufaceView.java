package com.daixiaojie.surfaceviewtest2;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Date;

/**
 * Created by daixiaojie on 2017/1/31.
 */

public class CircleChangeSufaceView extends SurfaceView implements SurfaceHolder.Callback {
    private LoopThread thread;

    public CircleChangeSufaceView(Context context) {
        super(context);
        init();
    }

    private void init() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        thread = new LoopThread(holder, getContext());
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.isRunning = true;
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class LoopThread extends Thread {
        SurfaceHolder surfaceHolder;
        Context context;
        boolean isRunning;
        Paint paint;
        private int left = -500;
        private int right = -250;
        private int top = 50;
        private int bottom = 100;

        public LoopThread(SurfaceHolder holder, Context context) {
            surfaceHolder = holder;
            this.context = context;

            /*paint = new Paint();
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5.0f);*/
            paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setDither(true);
        }

        @Override
        public void run() {
            Canvas canvas = null;
            Date d = null;
            while (isRunning) {
                d = new Date();
                try {
                    synchronized (surfaceHolder) {
                        canvas = surfaceHolder.lockCanvas();
                        doDraw(canvas);
//                        Thread.sleep(20);
                        Thread.sleep(Math.max(0, 50-(new Date().getTime()- d.getTime())));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private void doDraw(Canvas canvas) {
            canvas.save(Canvas.MATRIX_SAVE_FLAG);
            canvas.drawColor(Color.BLACK);
            canvas.drawRect(300, 100, 600, 400, paint);
//        canvas.drawBitmap(line, null, rect, null);
            canvas.restore();
            /*canvas.drawRoundRect(left, top, right, bottom, 10, 10, paint);
            canvas.drawRoundRect(left+250, top+50, right+250, bottom+50, 10, 10, paint);
            canvas.drawRoundRect(left+500, top+100, right+500, bottom+100, 10, 10, paint);
            canvas.drawRoundRect(left+750, top, right+600, bottom, 10, 10, paint);
            left = left + 10;
            right = right + 10;
            if (left >= ((WindowManager) getContext()
                    .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth()) {
                left=-500;
                right=-250;
            }*/
//            canvas.translate(300, 300);
//            canvas.drawCircle(0, 0, radius++, paint);
//            if (radius >= 200) {
//                radius = 10;
//            }
        }
    }
}
