package com.daixiaojie.surfaceviewtest2.intonation;

import java.util.List;

/**
 * Created by daixiaojie on 2017/2/9.
 */

public class Data {
    private int cents_min;
    private int cents_max;
    private List<IntonationLine> data;


    public int getCents_min() {
        return cents_min;
    }

    public void setCents_min(int cents_min) {
        this.cents_min = cents_min;
    }

    public int getCents_max() {
        return cents_max;
    }

    public void setCents_max(int cents_max) {
        this.cents_max = cents_max;
    }

    public List<IntonationLine> getData() {
        return data;
    }

    public void setData(List<IntonationLine> data) {
        this.data = data;
    }
}
