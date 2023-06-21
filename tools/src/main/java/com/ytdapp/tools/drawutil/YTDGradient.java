package com.ytdapp.tools.drawutil;

/**
 * 渐变数据模型
 */
public class YTDGradient {

    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private int startColor;
    private int endColor;
    private double angle;

    public YTDGradient(String start, String end, int startColor, int endColor, double angle) {
        if ("left".equals(start)) {
            startX = 0;
            startY = 1;
        } else if ("left_top".equals(start)) {
            startX = 0;
            startY = 0;
        } else if ("top".equals(start)) {
            startX = 1;
            startY = 0;
        } else {
            startX = 2;
            startY = 0;
        }

        if ("right".equals(end)) {
            endX = 2;
            endY = 1;
        } else if ("right_bottom".equals(end)) {
            endX = 2;
            endY = 2;
        } else if ("bottom".equals(end)) {
            endX = 1;
            endY = 2;
        } else {
            endX = 0;
            endY = 2;
        }

        this.startColor = startColor;
        this.endColor = endColor;
        this.angle = angle;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getEndX() {
        return endX;
    }

    public float getEndY() {
        return endY;
    }

    public int getStartColor() {
        return startColor;
    }

    public int getEndColor() {
        return endColor;
    }

    public double getAngle() {
        return angle;
    }

}