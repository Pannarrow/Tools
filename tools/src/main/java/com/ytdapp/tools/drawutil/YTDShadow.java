package com.ytdapp.tools.drawutil;

/**
 * 阴影模型
 */
public class YTDShadow {
    private float radius;	//模糊半径（投影长度），越大越模糊
    private float offsetX;	//阴影离开边框的x横向距离
    private float offsetY;	//阴影离开边框的Y横向距离
    private int color;	//阴影颜色

    public YTDShadow(float radius, float dx, float dy, int color) {
        this.radius = radius;
        this.offsetX = dx;
        this.offsetY = dy;
        this.color= color;
    }

    public float getRadius() {
        return radius;
    }
    public float getOffsetX() {
        return offsetX;
    }
    public float getOffsetY() {
        return offsetY;
    }
    public int getColor() {
        return color;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
