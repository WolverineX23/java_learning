package org.example.base.thread.SynchronizedDemo;

public class SyncTest {

    // 代码块
    public void method() {
        synchronized (this) {
            System.out.println("synchronized 代码块");
        }
    }

    // 实例方法
    public synchronized void method2() {
        System.out.println("synchronized 代码块");
    }

    // 静态方法
    public synchronized static void method3() {
        System.out.println("synchronized 代码块");
    }

    // 可重入
    public synchronized void method4() {
        System.out.println("调用 method2, 检验重入性");
        method2();
    }

}
