package org.example.base.thread.RLockDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    // 请求的数量
    private static final int threadCount = 550;

    // 模拟一个线程请求的工作耗时
    public static void test(int threadNum) throws InterruptedException {
        Thread.sleep(1000);// 模拟请求的耗时操作
        System.out.println("threadNum:" + threadNum);
        Thread.sleep(1000);// 模拟请求的耗时操作
    }

    public static void main(String[] args) {
        // 创建一个具有固定线程数量的线程池对象（如果这里线程池的线程数量给太少的话你会发现执行的很慢）
        ExecutorService threadPool = Executors.newFixedThreadPool(300);

        // 初始许可证的数量
        final Semaphore semaphore = new Semaphore(20);

        for (int i = 0; i < threadCount; ++i) {
            final int threadNum = i;
            threadPool.execute(() -> {
                try {
                    semaphore.acquire();    // 获取一个许可, 可运行线程数量为20/1=20
//                    semaphore.acquire(5); // 获取5个许可，可运行线程数量为20/5=4
                    test(threadNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
//                    semaphore.release(5); // 释放5个许可
                }
            });
        }

        threadPool.shutdown();
        System.out.println("finished!");
    }

}
