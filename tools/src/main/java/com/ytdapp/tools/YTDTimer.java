package com.ytdapp.tools;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.ytdapp.tools.log.YTDLog;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;

/**
 * 定时器
 */
public class YTDTimer {
    private Timer timer;
    private TimerTask task;
    private Handler handler;

    private long delay;
    private long period;
    private IntervalListener intervalListener;
    private volatile boolean pause;

    public void setIntervalListener(IntervalListener intervalListener) {
        this.intervalListener = intervalListener;
    }

    public YTDTimer(long period) {
        this.delay = 0L;
        this.period = period;
        init();
    }

    public YTDTimer(long delay, long period) {
        this.delay = delay;
        this.period = period;
        init();
    }

    private void init() {
        timer = new Timer();
        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (intervalListener != null) {
                    intervalListener.onPeriod();
                }
            }
        };
        task = new TimerTask() {
            @Override
            public void run() {
                if (pause) {
                    return;
                }
                YTDLog.d("YTDTimer", "YTDTimer running");
                if (handler != null) {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
        };
        //启动定时器 参数对应为 TimerTask 延迟时间 间隔时间
        timer.schedule(task, delay, period);
    }

    /**
     * 暂停
     */
    public void pause() {
        YTDLog.d("YTDTimer", "YTDTimer pause");
        pause = true;
    }

    /**
     * 恢复
     */
    public void resume() {
        YTDLog.d("YTDTimer", "YTDTimer resume");
        pause = false;
    }

    /**
     * 取消定时器
     */
    public void cancel() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    /**
     * 定时器间隔监听
     */
    public interface IntervalListener {
        void onPeriod();
    }
}
