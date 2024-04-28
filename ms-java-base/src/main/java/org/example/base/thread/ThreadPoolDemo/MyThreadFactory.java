package org.example.base.thread.ThreadPoolDemo;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNum = new AtomicInteger();

    private final String name;

    // 创建一个带名字的线程池生产工厂
    public MyThreadFactory(String name) {
        this.name = name;
    }


    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread t = new Thread(r);

        t.setName(name + " [#" + threadNum.incrementAndGet() + "]");
        return t;
    }
}
