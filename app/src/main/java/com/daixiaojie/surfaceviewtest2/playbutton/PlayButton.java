package com.daixiaojie.surfaceviewtest2.playbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.daixiaojie.surfaceviewtest2.R;

/**
 * Created by daixiaojie on 2017/2/16.
 */

public class PlayButton extends View {
    private static final int DEFAULT_WIDTH = 80;
    private static final float ANGLEUNIT = 3.6f;

    private Context context;
    private Paint paint;

    private float width;  //控件宽度
    private float height;  //控件高度

    private float radius;   //圆形半径
    private float ringDownloadWidth; //圆环下载宽度
    private float ringRecordWidth; //圆环录制宽度
    private int buttonColor;  // 中心控制区颜色
    private int ringPreColor; //进度条预加载颜色
    private int ringProgressColor;  //进度条填充颜色


    private int textSize;  //字体大小
    private int textColor;  //字体颜色

    private int progress; //0-100

    private RecordStatus status = RecordStatus.DOWNLOADING;

    public enum RecordStatus {
        DOWNLOADING, START, RUNNING, PAUSE, FINISH
    }

    public PlayButton(Context context) {
        this(context, null);
    }

    public PlayButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //清除flag
        setWillNotDraw(false);
        this.context = context;
        paint = new Paint();
        paint.setFilterBitmap(true);//对位图进行滤波处理
        paint.setDither(true);
        obtainAttributes(context, attrs);
        /*String heightStr = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");
        String widthStr = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_width");
        if (heightStr.equals(ViewGroup.LayoutParams.MATCH_PARENT + "")) {
        } else if (heightStr.equals(ViewGroup.LayoutParams.WRAP_CONTENT + "")) {
        } else {
            int[] systemAttrs = {android.R.attr.layout_height};
            TypedArray a = context.obtainStyledAttributes(attrs, systemAttrs);
            height = a.getDimensionPixelSize(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            a.recycle();
        }
        if (widthStr.equals(ViewGroup.LayoutParams.MATCH_PARENT + "")) {
        } else if (widthStr.equals(ViewGroup.LayoutParams.WRAP_CONTENT + "")) {
        } else {
            int[] systemAttrs = {android.R.attr.layout_width};
            TypedArray a = context.obtainStyledAttributes(attrs, systemAttrs);
            width = a.getDimensionPixelSize(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            a.recycle();
        }
        width = width > height ? height : width;
        height = height > width ? width : height;
        */
        status = RecordStatus.DOWNLOADING;
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PlayButton);
        ringDownloadWidth = array.getDimension(R.styleable.PlayButton_playbutton_ringdownloadwidth, dp2px(2));
        ringRecordWidth = array.getDimension(R.styleable.PlayButton_playbutton_ringrecordwidth, dp2px(4));
        buttonColor = array.getColor(R.styleable.PlayButton_playbutton_buttoncolor, 0xFC4874);
        ringPreColor = array.getColor(R.styleable.PlayButton_playbutton_ringprecolor, 0x4F5D86);
        ringProgressColor = array.getColor(R.styleable.PlayButton_playbutton_ringprogresscolor, 0xFC4874);
        textSize = array.getColor(R.styleable.PlayButton_playbutton_textsize, sp2px(14));
        textColor = array.getColor(R.styleable.PlayButton_playbutton_textcolor, 0xFFFFFF);
        progress = array.getColor(R.styleable.PlayButton_playbutton_progress, 0);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth() > getHeight() ? getHeight() : getWidth();
        height = getHeight() > getWidth() ? getWidth() : getHeight();
        paint.setAntiAlias(true);
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        radius = width / 2;
        switch (status) {
            case DOWNLOADING:
                drawRing(canvas);
                drawButton(canvas);
                drawText(canvas);
                break;
            case START:
                progress = 0;
                drawButton(canvas);
                drawText(canvas);
                break;
            case RUNNING:
                drawRing(canvas);
                drawButton(canvas);
                break;
            case PAUSE:
                drawRing(canvas);
                drawButton(canvas);
                break;
            case FINISH:

                break;
        }

    }

    private void drawText(Canvas canvas) {
        String text = "";
        if (status == RecordStatus.DOWNLOADING) {
            text = progress + "%";
        } else if (status == RecordStatus.START){
            text = "START";
            paint.setTypeface(Typeface.DEFAULT_BOLD);
        }
        paint.setTextSize(textSize);
        Rect temp = new Rect();
        paint.getTextBounds(text, 0, text.length(), temp);
        int textWidth = temp.width();
        int textHeight = temp.height();
        paint.setColor(textColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + textHeight / 2, paint);
    }

    private void drawButton(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(buttonColor);
        if (status == RecordStatus.DOWNLOADING) {
            canvas.drawCircle(width / 2, height / 2, radius - ringDownloadWidth - dp2px(2), paint);
        } else if (status == RecordStatus.START) {
            canvas.drawCircle(width / 2, height / 2, radius, paint);
        } else if (status == RecordStatus.RUNNING) {
            float btnWidth = width - (ringRecordWidth + dp2px(12)) * 2;
            float btnHeight = btnWidth;
            RectF rect = new RectF(width / 2 - btnWidth / 2, height / 2 - btnHeight / 2, width / 2 + btnWidth / 2, height / 2 + btnHeight / 2);
            canvas.drawRoundRect(rect, dp2px(6), dp2px(6), paint);
        } else if (status == RecordStatus.PAUSE) {
            canvas.drawCircle(width / 2, height / 2, radius - ringRecordWidth - dp2px(2), paint);
        }
    }

    private void drawRing(Canvas canvas) {
        float progressWidth = 0f;
        if (status == RecordStatus.DOWNLOADING) {
            progressWidth = ringDownloadWidth;
        } else if (status == RecordStatus.RUNNING || status == RecordStatus.PAUSE) {
            progressWidth = ringRecordWidth;
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(progressWidth);
        paint.setColor(ringPreColor);
        RectF rect = new RectF(ringDownloadWidth, ringDownloadWidth, width - ringDownloadWidth, height - ringDownloadWidth);
        canvas.drawArc(rect, -90, 360, false, paint);
        paint.setColor(ringProgressColor);
        canvas.drawArc(rect, -90, progress * ANGLEUNIT, false, paint);
    }

    protected int dp2px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected int sp2px(float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    public void setProgress(int progress) {
        if (progress < 0 || progress > 100) {
            return;
        }
        this.progress = progress;
        invalidate();
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
        invalidate();
    }

    public RecordStatus getStatus() {
        return status;
    }
}
