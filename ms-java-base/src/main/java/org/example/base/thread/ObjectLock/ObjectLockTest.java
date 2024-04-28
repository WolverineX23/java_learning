package org.example.base.thread.ObjectLock;


public class ObjectLockTest {
    public static void main(String[] args) {
        //创建两个对象
        final MyObject myObject = new MyObject();
        final MyObject myObject01 = new MyObject();

        Thread t1=new Thread (new Runnable() {
            @Override
            public void run() {
                myObject.method1();
            }
        },"t1");
        Thread t2=new Thread (new Runnable() {
            @Override
            public void run() {
                myObject01.method1();
            }
        },"t2");
        t1.start();
        t2.start();
    }
}
