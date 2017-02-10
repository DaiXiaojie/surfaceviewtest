package com.daixiaojie.surfaceviewtest2.intonation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.DecelerateInterpolator;

import com.daixiaojie.surfaceviewtest2.R;
import com.daixiaojie.surfaceviewtest2.SparkManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by daixiaojie on 2017/2/9.
 */

public class IntonationSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    /**
     * 与SurfaceHolder绑定的Canvas
     */
    private Canvas mCanvas;
    /**
     * 用于绘制的线程
     */
    private Thread t;
    /**
     * 线程的控制开关
     */
    private boolean isRunning;

    private Paint mPaint;

    /**
     * 当前View的尺寸
     */
    private int mWidth;
    private int mHeight;
    private RectF areaRect = new RectF();

    /**
     * 背景
     */
    private Bitmap mBg;

    /**
     * 指示器
     */
    private Indicator indicator;
    private Bitmap indBitmap;

    /**
     * 音准线
     */
    private IntonationLine line;
//    private Bitmap lineBitmap;
    private RectF rect;
    private int lineWidth;

    /**
     * 粒子效果
     */
    private SparkManager sparkManager;

    private int minCents;
    private int maxCents;

    private List<IntonationLine> lines;

    private int mSpeed = Util.dp2px(getContext(), 180);

    private List<IntonationLine> mNeedRemovelines = new LinkedList<>();
    private int needRemoveLineCount = 0;

    private long startTime;

    public IntonationSurfaceView(Context context) {
        this(context, null);
    }

    public IntonationSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);

        setZOrderOnTop(true);// 设置画布 背景透明
        mHolder.setFormat(PixelFormat.TRANSLUCENT);

        // 设置可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        // 设置常亮
        this.setKeepScreenOn(true);

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        sparkManager = new SparkManager();

        initBitmaps();
        initLine();
    }

    private void initBitmaps() {
        mBg = loadImageByResId(R.mipmap.bg);
        indBitmap = loadImageByResId(R.mipmap.chart_work_play);
//        lineBitmap = loadImageByResId(R.mipmap.line);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        // 火花数组
        int[][] sparks = new int[400][10];
        startTime = System.currentTimeMillis();
        while (isRunning) {
            long start = System.currentTimeMillis();
            long updateTime = start-startTime;
            logic();
            draw(updateTime, sparks);
            long end = System.currentTimeMillis();
            try {
                if (end - start < 20) {
                    Thread.sleep(20 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void draw(long updateTime, int[][] sparks) {
        try {
            // 获得canvas

            mCanvas = mHolder.lockCanvas();

            if (mCanvas != null) {
                // drawSomething..
                synchronized (mHolder) {
                    drawBg();
                    drawSplitLine();
                    drawline(updateTime);
                    drawIndicator();
                    drawSpark(sparks);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }

    }

    private void drawSplitLine() {
        RectF rect = new RectF(mWidth / 8, getY(), mWidth / 8 + 4, mHeight);
        mCanvas.drawRect(rect, mPaint);
    }

    private void drawSpark(int[][] sparks) {
        // 循环绘制所有火花
        sparkManager.isActive = true;
        for (int[] n : sparks)
        {
            n = sparkManager.drawSpark(mCanvas, mWidth / 8, indicator.getY() + indicator.getmHeight() / 2, n, mWidth);
        }
        sparkManager.isActive = false;
    }

    private void drawline(long updateTime) {
        int i = 0;
        for (IntonationLine line : lines) {
            if (updateTime >= (line.getAppearTime() * 1000)){
               /* if (i == 0)
                System.out.println("jinlaile222：" + line.getX() + "--" + updateTime + "---" + line.getAppearTime() + "--" + mSpeed + "--" + mWidth);*/
                int x = mWidth - (int)((updateTime * 0.001 - line.getAppearTime())*1.0*mSpeed);
                if (i == 0)
                System.out.println("x:" + x + "--" + "time:" + (updateTime * 0.001 - line.getAppearTime()));
                line.setX(x);
//                System.out.println("jinlaile：" + line.getX());
                line.draw(mCanvas, mPaint);
                i++;
            } else {
                break;
            }
        }
    }

    private void drawIndicator() {
        if (indicator.getY() != indicator.getOldY()) {
            indicator.draw(mCanvas);
        }
    }

    private void drawBg() {
        mCanvas.drawBitmap(mBg, null, areaRect, null);
    }

    private void logic() {
        logicLine();
    }

    private void logicLine() {
        for (IntonationLine line : lines) {
            if (line.getX() < -line.getLineWidth()) {
                mNeedRemovelines.add(line);
                needRemoveLineCount++;
                continue;
            }
//            line.setX(line.getX() - (mSpeed / 30));
        }
        lines.removeAll(mNeedRemovelines);
        mNeedRemovelines.clear();
    }

    private void initLine() {
        /*lines.clear();
        lines.add(LineBean.getLineList().get(0));
        mNeedRemovelines.clear();*/
       /* Data data = new Data();
        data.setMax_cents(7100);
        data.setMin_cents(5400);
        List<IntonationLine> temps = new ArrayList<>();
        IntonationLine line = new IntonationLine(getContext(), 1080, 1700, data.getMin_cents(), data.getMax_cents(), mSpeed);
        temps.add(line);
        data.setHeihei(temps);
        lines.addAll(data.getHeihei());*/
//        lines = LineBean.getLineList();
    }

    /**
     * 根据resId加载图片
     *
     * @param resId
     * @return
     */
    private Bitmap loadImageByResId(int resId) {
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        areaRect.set(0, 0, w, h);
        indicator = new Indicator(getContext(), mWidth, mHeight, indBitmap);
        Data data = LineBean.getLineList();
        minCents = data.getCents_min();
        maxCents = data.getCents_max();
        lines = new LinkedList<>();
        System.out.println("speed:" + mSpeed);
        for (IntonationLine line : data.getData()) {
            lines.add(new IntonationLine(mWidth,mHeight,data.getCents_min(), data.getCents_max(), mSpeed, line));
        }
    }

    public void getCurrentCents(int currentCents) {
        if ((minCents > 0) && (maxCents >= minCents)) {
            currentCents = currentCents > maxCents ? maxCents : currentCents;
            currentCents = currentCents < minCents ? minCents : currentCents;
            System.out.println("currentCents:"  + currentCents + "---Y:" + Util.getY(currentCents, mWidth, minCents, maxCents));
            int targetY = Util.getY(currentCents, mHeight, minCents, maxCents);
            targetY = targetY < (int)getY() ? (int)getY() : targetY;
            targetY = targetY > (int)getY() + mHeight - indicator.getmHeight() ? (int)getY() + mHeight - indicator.getmHeight(): targetY;
            System.out.println("targetY:" + targetY);
            int duration = 300;
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(indicator.getY(), targetY);
            valueAnimator.setDuration(duration).start();
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (Float) animation.getAnimatedValue();
                    indicator.setY((int)value);
                }
            });
//            indicator.setY((int)value);
        }
    }
}
