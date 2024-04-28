package org.example.base.thread.CreateThreadDemo;

import java.util.concurrent.CompletableFuture;

public class UseCompletableFuture {

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("5......");
            return "wx";
        });

        Thread.sleep(1000);
    }

}
