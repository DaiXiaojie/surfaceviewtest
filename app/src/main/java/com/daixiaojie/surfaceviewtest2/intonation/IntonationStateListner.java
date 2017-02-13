package com.daixiaojie.surfaceviewtest2.intonation;

/**
 * Created by daixiaojie on 2017/2/13.
 */

public interface IntonationStateListner {
    /**
     * 录制开始
     * @param data song数据;
     * @param startTime song开始事件戳;
     * */
    public void onStart(Data data, long startTime);
    /**
     * 录制暂停
     * */
    public void onPause();
    /**
     * 录制结束
     * */
    public void onFinish();
    /**
     * 录制恢复
     * @param startTime 恢复时重传startTime;
     * */
    public void onResume(long startTime);
}
