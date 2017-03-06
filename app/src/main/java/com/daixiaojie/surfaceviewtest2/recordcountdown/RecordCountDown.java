package com.daixiaojie.surfaceviewtest2.recordcountdown;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import com.daixiaojie.surfaceviewtest2.intonation.Util;

/**
 * Created by daixiaojie on 2017/2/20.
 */

public class RecordCountDown extends View {
    public static final long COUNTDOWN_TIME = 5000; //倒计时总时间，单位ms
    private OnCountDownFinishListener onCountDownFinishListener;
    private PauseTimeCounter timeCounter;
    private int countDownNum = (int) (COUNTDOWN_TIME / 1000);
    private Paint paint;
    private Context context;
    private float width;  //控件宽度
    private float height;  //控件高度
    private int textSize;  //字体大小
    private int textColor;  //字体颜色


    public RecordCountDown(Context context) {
        this(context, null);
    }

    public RecordCountDown(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordCountDown(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //清除flag
        setWillNotDraw(false);
        this.context = context;
        paint = new Paint();
        paint.setFilterBitmap(true);//对位图进行滤波处理
        paint.setDither(true);
        textSize = Util.dp2px(context, 30);
        textColor = Color.WHITE;
    }

    public interface OnCountDownFinishListener {
        void onFinish();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        String text = String.valueOf(countDownNum);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(textSize);
        Rect temp = new Rect();
        paint.getTextBounds(text, 0, text.length(), temp);
        int textWidth = temp.width();
        int textHeight = temp.height();
        paint.setColor(textColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + textHeight / 2, paint);
    }


    public void setOnCountDownFinishListener(OnCountDownFinishListener onCountDownFinishListener) {
        this.onCountDownFinishListener = onCountDownFinishListener;
    }

    class PauseTimeCounter extends CountDownTimer {
        public PauseTimeCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            onCountDownFinishListener.onFinish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            countDownNum--;
            invalidate();
        }
    }

    public void start(long time) {
        if (timeCounter == null) {
            timeCounter = new PauseTimeCounter(COUNTDOWN_TIME, 1000);
        }
    }
}
