package com.ytdapp.tools.drawutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

public class YTDDrawUtils {

    /**
     * 绘制阴影bitmap
     * @param shadow
     * @param path
     * @param width
     * @param height
     * @param mainColor
     * @param xfermode
     * @return
     */
    public static Bitmap createShadowBitmap(YTDShadow shadow, Path path, int width, int height, int mainColor, PorterDuffXfermode xfermode) {
        Bitmap shadowBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas shadowCanvas = new Canvas(shadowBitmap); // 创建画布用于绘制阴影
        shadowCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG)); // 设置画布绘图无锯齿
        Paint paint = new Paint();
        paint.setColor(-1);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setShadowLayer(shadow.getRadius(), shadow.getOffsetX(), shadow.getOffsetY(), shadow.getColor());

        int saved = shadowCanvas.save();
        float dx = -shadow.getOffsetX() + shadow.getRadius();
        float dy = -shadow.getOffsetY() + shadow.getRadius();
        shadowCanvas.translate(dx, dy); // 保存画布状态

        shadowCanvas.drawPath(path, paint);// 绘制阴影

        paint.clearShadowLayer();
        paint.setColor(mainColor);
        paint.setXfermode(xfermode);
        shadowCanvas.drawPath(path, paint);// 用混合模式扣出阴影
        paint.setXfermode(null);

        shadowCanvas.restoreToCount(saved); // 恢复画布状态

        return shadowBitmap;
    }

    /**
     * 绘制矩形阴影
     * @param canvas
     * @param shadow
     * @param radii
     * @param boundRect
     * @param mainColor 矩形原本的颜色
     */
    public static void drawShadow(Canvas canvas, YTDShadow shadow, float[] radii, RectF boundRect, int mainColor) {
        Path path = new Path();
        if (radii != null) {//至少有一个角是圆角的情况
            path.addRoundRect(boundRect, radii, Path.Direction.CW); // CW - 顺时针
        } else {
            path.addRect(boundRect, Path.Direction.CW);
        }

        float addY = Math.max(Math.abs(shadow.getOffsetY()), shadow.getRadius() * 2);
        float addX = Math.max(Math.abs(shadow.getOffsetX()), shadow.getRadius() * 2);

        Bitmap shadowBitmap = createShadowBitmap(shadow, path, (int) (boundRect.width() + addX), (int) (boundRect.height() + addY), mainColor, new PorterDuffXfermode(PorterDuff.Mode.SRC));
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        canvas.drawBitmap(shadowBitmap, boundRect.left - addX / 2, boundRect.top - addY / 2, paint);
    }

    /**
     * 绘制阴影
     * @param canvas
     * @param shadow
     * @param radii
     * @param boundRect
     */
    public static void drawShadow(Canvas canvas, YTDShadow shadow, float[] radii, RectF boundRect) {
        Path path = new Path();
        if (radii != null) {//至少有一个角是圆角的情况
            path.addRoundRect(boundRect, radii, Path.Direction.CW); // CW - 顺时针
        } else {
            path.addRect(boundRect, Path.Direction.CW);
        }
        Paint paint = new Paint();
        paint.setColor(-1);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setShadowLayer(shadow.getRadius(), shadow.getOffsetX(), shadow.getOffsetY(), shadow.getColor());
        canvas.drawPath(path, paint);// 绘制阴影
    }

    /**
     * 绘制渐变
     * @param gradient
     * @param radii
     * @param boundRect
     */
    public static Bitmap createGradientBitmap(YTDGradient gradient, float[] radii, RectF boundRect) {
        Bitmap gradientBitmap = Bitmap.createBitmap((int) boundRect.width(), (int) boundRect.height(), Bitmap.Config.ARGB_8888);

        Canvas gradientCanvas = new Canvas(gradientBitmap); // 创建画布用于绘制阴影
        gradientCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG)); // 设置画布绘图无锯齿
        Paint shadowPaint = new Paint();
        shadowPaint.setColor(-1);
        shadowPaint.setAntiAlias(true);
        shadowPaint.setDither(true);
        shadowPaint = setBoxGradientPaint(shadowPaint, gradient, (int)boundRect.width(), (int)boundRect.height(), 0, 0);

        Path path = new Path();
        if (radii != null) {//至少有一个角是圆角的情况
            path.addRoundRect(boundRect, radii, Path.Direction.CW);
        } else {
            path.addRect(boundRect, Path.Direction.CW);
        }
        gradientCanvas.drawPath(path, shadowPaint);

        return gradientBitmap;
    }

    /**
     * 设置渐变色画笔
     * @param paint
     * @param boxGradient
     * @param w
     * @param h
     * @param baseX
     * @param baseY
     * @return
     */
    public static Paint setBoxGradientPaint(Paint paint, YTDGradient boxGradient, int w, int h, int baseX, int baseY) {
        if (boxGradient != null) {
            //渐变
            float x0, y0, x1, y1;
            if (boxGradient.getStartX() == 0 && boxGradient.getStartY() == 1) {//left 渐变
                x0 = 0;
                y0 = h / 2f;
            } else if (boxGradient.getStartX() == 0 && boxGradient.getStartY() == 0) {//left_top 渐变
                if (boxGradient.getAngle() == 0) {
                    x0 = 0;
                    y0 = 0;
                } else {
                    if (w > h) {
                        x0 = w / 2f - h * (float) Math.tan(boxGradient.getAngle()) / 2f;
                        y0 = 0;
                    } else {
                        x0 = 0;
                        y0 = h / 2f - w * (float) Math.tan(boxGradient.getAngle()) / 2f;
                    }
                }
            } else if (boxGradient.getStartX() == 1 && boxGradient.getStartY() == 0) {//top 渐变
                x0 = w / 2f;
                y0 = 0;
            } else {//left_bottom
                if (boxGradient.getAngle() == 0) {
                    x0 = 0;
                    y0 = h;
                } else {
                    if (w > h) {
                        x0 = w / 2f - h * (float) Math.tan(boxGradient.getAngle()) / -2f;
                        y0 = h;
                    } else {
                        x0 = 0;
                        y0 = h / 2f + w * (float) Math.tan(boxGradient.getAngle()) / -2f;
                    }
                }
            }
            if (boxGradient.getEndX() == 2 && boxGradient.getEndY() == 1) {//right 渐变
                x1 = w;
                y1 = h / 2f;
            } else if (boxGradient.getEndX() == 2 && boxGradient.getEndY() == 2) {//right_bottom 渐变
                if (boxGradient.getAngle() == 0) {
                    x1 = w;
                    y1 = h;
                } else {
                    if (w > h) {
                        x1 = w / 2f + h * (float) Math.tan(boxGradient.getAngle()) / 2f;
                        y1 = h;
                    } else {
                        x1 = w;
                        y1 = h / 2f + w * (float) Math.tan(boxGradient.getAngle()) / 2f;
                    }
                }
            } else if (boxGradient.getEndX() == 1 && boxGradient.getEndY() == 2) {//bottom 渐变
                x1 = w / 2f;
                y1 = h;
            } else {//right_top 渐变
                if (boxGradient.getAngle() == 0) {
                    x1 = w;
                    y1 = 0;
                } else {
                    if (w > h) {
                        x1 = w / 2f + h * (float) Math.tan(boxGradient.getAngle()) / -2f;
                        y1 = 0;
                    } else {
                        x1 = w;
                        y1 = h / 2f - w * (float) Math.tan(boxGradient.getAngle()) / -2f;
                    }
                }
            }
            LinearGradient linearGradient = new LinearGradient(x0+baseX, y0+baseY, x1+baseX, y1+baseY, boxGradient.getStartColor(),
                    boxGradient.getEndColor(), Shader.TileMode.CLAMP);
            paint.setShader(linearGradient);
        }
        return paint;
    }

    /**
     * 设置超出边界
     * @param view
     * @param context
     * @param shadow
     */
    public static void setClipChildren(View view, Context context, YTDShadow shadow) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(displayMetrics);
        }
        int maxWidth = displayMetrics.widthPixels, maxHeight = displayMetrics.heightPixels;
        Rect viewRect = new Rect();
        Rect parentRect = new Rect();

        view.getHitRect(viewRect);
        viewRect.right += shadow.getOffsetX();
        viewRect.bottom += shadow.getOffsetY();

        if (viewRect.right > maxWidth) {
            viewRect.right = maxWidth;
        }
        if (viewRect.bottom > maxHeight) {
            viewRect.bottom = maxHeight;
        }

        ViewParent parentView = view.getParent();
        if (parentView instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) parentView;

            if (getClipChildren(parent)) {
                parent.setClipChildren(false);
                parent.setClipToPadding(false);
            }

            parent.getHitRect(parentRect);
        }
    }

    private static boolean getClipChildren(ViewGroup parent) {
        return parent.getClipChildren();
    }
}
