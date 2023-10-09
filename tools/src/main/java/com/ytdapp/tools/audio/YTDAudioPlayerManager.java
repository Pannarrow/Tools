package com.ytdapp.tools.audio;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import com.ytdapp.tools.log.YTDLog;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class YTDAudioPlayerManager {
    private static class PlayerManagerHandler{
        private static final YTDAudioPlayerManager instance = new YTDAudioPlayerManager();
    }

    public static YTDAudioPlayerManager getInstance(){
        return PlayerManagerHandler.instance;
    }

    /**
     * 播放器
     */
    private MediaPlayer mediaPlayer;

    /**
     * 计时器
     */
    private Timer timer;

    /**
     * 是否暂停
     */
    private boolean isPause;

    /**
     * 回调切换线程
     */
    private Handler playerHandler;

    public void prepare(String path, YTDAudioPlayerListener listener) {
        release();
        playerHandler = new Handler(Looper.getMainLooper());
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setLooping(false);
        try {
            File file = new File(path);
            mediaPlayer.setDataSource(file.getPath());//指定音频文件的路径
            mediaPlayer.prepare();//让MediaPlayer进入到准备状态
        } catch (IOException e) {
            YTDLog.log(e);
        }
        mediaPlayer.setOnPreparedListener(mp -> onAudioPrepared(listener));
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                return false;
            }
        });
        mediaPlayer.setOnCompletionListener(mediaPlayer -> {
            onComplete(listener);
            setPlayerSeek(0);
        });
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (!isPause && mediaPlayer != null && mediaPlayer.isPlaying()) {
                        onProgress(mediaPlayer.getCurrentPosition(), listener);
                    }
                } catch (Exception e) {
                    YTDLog.log(e);
                }
            }
        },0,10);
    }

    /**
     * 开始播放
     */
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        isPause = false;
    }

    /**
     * 回调准备就绪
     * @param callback 播放回调
     */
    private void onAudioPrepared(YTDAudioPlayerListener callback) {
        playerHandler.post(() -> {
            if (callback != null){
                callback.onPrepared();
            }
        });
    }

    /**
     * 回调完成
     * @param callback 播放回调
     */
    private void onComplete(YTDAudioPlayerListener callback) {
        playerHandler.post(() -> {
            if (callback != null){
                callback.onComplete();
            }
        });
    }

    /**
     * 回调进度
     * @param progress 进度
     * @param callback 播放回调
     */
    private void onProgress(final int progress , YTDAudioPlayerListener callback){
        playerHandler.post(() -> {
            if (callback != null) {
                callback.onProgress(progress);
            }
        });
    }

    /**
     * 暂停
     */
    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        isPause = true;
    }

    /**
     * 释放资源
     */
    public void release() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (playerHandler != null) {
            playerHandler.removeCallbacksAndMessages(null);
            playerHandler = null;
        }
        isPause = false;
    }

    /**
     * 获取总时间
     * @return 音频时长
     */
    public int getAudioDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    /**
     * 是否正在判断
     * @return 是否正在播放
     */
    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    /**
     * 修改进度
     * @param msec 进度
     */
    public void setPlayerSeek(int msec) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(msec);
        }
    }
}
