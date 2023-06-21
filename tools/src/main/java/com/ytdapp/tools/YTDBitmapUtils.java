package com.ytdapp.tools;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.ytdapp.tools.log.YTDLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图像工具类
 */
public class YTDBitmapUtils {

    /**
     * 图片转字节
     * @param bitmap 图片
     * @return
     */
    public static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            return baos.toByteArray();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    YTDLog.log(e);
                }
            }
        }
    }

    /**
     * 字节转图片
     * @param data 图片字节
     * @return
     */
    public static Bitmap bytesToBitmap(byte[] data) {
        return bytesToBitmap(data,1);
    }

    /**
     * 字节转图片
     * @param data 图片字节
     * @param sampleSize 图片质量
     * @return
     */
    public static Bitmap bytesToBitmap(byte[] data, int sampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    /**
     * 判断是否图片
     * @param url 图片地址
     * @return
     */
    public static boolean isImage(String url){
        String fileUrl = url.toLowerCase();
        if (fileUrl.startsWith("http")){
            return true;
        }
        if (url.endsWith(".jpg") || url.endsWith(".jpeg") ||
                url.endsWith(".png") || url.endsWith(".gif") ||
                url.endsWith(".bmp") || url.endsWith(".webp")){
            return true;
        }
        return false;
    }

    /**
     * 保存图片到路径
     * @param path 图片路径
     * @param bitmap 图片
     * @return
     */
    public static String saveBitmap2File(String path, Bitmap bitmap) {
        File filePic;
        FileOutputStream fos = null;
        try {
            filePic = new File(path);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            fos = new FileOutputStream(filePic);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                bitmap.recycle();
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            YTDLog.log(e);
            return null;
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    YTDLog.log(e);
                }
            }
        }
        return filePic.getPath();
    }

    /**
     * 截取图片
     * @param v 截取图片
     * @return
     */
    public static Bitmap getBitmapFromView(View v) {
        int tempVisibility = v.getVisibility();
        if (v.getVisibility() != View.VISIBLE){
            tempVisibility = v.getVisibility();
            v.setVisibility(View.VISIBLE);
        }
        Bitmap b;
        try {
            b = drawViewIntoBitmap(v, Bitmap.Config.ARGB_8888);
        }catch (OutOfMemoryError error){
            b = drawViewIntoBitmap(v, Bitmap.Config.RGB_565);
        }finally {
            v.setVisibility(tempVisibility);
        }
        return b;
    }

    private static Bitmap drawViewIntoBitmap(View v, Bitmap.Config config){
        if (v.getWidth() == 0 || v.getHeight() == 0){
            return null;
        }
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), config);
        Canvas c = new Canvas(b);
        // Draw background
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null){
            bgDrawable.draw(c);
        }else {
            c.drawColor(Color.WHITE);
        }
        v.draw(c);
        return b;
    }

    /**
     * Drawable to Bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 获取 drawable 长宽
        int width = drawable.getIntrinsicWidth();
        int heigh = drawable.getIntrinsicHeight();
        drawable.setBounds(0, 0, width, heigh);

        // 获取drawable的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 创建bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, heigh, config);
        // 创建bitmap画布
        Canvas canvas = new Canvas(bitmap);
        // 将drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 保存bitmap到相册
     * @param context
     * @param bitmap
     * @return
     */
    public static boolean fileSaveToPublic(Context context, Bitmap bitmap) {
        //Android 10及以上版本
        //设置路径 Pictures/
        String folder = Environment.DIRECTORY_PICTURES;
        //设置保存参数到ContentValues中
        ContentValues values = new ContentValues();
        //设置图片名称
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "Easimotor_Enterprise_customer_service.jpg");
        //设置图片格式
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        //设置图片路径
        values.put(MediaStore.Images.Media.RELATIVE_PATH, folder);
        //执行insert操作，向系统文件夹中添加文件
        //EXTERNAL_CONTENT_URI代表外部存储器，该值不变
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        OutputStream os = null;
        try {
            if (uri != null) {
                //若生成了uri，则表示该文件添加成功
                //使用流将内容写入该uri中即可
                os = context.getContentResolver().openOutputStream(uri);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(intent);
        return true;
    }
}
