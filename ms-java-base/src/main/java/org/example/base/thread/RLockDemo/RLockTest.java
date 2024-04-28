package org.example.base.thread.RLockDemo;

import java.util.concurrent.locks.*;

public class RLockTest {

    // 可重入锁
    Lock lock = new ReentrantLock();

    // 定义读写锁
    static ReadWriteLock rwLock = new ReentrantReadWriteLock();
    // 获取读锁
    static Lock rLock = rwLock.readLock();
    // 获得写锁
    static Lock wLock = rwLock.writeLock();

    // StampedLock
    StampedLock stampedLock = new StampedLock();

    // 读写锁 基本使用
    void rwLockTest() {

        // 读数据
        try {
            rLock.lock();

            // 读取共享数据
        } finally {
            rLock.unlock();
        }

        // 写数据
        try {
            wLock.lock();

            // 修改共享数据
        } finally {
            wLock.unlock();
        }
    }

    // 读
    static void read() {
        try {

            rLock.lock();   // 获取读锁
            System.out.println(Thread.currentThread().getName() + ": 获得读锁, 开始读取数据的时间--" + System.currentTimeMillis());

//                        TimeUnit.SECONDS.sleep(3); // 模拟读取数据用时、sleep 方法是要捕获异常
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock(); // 释放读锁
        }
    }

    // 写
    static void write() {
        try {
            wLock.lock();
            System.out.println(Thread.currentThread().getName() + ": 获得写锁, 开始修改数据的时间--" + System.currentTimeMillis());

            Thread.sleep(3000); // 模拟修改数据的用时
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() +": 修改数据完毕的时间--"+System.currentTimeMillis());
            wLock.unlock();
        }
    }

    // 读读共享
    static void readRead() {

        for (int i = 0; i < 5; ++i) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RLockTest.read();
                }
            }, "Thread-" + i).start();
        }

        /* 输出：
            Thread-0: 获得读锁, 开始读取数据的时间--1712921387981
            Thread-3: 获得读锁, 开始读取数据的时间--1712921387981
            Thread-4: 获得读锁, 开始读取数据的时间--1712921387981
            Thread-2: 获得读锁, 开始读取数据的时间--1712921387981
            Thread-1: 获得读锁, 开始读取数据的时间--1712921387981
         */
    }

    // 写写互斥
    static void writeWrite() {

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                RLockTest.write();
            }, "Thread-" + i).start();
        }

        /* 输出:
            Thread-2: 获得写锁, 开始修改数据的时间--1712921460929
            Thread-2: 修改数据完毕的时间--1712921463958
            Thread-1: 获得写锁, 开始修改数据的时间--1712921463959
            Thread-1: 修改数据完毕的时间--1712921466971
            Thread-4: 获得写锁, 开始修改数据的时间--1712921466971
            Thread-4: 修改数据完毕的时间--1712921469979
            Thread-0: 获得写锁, 开始修改数据的时间--1712921469979
            Thread-0: 修改数据完毕的时间--1712921472985
            Thread-3: 获得写锁, 开始修改数据的时间--1712921472985
            Thread-3: 修改数据完毕的时间--1712921475996
         */
    }

    // 读写互斥
    static void readWrite() {
        // 定义一个线程 读数据
        new Thread(RLockTest::read, "Thread-Read").start();

        // 定义一个线程 写数据
        new Thread(RLockTest::write, "Thread-Write").start();

        /* 输出
            Thread-Read: 获得读锁, 开始读取数据的时间--1712921534654
            Thread-Write: 获得写锁, 开始修改数据的时间--1712921537684
            Thread-Write: 修改数据完毕的时间--1712921540695
         */
    }

    // 测试方法
    public static void main(String[] args) {
//        readRead();
//        writeWrite();
        readWrite();
    }
}
