package org.example.base.thread.CreateThreadDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 实现Callable接口，创建有返回值的线程；
 *
 */
public class ImplementsCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("3.............");

        return "wx";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ImplementsCallable callable = new ImplementsCallable();

        FutureTask<String> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();

        System.out.println(futureTask.get());
    }
}
