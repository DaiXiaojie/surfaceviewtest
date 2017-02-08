package com.daixiaojie.surfaceviewtest2;

/**
 * Created by daixiaojie on 2017/2/8.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/**
 * http://wallage.blog.163.com/blog/static/173896242010101232220959/
 * @author emmet1988.iteye.com
 *
 */
public class SurfaceViewDraw extends SurfaceView implements Runnable,SurfaceHolder.Callback {

    private Bitmap backgroundBitmap;
    private Bitmap rotateBitmap;
    SurfaceHolder surfaceHolder;

    public SurfaceViewDraw(Context context) {
        super(context);
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        rotateBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.chart_work_play);
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(this).start();
        Log.d("surfaceview", "surfaceCreated");
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Log.d("surfaceview", "surfaceChanged");
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("surfaceview", "surfaceDestroyed");
    }

    @Override
    public void run() {
        Log.d("surfaceview", "run");
        Canvas canvas = null;
        int rotateValue = 0;//旋转角度
        int frameCount = 0;//帧计数器
        while (!Thread.currentThread().isInterrupted()) {
            try {
//              canvas = surfaceHolder.lockCanvas();//获取画布对象(获取整个屏幕的画布)
                canvas = surfaceHolder.lockCanvas(new Rect(10, 10, 240, 250));//获取某个区域的画布
                Paint paint = new Paint();
                Log.d("surfaceview", "rotateValue " +rotateValue+"|frameCount "+frameCount);
                if (frameCount++ < 2) {//仅在第一次绘制时绘制背景
                    /*
                     * 这里为什么设置成＜2，而不是1，是由于SurfaceView本身的双缓冲技术。
                        覆盖刷新其实就是将每次的新的图形绘制到上一帧去，
                        所以如果图像是半透明的，就要考虑重复叠加导致的问题了，
                        而如果是完全不透明的图形则不会有任何问题。
                        背景会在背景图和黑色背景之间来回闪。
                        这个问题其实是源于SurfaceView的双缓冲机制，我理解就是它会缓冲
                        前两帧的图像交替传递给后面的帧用作覆盖，这样由于我们仅在第一帧
                        绘制了背景，第二帧就是无背景状态了，且通过双缓冲机制一直保持下
                        来，解决办法就是改为在前两帧都进行背景绘制。
                     */
                    canvas.drawBitmap(backgroundBitmap, 0, 0, paint);//绘制背景
                }
                //创建矩阵以控制图片的旋转和平移
                Matrix matrix = new Matrix();
                rotateValue += 40;
                matrix.setRotate(rotateValue, rotateBitmap.getWidth()/2, rotateBitmap.getHeight()/2);
//              matrix.postRotate(rotateValue, rotateBitmap.getWidth()/2, rotateBitmap.getHeight()/2);
//              matrix.setTranslate(100, rotateValue);
                if (rotateValue == 360) {
                    rotateValue = 0;
                }
                matrix.setTranslate(80,50);//设置左边距和上边距
                //绘制问号
                Log.d("surfaceview", "canvas "+canvas);
                Log.d("surfaceview", "rotateBitmap "+rotateBitmap);
                Log.d("surfaceview", "matrix "+matrix);
                Log.d("surfaceview", "paint "+paint);
                if (canvas != null) {
                    canvas.drawBitmap(rotateBitmap, matrix, paint);
                    //解锁画布，提交画好的图像
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Log.d("surfaceview", "InterruptedException");
            } finally {
                Log.d("surfaceview", "finally");
            }
        }
    }

}