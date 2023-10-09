package com.ytdapp.tools;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

/**
 * @Description 创建Drawable
 * @Author hongpan
 * @Date 2023/08/30 周三 16:50
 */
public class YTDDrawableUtils {

    public static Drawable getCircleDrawable(Context context, String colorStr) {
        int radius = (int)context.getResources().getDimension(R.dimen.dp_10);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setColor(YTDParseUtil.parseColor(colorStr));
        drawable.setSize(radius * 2, radius * 2);
        return drawable;
    }

    public static Drawable getRadiusDrawable(float radius, int colorStr) {
        return getRadiusDrawable(new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, colorStr);
    }

    public static Drawable getRadiusDrawable(float[] radii, int colorStr) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadii(radii);
        drawable.setColor(colorStr);
        return drawable;

    }

    public static Drawable getRadiusDrawable(float radius, int colorStr, int strokeColor, int strokeWidth) {
        return getRadiusDrawable( new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, colorStr, strokeColor, strokeWidth);
    }

    public static Drawable getRadiusDrawable(float[] radii, int colorStr, int strokeColor, int strokeWidth) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadii(radii);
        drawable.setStroke(strokeColor, strokeWidth);
        drawable.setColor(colorStr);
        return drawable;
    }
}
