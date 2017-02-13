package com.daixiaojie.surfaceviewtest2;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.daixiaojie.surfaceviewtest2.intonation.IntonationSurfaceView;
import com.daixiaojie.surfaceviewtest2.intonation.LineBean;

import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    public static final long SONG_TIME_COUNT = 200000;

    RelativeLayout relativeLayout;
    private IntonationSurfaceView intonationSurfaceView;
    private TimeCount timeCount;
    private SongPlayTimeCount songPlayTimeCount;
    private long playTimeCount;
    private Button btnStart, btnRestart, btnFinish;
    private SeekBar seekBar;

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
        btnStart = (Button) findViewById(R.id.btn_startorpause_acitvity_main);
        btnRestart = (Button) findViewById(R.id.btn_restart_acitvity_main);
        btnFinish = (Button) findViewById(R.id.btn_finish_acitvity_main);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        btnStart.setOnClickListener(this);
        btnRestart.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
    }

    private void userVoiceCents(boolean isOpen) {
        if (timeCount != null) {
            timeCount.cancel();
        }
        if (isOpen) {
            timeCount = new TimeCount(SONG_TIME_COUNT, 300);
            timeCount.start();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_startorpause_acitvity_main:
                switch (intonationSurfaceView.getmState()) {
                    case IntonationSurfaceView.STATE_INIT:
                        startPlay();
                        break;
                    case IntonationSurfaceView.STATE_RUNNING:
                        pausePlay();
                        break;
                    case IntonationSurfaceView.STATE_PAUSE:
                        resumePlay(SONG_TIME_COUNT - playTimeCount);
                        break;
                    case IntonationSurfaceView.STATE_FINISH:
                        resumePlay(SONG_TIME_COUNT);
                        break;

                }
                break;
            case R.id.btn_restart_acitvity_main:
                restartPlay();
                break;
            case R.id.btn_finish_acitvity_main:
                finishPlay();
                break;
        }
    }

    private void finishPlay() {
        intonationSurfaceView.onFinish();
        playTimeCount = SONG_TIME_COUNT;
        if (songPlayTimeCount!=null) {
            songPlayTimeCount.cancel();
        }
        btnStart.setText("start");
        seekBar.setProgress(100);

        userVoiceCents(false);
    }

    private void restartPlay() {
        if (intonationSurfaceView.getmState() != IntonationSurfaceView.STATE_INIT && intonationSurfaceView.getmState() != IntonationSurfaceView.STATE_IDLE) {
            resumePlay(SONG_TIME_COUNT);
        } else {
            Toast.makeText(this, "还没开始，别点重唱好么！", Toast.LENGTH_SHORT).show();
        }
    }

    private void startPlay() {
        intonationSurfaceView.onStart(LineBean.getLineList());
        songPlayTimeCount = new SongPlayTimeCount(SONG_TIME_COUNT, 10);
        songPlayTimeCount.start();
        btnStart.setText("pause");

        userVoiceCents(true);
    }

    private void resumePlay(long time) {
        intonationSurfaceView.onResume();
        playTimeCount = 0;
        if (songPlayTimeCount != null) {
            songPlayTimeCount.cancel();
        }
        songPlayTimeCount = new SongPlayTimeCount(time, 10);
        songPlayTimeCount.start();
        btnStart.setText("pause");

        userVoiceCents(true);
    }

    private void pausePlay() {
        intonationSurfaceView.onPause();
        if (songPlayTimeCount != null) {
            songPlayTimeCount.cancel();
        }
        btnStart.setText("start");

        userVoiceCents(false);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        pausePlay();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        playTimeCount = seekBar.getProgress() * SONG_TIME_COUNT / 100;
//        intonationSurfaceView.setUpdateTime(playTimeCount);
        resumePlay(SONG_TIME_COUNT - playTimeCount);
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
        int max = 7500;
        int min = 4500;
        Random random = new Random();

        return random.nextInt(max) % (max - min + 1) + min;
    }

    class SongPlayTimeCount extends CountDownTimer {
        public SongPlayTimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {//计时完毕时触发

        }

        @Override
        public void onTick(long millisUntilFinished) {
            playTimeCount = SONG_TIME_COUNT - millisUntilFinished;
            intonationSurfaceView.setUpdateTime(SONG_TIME_COUNT - millisUntilFinished);
            seekBar.setProgress((int) (playTimeCount * 100 / SONG_TIME_COUNT));
        }
    }
}
