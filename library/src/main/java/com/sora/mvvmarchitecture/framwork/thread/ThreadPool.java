package com.sora.mvvmarchitecture.framwork.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum ThreadPool {
    INSTANCE;
    private final ThreadPoolExecutor mThreadPool;

    ThreadPool() {
        int core = 5;
        int maxSize = 10;
        int keepAliveTime = 20;
        mThreadPool = new ThreadPoolExecutor(core, maxSize, keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    public void execute(Runnable runnable) {
        mThreadPool.execute(runnable);
    }

    public void remove(Runnable runnable) {
        mThreadPool.remove(runnable);
    }

    public void shutDown() {
        mThreadPool.shutdown();
    }
}
