package org.example.base.thread.CreateThreadDemo;

import java.util.concurrent.*;

public class UseExecutorService {

    public static void main(String[] args) {

        ExecutorService poolA = Executors.newFixedThreadPool(5);
        poolA.execute(() -> {
            System.out.println("4A...........");
        });
        poolA.shutdown();

        ThreadPoolExecutor poolB = new ThreadPoolExecutor(2, 3, 1,
                TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(3), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        poolB.execute(() -> {
            System.out.println("4B...............");
        });
        poolB.shutdown();
    }
}
