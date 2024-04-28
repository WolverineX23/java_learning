package org.example.base.thread.RLockDemo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchExample {

    // 请求的数量
    private static final int THREAD_COUNT = 550;

    // 模拟一个线程请求的工作耗时
    public static void test(int threadNum) throws InterruptedException {
        Thread.sleep(1000);// 模拟请求的耗时操作
        System.out.println("threadNum:" + threadNum);
        Thread.sleep(1000);// 模拟请求的耗时操作
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(300);
        final CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; ++i) {
            final int threadNum = i;

            threadPool.execute(() -> {
                try {
                    test(threadNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 表示一个请求已经被完成
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        threadPool.shutdown();
        System.out.println("finished!");
    }
}
