package com.scm.dashboard.schedulers;

import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理类
 *
 * Created by amqu on 2017/5/17.
 */
@Service
public class ThreadPoolManager {


    private ThreadPoolExecutor es = new ThreadPoolExecutor(2, 8,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(200), new ThreadPoolExecutor.CallerRunsPolicy());

    public void putThread(Runnable runnable) {
        es.submit(runnable);
    }
}
