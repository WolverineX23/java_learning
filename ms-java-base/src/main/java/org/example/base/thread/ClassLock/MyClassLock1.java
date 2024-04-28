package org.example.base.thread.ClassLock;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyClassLock1 {

    public void method1() {
        try {
            synchronized (MyClassLock1.class) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String dateStr=simpleDateFormat.format(new Date());
                System.out.println("线程名称：" + Thread.currentThread().getName() + "  执行时间："+dateStr);
                Thread.sleep(4000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
