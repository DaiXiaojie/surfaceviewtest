package com.daixiaojie.surfaceviewtest2.intonation;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by daixiaojie on 2017/2/9.
 */

public class IntonationLine {

    private int x;
    private int y;
    private int lineWidth;
    private int lineHeight;
    private double appearTime;

    private int cents;
    private int player;
    private double start_time;
    private String type;
    private double duration;
    private int collected_status;

    private int color;


    public IntonationLine(){

    }

    public IntonationLine(int intonationWidth, int intonationHeight,int minCents, int maxCents, int speed, IntonationLine baseLine) {
        setCents(baseLine.getCents());
        setPlayer(baseLine.getPlayer());
        setStart_time(baseLine.getStart_time());
        setType(baseLine.getType());
        setDuration(baseLine.getDuration());
        setCollected_status(baseLine.getCollected_status());
        x = intonationWidth;
        y = Util.getY(cents,intonationHeight, minCents, maxCents);
        appearTime = start_time - ((intonationWidth * 7.0) / (speed * 8.0));
        this.lineWidth = (int)(duration * speed);
        this.lineHeight = Util.getLineHeight(intonationHeight, minCents, maxCents);
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.drawRect(x, y, x + lineWidth, y + lineHeight, paint);
        canvas.restore();
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getCents() {
        return cents;
    }

    public void setCents(int cents) {
        this.cents = cents;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public double getStart_time() {
        return start_time;
    }

    public void setStart_time(double start_time) {
        this.start_time = start_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getCollected_status() {
        return collected_status;
    }

    public void setCollected_status(int collected_status) {
        this.collected_status = collected_status;
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }

    public double getAppearTime() {
        return appearTime;
    }

    public void setAppearTime(double appearTime) {
        this.appearTime = appearTime;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
