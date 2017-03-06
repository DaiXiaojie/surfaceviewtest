package com.daixiaojie.surfaceviewtest2;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.daixiaojie.surfaceviewtest2.playbutton.PlayButton;

/**
 * Created by daixiaojie on 2017/2/16.
 */

public class MainActivity2 extends Activity implements View.OnClickListener {
    private PlayButton pbtnControll;
    private int progress = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        pbtnControll = (PlayButton) findViewById(R.id.pbtn_activity_main2);
        pbtnControll.setOnClickListener(this);
        pbtnControll.setStatus(PlayButton.RecordStatus.DOWNLOADING);
        DownloadTimeCount timeCount = new DownloadTimeCount(120000, 100);
        timeCount.start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.pbtn_activity_main2:
                if (PlayButton.RecordStatus.RUNNING == pbtnControll.getStatus()) {
                    pbtnControll.setStatus(PlayButton.RecordStatus.PAUSE);
                } else if (PlayButton.RecordStatus.PAUSE == pbtnControll.getStatus()) {
                    pbtnControll.setStatus(PlayButton.RecordStatus.RUNNING);
                } else if (PlayButton.RecordStatus.START == pbtnControll.getStatus()) {
                    pbtnControll.setStatus(PlayButton.RecordStatus.RUNNING);
                }
                break;
        }
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
