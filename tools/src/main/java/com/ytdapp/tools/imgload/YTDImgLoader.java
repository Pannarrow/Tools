package com.ytdapp.tools.imgload;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ytdapp.tools.ContextUtil;
import com.ytdapp.tools.R;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 图片加载工具类
 */
public class YTDImgLoader {

    /**
     * 下载图片
     * @param fileURL
     * @param listener
     */
    public static void downloadOnly(String fileURL, DownloadListener listener) {
        if (ContextUtil.getContext() == null) {
            if (listener != null) {
                listener.callback(null);
            }
            return;
        }
        Glide.with(ContextUtil.getContext()).asFile().load(fileURL).into(new CustomTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                if (listener != null) {
                    listener.callback(resource);
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }

    /**
     * 简单获取图片, 重载方法用uri加载图片
     *
     * @param uri
     * @param imageView
     */
    public static void loadImage(Uri uri, ImageView imageView) {
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(uri)
                .centerCrop()
                .placeholder(R.mipmap.ic_place_holder)
                .error(R.mipmap.ic_error)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.NORMAL) //下载优先级
                .into(imageView);
    }

    /**
     * 简单获取图片
     *
     * @param path
     * @param imageView
     */
    public static void loadImage(String path, ImageView imageView) {
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(path)
                .centerCrop()
                .placeholder(R.mipmap.ic_place_holder)
                .error(R.mipmap.ic_error)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.NORMAL) //下载优先级
                .into(imageView);
    }

    /**
     * 简单获取带回调
     *
     * @param path
     * @param imageView
     * @param listener
     */
    public static void loadImageByListener(String path, ImageView imageView, BitmapReadyListener listener) {
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(path)
                .centerCrop()
                .placeholder(R.mipmap.ic_place_holder)
                .error(R.mipmap.ic_error)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.NORMAL) //下载优先级
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (listener != null) {
                            listener.onResourceReady(resource);
                        }
                        imageView.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                        if (listener != null) {
                            listener.onResourceReady(null);
                            imageView.requestLayout();
                        }
                    }
                });
    }

    /**
     * 加载图片带转换器, 重载方法用uri加载图片
     *
     * @param uri
     * @param imageView
     */
    public static void loadImageWithTransform(Uri uri, ImageView imageView, @NonNull Transformation<Bitmap> transformation) {
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(uri)
                .centerCrop()
                .transform(transformation)
                .placeholder(R.mipmap.ic_place_holder)
                .error(R.mipmap.ic_error)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.NORMAL) //下载优先级
                .into(imageView);
    }

    /**
     * 加载图片带转换器
     *
     * @param path
     * @param imageView
     */
    public static void loadImageWithTransform(String path, ImageView imageView, @NonNull Transformation<Bitmap> transformation) {
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(path)
                .centerCrop()
                .transform(transformation)
                .placeholder(R.mipmap.ic_place_holder)
                .error(R.mipmap.ic_error)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.NORMAL) //下载优先级
                .into(imageView);
    }

    /**
     * 清理内存缓存，要在主线程
     */
    public static void clearMemaryCache() {
        if (ContextUtil.getContext() != null) {
            Glide.get(ContextUtil.getContext()).clearMemory();
        }
    }

    /**
     * 清理磁盘缓存，要在子线程
     */
    public static void clearDiskCache() {
        if (ContextUtil.getContext() != null) {
            Glide.get(ContextUtil.getContext()).clearDiskCache();
        }
    }
}
