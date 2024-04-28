package org.example.base.thread.VolatileDemo;

import java.util.concurrent.TimeUnit;

/**
 * volatile 测试类
 *
 */
public class VolatileTest {
    static volatile boolean flag = true;

    /**
     * 可见性测试
     * static boolean flag = true;
     * static volatile boolean flag = true;
     *
     */
    static void visualTest() {
        // t1 线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ------come in");
            try {
                TimeUnit.SECONDS.sleep(2);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = false;

        }, "t1").start();

        // t2 线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ------come in");
            while (flag) {

            }
            System.out.println(Thread.currentThread().getName()+"\t -----flag被设置为false，程序停止");
        }, "t2").start();
    }

    /**
     * 测试 原子性
     *
     */
    static void testAtom() {
        MyNumber obj = new MyNumber();

        for (int i = 0; i < 10; ++i) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    obj.addPlusPlus();
                }
            }).start();

            System.out.println(i + ": " + obj.number);
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /* 保证前面的前程都执行完
        while (Thread.activeCount() > 1)
            Thread.yield();
         */

        System.out.println("Final: " + obj.number);
        /* volatile 方式
            0: 0
            1: 1000
            2: 1000
            3: 1873
            4: 3728
            5: 4728
            6: 5728
            7: 6728
            8: 7728
            9: 8728
            Final: 9728
         */
        /*  synchronized 方式
            0: 0
            1: 1000
            2: 1000
            3: 1703
            4: 2589
            5: 5000
            6: 6000
            7: 6404
            8: 8000
            9: 8398
            Final: 10000
         */
    }

    /**
     * main
     *
     */
    public static void main(String[] args) {
//        visualTest();

        testAtom();
    }
}
