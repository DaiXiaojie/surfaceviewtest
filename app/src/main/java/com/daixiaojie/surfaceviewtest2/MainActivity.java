package com.daixiaojie.surfaceviewtest2;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.RelativeLayout;

import com.daixiaojie.surfaceviewtest2.intonation.IntonationSurfaceView;

import java.util.Random;

public class MainActivity extends Activity{
    RelativeLayout relativeLayout;
    private IntonationSurfaceView intonationSurfaceView;
    private TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// /*    setContentView(new CircleChangeSufaceView(this));
//        SparkView sparkView = new SparkView(this);
//        MyStockIndicatorView view = new MyStockIndicatorView(this);
//        setContentView(sparkView);
//        setContentView(R.layout.activity_main);
//        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout2);
//        SparkView sparkView = new SparkView(this);*/
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);*/
//        intonationSurfaceView = new IntonationSurfaceView(this);
//        setContentView(intonationSurfaceView);
        setContentView(R.layout.activity_main);
        intonationSurfaceView = (IntonationSurfaceView) findViewById(R.id.intonationview);
        timeCount = new TimeCount(500000, 300);
        timeCount.start();
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {//计时完毕时触发

        }

        @Override
        public void onTick(long millisUntilFinished) {
            int cents = getRandom();
            intonationSurfaceView.getCurrentCents(cents);
        }
    }

    public int getRandom() {
        int max=7500;
        int min=4500;
        Random random = new Random();

        return random.nextInt(max)%(max-min+1) + min;
    }
}
