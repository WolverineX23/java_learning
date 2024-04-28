package org.example.base.thread.CreateThreadDemo;

import java.util.concurrent.FutureTask;

public class UseFutureTask {

    public static void main(String[] args) {

        // 和实现Callable接口的方式差不多，只不过用匿名形式创建Callable
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println("7.....");
            return "wx";
        });

        new Thread(futureTask).start();
    }
}
