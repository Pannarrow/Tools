package com.ytdapp.tools.imgload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.ytdapp.tools.YTDParseUtil;
import com.ytdapp.tools.YTDUIDisplayHelper;

import java.security.MessageDigest;

import androidx.annotation.NonNull;

/**
 * Glide圆角转换器
 */
public class YTDRoundTransform extends CenterCrop {

    private static float mRadius = 0f;
    private static float mBorderWidth = 0f;
    private static String mBorderColor;

    public YTDRoundTransform(Context context) {
        this(context, 4);
    }

    /**
     * 圆角
     *
     * @param context
     * @param radius
     */
    public YTDRoundTransform(Context context, int radius) {
        mRadius = YTDUIDisplayHelper.dp2px(context, radius);
    }

    /**
     * 圆角带边框
     *
     * @param context
     * @param radius
     * @param borderColor
     * @param borderWidth
     */
    public YTDRoundTransform(Context context, int radius, String borderColor, int borderWidth) {
        mRadius = YTDUIDisplayHelper.dp2px(context, radius);
        mBorderColor = borderColor;
        mBorderWidth = YTDUIDisplayHelper.dp2px(context, borderWidth);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap transform = super.transform(pool, toTransform, outWidth, outHeight);
        return roundCrop(pool, transform);
    }

    private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        if (!TextUtils.isEmpty(mBorderColor)) {
            //绘制圆角带边框
            Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置边框样式
            borderPaint.setColor(YTDParseUtil.parseColor(mBorderColor));
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(mBorderWidth);
            drawRoundRect(canvas, paint, source.getWidth(), source.getHeight(), borderPaint);
        } else {
            //绘制圆角
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, mRadius, mRadius, paint);
        }

        return result;
    }

    /**
     * 绘制边框
     * @param canvas
     * @param paint
     * @param width
     * @param height
     * @param borderPaint
     */
    private static void drawRoundRect(Canvas canvas, Paint paint, float width, float height, Paint borderPaint) {
        float right = width;
        float bottom = height;
        float halfBorder = mBorderWidth / 2;
        Path path = new Path();
        float[] pos = new float[8];
        int shift = 0b1111;
        int index = 3;
        while (index >= 0) {//设置四个边角的弧度半径
            pos[2 * index + 1] = ((shift & 1) > 0) ? mRadius : 0;
            pos[2 * index] = ((shift & 1) > 0) ? mRadius : 0;
            shift = shift >> 1;
            index--;
        }
        path.addRoundRect(new RectF(halfBorder, halfBorder, right - halfBorder, bottom - halfBorder),
                pos
                , Path.Direction.CW);
        canvas.drawPath(path, paint);//绘制要加载的图形
        canvas.drawPath(path, borderPaint);//绘制边框
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
