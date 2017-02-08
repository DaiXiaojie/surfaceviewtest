package com.daixiaojie.surfaceviewtest2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new CircleChangeSufaceView(this));
        SparkView sparkView = new SparkView(this);
//        MyStockIndicatorView view = new MyStockIndicatorView(this);
        setContentView(sparkView);
//        setContentView(R.layout.activity_main);
//        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout2);
//        SparkView sparkView = new SparkView(this);

    }
}
