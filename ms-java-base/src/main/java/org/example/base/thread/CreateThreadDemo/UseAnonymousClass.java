package org.example.base.thread.CreateThreadDemo;

public class UseAnonymousClass {

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("8A.........");
            }
        }).start();

        new Thread(() -> {
            System.out.println("8B.........");
        }).start();
    }
}
