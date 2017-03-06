package com.daixiaojie.surfaceviewtest2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import com.daixiaojie.surfaceviewtest2.intonation.Util;


/**
 * Created by daixiaojie on 2017/2/21.
 */

/**
 * 录制暂停恢复数字倒计时点指示器
 */
public class RecordCountDownPoint extends View {
    public static final long COUNTDOWN_TIME = 5000; //倒计时总时间，单位ms
    private OnCountDownFinishListener onCountDownFinishListener;
    private PauseTimeCounter timeCounter;
    private int countDownNum = 0;
    private Paint paint;
    private Context context;
    private float width;  //控件宽度
    private float height;  //控件高度
    private Status status = Status.START;

    private int pointColor = 0xFFFC4874;
    private int radius;
    private int stroke;

    public enum Status {
        START, RUNNING, FINISH
    }

    /**
     *  预先暴露倒计时回调接口，便于业务扩展
     */
    public interface OnCountDownFinishListener {
        void onFinish();
    }

    public RecordCountDownPoint(Context context) {
        this(context, null);
    }

    public RecordCountDownPoint(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordCountDownPoint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //清除flag
        setWillNotDraw(false);
        this.context = context;
        paint = new Paint();
        paint.setFilterBitmap(true);//对位图进行滤波处理
        paint.setDither(true);
        radius = Util.dp2px(context, 5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        switch (status) {
            case RUNNING:
                drawPoint(canvas);
                break;
        }
    }

    private void drawPoint(Canvas canvas) {
        paint.setColor(pointColor);
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < countDownNum; i++) {
//            canvas.drawCircle(radius + radius * 2 * i + stroke * i, radius, radius, paint);
            RectF rect = new RectF(radius * 2 * i + stroke * i, 0, radius * 2 * i + stroke * i + radius * 2, radius * 2);
            System.out.println("rect:" + rect.left + "--" + rect.right +
                    "--" +rect.top +
                    "--" +rect.bottom +
                    "--" +radius+
                    "--" + width +
                    "--" + height);
            canvas.drawRoundRect(rect, radius, radius, paint);
        }
    }

    class PauseTimeCounter extends CountDownTimer {
        public PauseTimeCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (onCountDownFinishListener != null) {
                onCountDownFinishListener.onFinish();
            }
            status =  Status.FINISH;
            invalidate();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            countDownNum--;
            invalidate();
        }
    }


    public void show(long duration) {
        status = Status.RUNNING;
        if (duration <= 0) {
            duration = COUNTDOWN_TIME;

        }
        countDownNum = (int) (duration / 1000);
        stroke = (getWidth() - (countDownNum - 1) * radius * 2) / (countDownNum - 2);
        if (timeCounter != null) {
            timeCounter.cancel();
        }
        timeCounter = new PauseTimeCounter(duration, 1000);
        timeCounter.start();
    }


}
