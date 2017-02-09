package com.daixiaojie.surfaceviewtest2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.daixiaojie.surfaceviewtest2.intonation.IntonationSurfaceView;

public class MainActivity extends Activity{
    RelativeLayout relativeLayout;
    private IntonationSurfaceView intonationSurfaceView;

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        intonationSurfaceView = new IntonationSurfaceView(this);
        setContentView(intonationSurfaceView);

    }
}
