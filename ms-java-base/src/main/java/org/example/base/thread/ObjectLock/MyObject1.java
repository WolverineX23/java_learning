package org.example.base.thread.ObjectLock;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyObject1 {
    public  void method1(){
        try {
            synchronized (this){
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
                String dateStr=simpleDateFormat.format(new Date());
                System.out.println("线程名称："+Thread.currentThread().getName()+"  执行时间："+dateStr);
                Thread.sleep(4000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
