package org.example.base.thread.ClassLock;

public class ClassLockTest {

    public static void main(String[] args) {
        //创建两个对象
        final MyClassLock myObject = new MyClassLock();
        final MyClassLock myObject01 = new MyClassLock();

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
