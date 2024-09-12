package org.example.algorithm.FaceTest;

/**
 * 三个线程循环打印 A B C
 */
public class KSOne {
    private static final Object lock = new Object();
    private static int status = 0;

    public static void main(String[] args) {

        // print A
        Runnable printA = () -> {
            synchronized (lock) {
                while (true) {
                    if (status == 0) {
                        System.out.println("A");
                        status = 1;
                        lock.notifyAll();       // 唤醒所有等待的线程
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        // print B
        Runnable printB = () -> {
            synchronized (lock) {
                while (true) {
                    if (status == 1) {
                        System.out.println("B");
                        status = 2;
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        // print C
        Runnable printC = () -> {
            synchronized (lock) {
                while (true) {
                    if (status == 2) {
                        System.out.println("C");
                        status = 0;
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread threadA = new Thread(printA);
        Thread threadB = new Thread(printB);
        Thread threadC = new Thread(printC);

        threadA.start();
        threadB.start();
        threadC.start();
    }
}
