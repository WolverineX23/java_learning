package org.example.base.thread.ThreadPoolDemo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class ThreadPoolTest {

        private static final String threadNamePrefix = "sec_kill";
        private static final int corePoolSize = 5;
        private static final int maximumPoolSize = 10;
        private static final int keepAliveTime = 1;

        private static final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
        private static final BlockingQueue<Runnable> linkedWorkQueue = new LinkedBlockingDeque<>();     // 无界队列
        private static final BlockingQueue<Runnable> priorityWorkQueue = new PriorityBlockingQueue<>(); // 优先级阻塞队列 - 无界

        private static final MyThreadFactory myFac = new MyThreadFactory(threadNamePrefix);

        // 利用 guava 的 ThreadFactoryBuilder 方法创建线程池
        static void buildPoolByGuava() {
            ThreadFactory threadFactory = new ThreadFactoryBuilder()
                    .setNameFormat(threadNamePrefix)
                    .setDaemon(true).build();

            ExecutorService threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MINUTES, workQueue, threadFactory);
        }

        // 利用自定义 ThreadFactory 创建线程池
        static void buildPoolByMyFac() {
            ExecutorService threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MINUTES, workQueue, myFac);
    }

    public static void main(String[] args) {

    }
}
