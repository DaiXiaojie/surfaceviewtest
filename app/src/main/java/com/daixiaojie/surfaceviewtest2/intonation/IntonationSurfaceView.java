package com.daixiaojie.surfaceviewtest2.intonation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
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
    public static final int STATE_IDLE = -1;
    public static final int STATE_INIT = 0;
    public static final int STATE_RUNNING = 1;
    public static final int STATE_PAUSE = 2;
    public static final int STATE_FINISH = 3;

    private int mState;

    public static final Boolean IS_DEBUG = false;

    private long frameNum;

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

    private Data data;
    private List<IntonationLine> lines;

    private int mSpeed = Util.dp2px(getContext(), 120);

    private List<IntonationLine> mNeedRemovelines = new LinkedList<>();
    private int needRemoveLineCount = 0;

    private long updateTime;

    private int currentCents;

    public IntonationSurfaceView(Context context) {
        this(context, null);
    }

    public IntonationSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_HARDWARE, null);

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
        mPaint.setFilterBitmap(true);//对位图进行滤波处理
        mPaint.setDither(true);

        sparkManager = new SparkManager();

        initBitmaps();
    }

    private void initBitmaps() {
        mBg = loadImageByResId(R.mipmap.test_intonation_bg);
        indBitmap = loadImageByResId(R.mipmap.chart_work_play);
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
        int[][] sparks = new int[100][10];
//        startTime = System.currentTimeMillis();
        while (isRunning) {
            long start = System.currentTimeMillis();
//            long updateTime = start-startTime;
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
            long realEnd = System.currentTimeMillis();
            frameNum = 1000 / (realEnd - start);
            if (IS_DEBUG) {
                Log.i("IntonationFps:", String.valueOf(frameNum));
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
        RectF rect = new RectF(mWidth / 8, 0, mWidth / 8 + 4, mHeight);
        mCanvas.drawRect(rect, mPaint);
    }

    private void drawSpark(int[][] sparks) {
        // 循环绘制所有火花
        sparkManager.isActive = true;
        for (int[] n : sparks) {
            n = sparkManager.drawSpark(mCanvas, mWidth / 8, indicator.getY() + indicator.getmHeight() / 2, n, mWidth / 2);
        }
        sparkManager.isActive = false;
    }

    private void drawline(long updateTime) {
        if (mState == STATE_RUNNING || mState == STATE_PAUSE) {
            for (IntonationLine line : lines) {
                if (updateTime >= (line.getAppearTime() * 1000)) {
                    int x = mWidth - (int) ((updateTime * 0.001 - line.getAppearTime()) * 1.0 * mSpeed);
                    line.setX(x);
//                    boolean ishah = updateTime >= line.getStart_time() && updateTime < (line.getStart_time()+ line.getDuration());
//                    System.out.println("ishaha" + ishah + "--" + updateTime + "--" + line.getStart_time() + "--" + line.getDuration());
                    if (updateTime >= (line.getStart_time() * 1000) && updateTime < ((line.getStart_time() + line.getDuration()) * 1000)) {
                        if (currentCents <= line.getCents() && currentCents >= (line.getCents() - 100)) {
                         /*   System.out.println("updateTime:" + updateTime
                                    + "--startTime:" + line.getStart_time()
                                    + "--duration:" + line.getDuration()
                                    + "--currentCents:" + currentCents
                                    + "--cents:" + line.getCents()
                                    + "--x:" + line.getX()
                                    + "--appear:" + line.getAppearTime()
                                    + "--width:" + mWidth
                                    + "--speed" + mSpeed);
                            System.out.println("准了！！！");*/
                            line.setColor(Color.RED);
                        }

                    } else {
                        if (line.getColor() != Color.RED) {
                            line.setColor(Color.WHITE);
                        }
                    }
//                    mPaint.setColor(Color.WHITE);
                    line.draw(mCanvas, mPaint);
                } else {
                    break;
                }
            }
        }
    }

    private void drawIndicator() {
        if (indicator.getY() != indicator.getOldY()) {
            indicator.draw(mCanvas);
        }
    }

    private void drawBg() {
//        mCanvas.drawBitmap(mBg, null, areaRect, null);
        Paint bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);
        bgPaint.setFilterBitmap(true);//对位图进行滤波处理
        bgPaint.setDither(true);

        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        RectF rect = new RectF(getX() + mWidth / 8, 0, getX() + mWidth, mHeight);
        bgPaint.setColor(0x22000000);
        mCanvas.drawRect(rect, bgPaint);

        //新建一个线性渐变，前两个参数是渐变开始的点坐标，第三四个参数是渐变结束的点的坐标。
        bgPaint.setColor(0xFFFF0000);
        Shader shader = new LinearGradient(getX() + mWidth/8, getY(),getX(),getY(),new int[] {0x99FF0000, 0x22FF0000},null,Shader.TileMode.CLAMP);
        bgPaint.setShader(shader);
        RectF rect2 = new RectF(getX(), 0, getX() + mWidth / 8, mHeight);
        mCanvas.drawRect(rect2, bgPaint);

    }

    private void logic() {
        logicLine();
    }

    private void logicLine() {
        /*if (mState == STATE_RUNNING) {
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
        }*/
        if (mState == STATE_RUNNING) {
            for (IntonationLine line : lines) {
                if (line.getX() < -line.getLineWidth()) {
                    line.setColor(Color.WHITE);
                    continue;
                }
            }

        }
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
        indicator = new Indicator(getContext(), (int) getX(), (int) getY(), mWidth, mHeight, indBitmap);
    }

    public void getCurrentCents(int currentCents) {
        if ((minCents > 0) && (maxCents >= minCents)) {
            this.currentCents = currentCents;
            currentCents = currentCents > maxCents ? maxCents : currentCents;
            currentCents = currentCents < minCents ? minCents : currentCents;
            System.out.println("currentCents:" + currentCents + "---Y:" + Util.getY(currentCents, mWidth, minCents, maxCents));
            int targetY = Util.getY(currentCents, mHeight, minCents, maxCents);
            targetY = targetY < (int) getY() ? (int) getY() : targetY;
            targetY = targetY > (int) getY() + mHeight - indicator.getmHeight() ? (int) getY() + mHeight - indicator.getmHeight() : targetY;
            System.out.println("targetY:" + targetY);
            int duration = 200;
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(indicator.getY(), targetY);
            valueAnimator.setDuration(duration).start();
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (Float) animation.getAnimatedValue();
//                    if (value < (int)getY() + mHeight - indicator.getmHeight() && value > (int)getY()) {
                    System.out.println("value:" + (int) value);
                    indicator.setY((int) value);
//                    }
                }
            });
            valueAnimator.cancel();
//            indicator.setY((int)value);
        }
    }

    public void onStart(Data data) {
        this.data = data;
        mState = STATE_RUNNING;
        minCents = this.data.getCents_min();
        maxCents = this.data.getCents_max();
        if (lines == null) {
            lines = new LinkedList<>();
        } else {
            lines.clear();
        }
        System.out.println("speed:" + mSpeed);
        for (IntonationLine line : this.data.getData()) {
            lines.add(new IntonationLine(mWidth, mHeight, data.getCents_min(), data.getCents_max(), mSpeed, line));
        }
        this.data.getData().clear();
        this.data.getData().addAll(lines);//装载成本地化后的lines数据
    }

    public void onPause() {
        mState = STATE_PAUSE;
    }

    public void onFinish() {
        mState = STATE_FINISH;
    }

    public void onResume() {
        /*int startLineIndex = 0;
        System.out.println("updateTime:" + updateTime);
        for (int i = 0; i < data.getData().size(); i++) {
            long lineDisappearTime = (long)(((mWidth + data.getData().get(i).getLineWidth()) / mSpeed + data.getData().get(i).getAppearTime()) * 1000);
            if (lineDisappearTime > updateTime) {
                startLineIndex = i;
                break;
            }
        }
        lines.clear();
//        lines.addAll(data.getData());
        lines.addAll(data.getData().subList(startLineIndex, data.getData().size()));
        System.out.println("startlineIndex:" + startLineIndex + "-------size:" + lines.size() + "line.1:" + lines.get(0).getStart_time() + "---" + updateTime);*/
        mState = STATE_RUNNING;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getmState() {
        return mState;
    }


}
