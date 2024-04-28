package org.example.base.thread.ThreadLocalDemo;

import java.text.SimpleDateFormat;
import java.util.Random;

public class ThreadLocalTest implements Runnable{

    // SimpleDateFormat 不是线程安全的，所以每个线程都要有自己独立的副本
    // java 8 - ThreadLocal.withInitial
    private static final ThreadLocal<SimpleDateFormat> formatter = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd HHmm"));
    // before
    private static final ThreadLocal<SimpleDateFormat> formatter2 = new ThreadLocal<>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd HHmm");
        }
    };

    @Override
    public void run() {
        System.out.println("Thread name = " + Thread.currentThread().getName() + " default Formatter = " + formatter.get().toPattern());

        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // formatter pattern is changed here by thread, but it won't reflect to other threads
        formatter.set(new SimpleDateFormat());

        System.out.println("Thread name = " + Thread.currentThread().getName() + " default Formatter = " + formatter.get().toPattern());
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalTest obj = new ThreadLocalTest();

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(obj, "" + i);
            Thread.sleep(new Random().nextInt(1000));
            t.start();
        }
    }
}
