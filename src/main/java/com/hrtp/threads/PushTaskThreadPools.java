package com.hrtp.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * PushTaskThreadPools
 * 向极光推送发送信息线程池
 * @author YQ
 **/
public class PushTaskThreadPools {

    public static ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 2 + 1
    );

    public synchronized static void addWorkerThread(Thread thread){
        executorService.execute(thread);
    }
}