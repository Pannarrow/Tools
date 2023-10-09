package com.ytdapp.tools;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description 线程池
 * @Author hongpan
 * @Date 2023/08/18 周五 8:22
 */
public class YTDThreadPool {
    /**
     * 根据cup核心数设置线程池数量
     */
    private final static int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    /**
     * 最大线程池数量= cpu核心数*2+1
     */
    private final static int MAXIMUM_POOL_SIZE = CORE_POOL_SIZE * 2 + 1;
    /**
     * 等待线程的存活时间
     */
    private final static long KEEP_ALIVE_TIME = 1;
    /**
     * 等待线程存活时间的单位
     */
    private final static TimeUnit TIME_UNIT = TimeUnit.MICROSECONDS;

    private final ThreadPoolExecutor executor;

    /**
     * 类加载时就初始化，饿汉式单例
     */
    private static final YTDThreadPool INSTANCE = new YTDThreadPool();

    public static YTDThreadPool getInstance() {
        return INSTANCE;
    }

    private YTDThreadPool() {
        executor = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT,
                new LinkedBlockingQueue<Runnable>(),
                Executors.defaultThreadFactory(),
                // 将工作队列中最老的任务丢弃，然后重新尝试接纳被拒绝的任务
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }


    public void execute(Runnable r) {
        if (executor != null) {
            executor.execute(r);
        }
    }

    /**
     * 取消任务
     * @param r
     */
    public void cancel(Runnable r) {
        if (executor != null) {
            // 从线程队列中移除对象
            executor.getQueue().remove(r);
        }
    }

    /**
     * 取消任务
     */
    public void clear() {
        if (executor != null) {
            // 从线程队列中移除对象
            executor.getQueue().clear();
        }
    }
}
