package org.example.base.thread.VolatileDemo;

import java.util.concurrent.atomic.AtomicInteger;

public class MyNumber {


    /* volatile方式

    volatile int number;

    public void addPlusPlus() {
        number++;
    }
     */

    /* synchronized 方式

    int number;

    public synchronized void addPlusPlus() {
        number++;
    }
     */

    /* Lock 方式

    int number;
    Lock lock = new ReentrantLock();

    public void addPlusPlus() {
        lock.lock();
        try {
            number++;
        } finally {
            lock.unlock();
        }
    }
     */

    // 原子类 方式
    AtomicInteger number = new AtomicInteger();

    public void addPlusPlus() {
        number.getAndIncrement();
    }
}
