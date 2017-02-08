package com.daixiaojie.surfaceviewtest2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by daixiaojie on 2017/1/31.
 */

public class CircleChangeView extends View {
    private int radius = 10;
    private Paint paint;

    public CircleChangeView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(300, 300);
        canvas.drawCircle(0, 0, radius++, paint);

        if (radius >= 200) {
            radius = 10;
        }

        invalidate();
    }
}
