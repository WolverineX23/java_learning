package org.example.base.thread.CreateThreadDemo;

public class ImplementsRunnable implements Runnable {


    @Override
    public void run() {
        System.out.println("2..........");
    }

    public static void main(String[] args) {
        ImplementsRunnable runnable = new ImplementsRunnable();
        new Thread(runnable).start();
    }
}
