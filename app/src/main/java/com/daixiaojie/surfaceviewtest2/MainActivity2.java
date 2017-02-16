package com.daixiaojie.surfaceviewtest2;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.daixiaojie.surfaceviewtest2.playbutton.PlayButton;

/**
 * Created by daixiaojie on 2017/2/16.
 */

public class MainActivity2 extends Activity {
    private PlayButton pbtnControll;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        pbtnControll = (PlayButton) findViewById(R.id.pbtn_activity_main2);
        pbtnControll.setStatus(PlayButton.RecordStatus.DOWNLOADING);
        DownloadTimeCount timeCount = new DownloadTimeCount(120000, 100);
        timeCount.start();
    }


    class DownloadTimeCount extends CountDownTimer {
        public DownloadTimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {//计时完毕时触发

        }

        @Override
        public void onTick(long millisUntilFinished) {
            progress = progress + 1;
            pbtnControll.setProgress(progress);
        }
    }
}
